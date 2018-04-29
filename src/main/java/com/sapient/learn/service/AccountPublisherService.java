package com.sapient.learn.service;


import java.util.List;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

import com.sapient.learn.model.Entity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountPublisherService {
	
	public void processAccount(Exchange exchange) {
		
		Entity account = (Entity) exchange.getIn().getBody();
		
		// Publish Account
		publichAccount(account);
	}
	
	public void processAccounts(Exchange exchange) {
		
		List<Entity> accountList = (List<Entity>) exchange.getIn().getBody();
		
		for (Entity account: accountList) {
			
			// Publish Account
			publichAccount(account);
			
		}
		
	}
	
	
	private void publichAccount(Entity account) {
		
		// Publish the account to CRM application
		
	}
	
	
	
}
