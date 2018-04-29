package com.sapient.learn.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

import com.sapient.learn.model.Order;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	
	ConcurrentHashMap<Integer, Order> orderDetails = new ConcurrentHashMap<>();
	
	@PostConstruct
	void init() {
		
		// Order 1
		Order o1 = new Order(101, "India", 10000, "ITM0001", "100", 100);
		orderDetails.put(o1.getNumber(), o1);
		
		// Order 2
		Order o2 = new Order(102, "India", 2000, "ITM0011", "100", 20);
		orderDetails.put(o2.getNumber(), o2);

		// Order 3
		Order o3 = new Order(103, "USA", 20000, "ITM0021", "100", 200);
		orderDetails.put(o3.getNumber(), o3);
		
		// Order 4
		Order o4 = new Order(104, "USA", 1000, "ITM0028", "100", 10);
		orderDetails.put(o4.getNumber(), o4);
		
		// Order 5
		Order o5 = new Order(105, "USA", 500, "ITM0029", "100", 5);
		orderDetails.put(o5.getNumber(), o5);
	}
	
	public void updateOrder(Exchange exchange) {
		
		Order order = (Order) exchange.getIn().getBody();
		orderDetails.put(order.getNumber(), order);
		
		System.out.println("Going to PUT Order with following details: ");
		System.out.println("OrderId: "+ order.getNumber());
		System.out.println("Country: "+ order.getCountry());
		System.out.println("Amount: "+ order.getAmount());
		System.out.println("ItemId: "+ order.getItemId());
	}
	
	public void getOrder(int orderId, Exchange exchange) {
		
		Order order = orderDetails.get(orderId);
		exchange.getIn().setBody(order);
		
	}
	
	public void getAllOrders(Exchange exchange){
		
		exchange.getIn().setBody(orderDetails.values());
	}

}
