package com.sapient.learn.route;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.routepolicy.quartz2.CronScheduledRoutePolicy;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sapient.learn.model.Entity;
import com.sapient.learn.processor.TransformationProcessor;
import com.sapient.learn.processor.ValidationProcessor;

@Configuration
public class AccountProcessingProblemStatement extends RouteBuilder {

	@Autowired
	private ValidationProcessor validationProcessor;
	
	@Autowired
	private TransformationProcessor transformationProcessor;
	
	@Override
	public void configure() throws Exception {

		// CSV Binding
		final DataFormat bindy = new BindyCsvDataFormat(Entity.class);
		
		// Quartz Scheduler Configurations
		CronScheduledRoutePolicy startPolicy = new CronScheduledRoutePolicy();
		startPolicy.setRouteStartTime("0 0/60 * * * ?"); // 1 hour

		from("file:D:/camel-ride/quartz/input?noop=true")
		.routePolicy(startPolicy)
		.noAutoStartup()
		.unmarshal(bindy) // ------------------------ UnMarshall data
		.split(body())    // ------------------------ Split List<Accounts> to Account
		.filter().ognl("request.body.entityType == 'Account'")
		.process(validationProcessor)   // ---------- Validate Account (Apply business validations)
		.process(transformationProcessor) // -------- Apply Transformations
		.to("direct:publish-to-salesforce"); // ----- Publish to Target System (Salesforce)
		
		from("direct:publish-to-salesforce")
		.log("Got the messgae for publishing" + body());
		
	}

}
