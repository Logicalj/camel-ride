package com.sapient.learn.salesforce;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesforceRestTemplateTest {

	@Autowired
	private SalesforceRestTemplate salesforceRestTemplate;
	
	@Test
	public void testToken() {
		
		salesforceRestTemplate.getOauthToken();
		
	}
	
}
