package com.sapient.learn.salesforce;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sapient.learn.common.RestTemplateConfig;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class SalesforceRestTemplate {

	@Autowired
	RestTemplateConfig restConfig;
	
	public String getOauthToken() {
		
		String oauthUrl = "https://login.salesforce.com/services/oauth2/token";

		  // Create a multi value map to put necessary attributes
		  // for the authentication request
		  MultiValueMap<String, String> mvMap = new LinkedMultiValueMap<>();
		  mvMap.add("grant_type", "password");
		  mvMap.add("client_id", "3MVG9d8..z.hDcPIyp3YlXtRWeKt97uQAhNSkmJAgUICz2wW3AQEkQqSXKn3Y_ekdUM9OMfvcSMTDwIdShtJy");
		  mvMap.add("client_secret", "4477470277634703641");
		  mvMap.add("username", "lakshay.joshi10@gmail.com");
		  mvMap.add("password", "Jan@12345rLcI5FrobNkaj8FPe1HTMjZY");

		// Create an instance of the RestTemplate
		  RestTemplate restTemplate = new RestTemplate(restConfig.clientHttpRequestFactory());

		  // Send the REST request to get authenticated and receive an OAuth token
		  Map<String, String> token = (Map<String, String>) restTemplate.postForObject(oauthUrl, mvMap, Map.class);

		  // The oauth token is now required for all further calls to the REST resources
		  String oauthToken = token.get("access_token");
		  
		  return oauthToken;
	}
	
	
	
}
