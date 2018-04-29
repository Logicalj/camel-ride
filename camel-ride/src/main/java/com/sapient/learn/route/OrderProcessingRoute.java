package com.sapient.learn.route;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.springframework.context.annotation.Configuration;

import com.sapient.learn.model.Order;

@Configuration
public class OrderProcessingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		// CSV Binding
		final DataFormat bindy = new BindyCsvDataFormat(Order.class);
		
		/**
		 *  file Based order processing route
		 */
		from("file:D:/camel-ride/order-processing/input/?noop=true")
		.autoStartup(true)
	    .unmarshal(bindy)
	    .to("bean:orderService?method=updateOrder");

		
		
		
		// REST Component configuration
		restConfiguration().component("restlet").host("localhost").port(8081).bindingMode(RestBindingMode.auto);
		
		/**
		 *  Order Processing route (Rest Service) 
		 */
		rest("/order").description("Order Rest Service")
			.consumes("application/json").produces("application/json")
			
			.get("/{id}").description("Find Order By ID").outType(Order.class)
				.to("bean:orderService?method=getOrder(${header.id})")
			
			.put().description("Create / Update Order").type(Order.class)
				.to("bean:orderService?method=updateOrder")
		
			.get("/list").description("Get all Orders").outType(Order.class)
			.to("bean:orderService?method=getAllOrders()");
		
	}

}
