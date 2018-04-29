package com.sapient.learn.route;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.routepolicy.quartz2.CronScheduledRoutePolicy;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedulerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		CronScheduledRoutePolicy startPolicy = new CronScheduledRoutePolicy();
		startPolicy.setRouteStartTime("0 0/1 * * * ?"); // 1 minute
		
		                 
		
		from("file:D:/camel-ride/quartz/input?noop=true").routePolicy(startPolicy).noAutoStartup().process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println(LocalDateTime.now() + "I'm running every min...");
            }
        })
		.to("file:D:/camel-ride/quartz/output");
		
	}

}
