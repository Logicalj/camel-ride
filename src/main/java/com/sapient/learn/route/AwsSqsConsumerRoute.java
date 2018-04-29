package com.sapient.learn.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSqsConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// Automated route (scheduled to run after every 5 seconds)
		from("timer:initial//start?period=5000")
		.autoStartup(false)
        .routeId("aws-sqs-route")
        .to("log:Hello From Route");
	}

}
