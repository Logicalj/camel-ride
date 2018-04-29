package com.sapient.learn.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sapient.learn.processor.TransformationProcessor;
import com.sapient.learn.processor.ValidationProcessor;

@Configuration
public class AccountProcessingScenarioFour extends RouteBuilder {

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
		.to("direct:publish-to-salesforce"); // ----- Publish to Target System (Salesforce)
	}

}
