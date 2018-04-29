package com.sapient.learn.route;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sapient.learn.model.Entity;
import com.sapient.learn.processor.TransformationProcessor;
import com.sapient.learn.processor.ValidationProcessor;

@Configuration
public class AccountProcessingScenarioFive extends RouteBuilder {

	@Autowired
	private ValidationProcessor validationProcessor;
	
	@Autowired
	private TransformationProcessor transformationProcessor;
	
	@Override
	public void configure() throws Exception {

		// Real-time Flow polling messages from Queue
		from("aws-sqs://MyQueue?amazonSQSClient=#client&defaultVisibilityTimeout=5000&deleteIfFiltered=false") // -- Read SQS message
		.autoStartup(false)
		.filter(header("entityType").isEqualTo("Account"))
		.to("direct:publish-to-target-rest");
		
		from("direct:publish-to-target-rest")
		.autoStartup(false)
		.to("http://APPLICATION_ENDPOINT/crm/api/rest/accocunt/"+header("entityUrl"))
		.process(validationProcessor)   // ---------- Validate Account (Apply business validations)
		.process(transformationProcessor) // -------- Apply Transformations
		.to("direct:publish-to-salesforce"); // ----- Publish to Target System
		
		from("direct:publish-to-salesforce")
		.log("Bulk Proessing of Accounts to CRM")
		.aggregate(constant(true), (Exchange oldExchange, Exchange newExchange) -> {
			ArrayList<Entity> accountList = null;
			if(oldExchange == null) {
				accountList = new ArrayList<>();
				accountList.add((Entity) newExchange.getIn().getBody());
				newExchange.getIn().setBody(accountList);
				return newExchange;
			}
			else {
				accountList = oldExchange.getIn().getBody(ArrayList.class);
				accountList.add((Entity) newExchange.getIn().getBody());
				return oldExchange;
			}
		})
		.completionPredicate(
				exchange -> exchange.getIn().getBody(ArrayList.class).size() == 1000
				) // ---------------------------------------- Aggregate 1000 message
		.throttle(200).timePeriodMillis(60*60*1000) // ------ Allow only 200 batches within a period of 60 minutes
		.to("bean:accountPublisherService?method=processAccounts");
		
		
	}

}
