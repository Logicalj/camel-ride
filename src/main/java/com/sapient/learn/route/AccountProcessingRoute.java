package com.sapient.learn.route;


import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sapient.learn.model.Entity;
import com.sapient.learn.processor.SqsMessageConsumer;
import com.sapient.learn.processor.TransformationProcessor;
import com.sapient.learn.processor.ValidationProcessor;

@Configuration
public class AccountProcessingRoute extends RouteBuilder {

	@Autowired
	private SqsMessageConsumer sqsMessageConsumer;
	
	@Autowired
	private ValidationProcessor validationProcessor;
	
	@Autowired
	private TransformationProcessor transformationProcessor;
	
	@Override
	public void configure() throws Exception {
		
		// Real-time Flow polling messages from Queue
		from("aws-sqs://MyQueue?amazonSQSClient=#client&defaultVisibilityTimeout=5000&deleteIfFiltered=false")
		.autoStartup(false)
		// Read message and delete message
		.process(sqsMessageConsumer)
		//Validate record
		.process(validationProcessor)
		//Transform input as per target
		.process(transformationProcessor)
		// Publish Record to CRM application
		.to("direct:publishToCrm-realtime");
		
		
		// CSV Binding
		final DataFormat account = new BindyCsvDataFormat(Entity.class);
		
		from("file:/D:/account-data/input")
		.autoStartup(false)
		.unmarshal(account) // Will get Collection of accounts
		.split(body()) // will split the body
		//Validate record
		.process(validationProcessor)
		//Transform input data as per target
		.process(transformationProcessor)
		// Publish Record to CRM application
		//.to("direct:publishToCrm-bulk");
		.to("direct:publishToCrm-bulk");
		
		
		from("direct:publishToCrm-realtime")
		.log("Realtime Proessing of Account to CRM")
		.to("bean:accountPublisherService?method=processAccount");
		
		
		from("direct:publishToCrm-bulk")
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
		.completionPredicate(exchange -> exchange.getIn().getBody(ArrayList.class).size() == 1000)
		.throttle(200)
		.timePeriodMillis(1*60*1000)
		.to("bean:accountPublisherService?method=processAccounts");
		
	}
		
		
}
