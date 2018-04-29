package com.sapient.learn.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentBasedRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:D:/camel-ride/content-based-routing/input")
		.autoStartup(false)
			.choice()
				.when(xpath("/Order/Country='USA'"))
					.log("Country: USA")
					.to("direct:process-order-for-USA")
				.when(xpath("/Order/Country='UK'"))
					.log("Country: UK")
					.to("direct:process-order-for-UK")
				.otherwise()
					.log("Unsupported Region !!")
					.to("direct:invalid-order")
		.end();
		
		from("direct:process-order-for-USA")
		// Got Order For USA
		.log("Processing USA Order !!")
		// PROCESS TAXTION
		.to("file:D:/camel-ride/content-based-routing/output/USA");
		
		from("direct:process-order-for-UK")
		// Got Order for UK
		.log("Processing UK Order !!")
		// PROCESS TAXTION
		.to("file:D:/camel-ride/content-based-routing/output/UK");
		
		
		from("direct:invalid-order")
		.log("Unsupported Region, Message body: ${body}")
		.to("file:D:/camel-ride/content-based-routing/output/INVALID");
		
		
		
	}
}
