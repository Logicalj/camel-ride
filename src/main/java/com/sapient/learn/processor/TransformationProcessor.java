package com.sapient.learn.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransformationProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Map<String, Object> message = (Map<String, Object>) exchange.getIn().getBody();
		
		// Process Transformations for TARGET
		
		exchange.getIn().getBody();
		
	}

}
