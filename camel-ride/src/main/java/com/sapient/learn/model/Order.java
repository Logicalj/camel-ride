package com.sapient.learn.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public class Order {
	
	public Order()
	{
		this.number = 001;
		this.country = "UK";
		this.amount = 10L;
		this.itemId = "ITM0000";
		this.itemCost = "5";
		this.itemQuantity = 2;
	}
	
	public Order(int number, String country, long amount, String itemId, String itemCost, int itemQuantity) {
		this.number = number;
		this.country = country;
		this.amount = amount;
		this.itemId = itemId;
		this.itemCost = itemCost;
		this.itemQuantity = itemQuantity;
	}
	@DataField(pos = 1)
	private int number;
	@DataField(pos = 2)
	private String country;
	@DataField(pos = 3)
	private long amount;
	@DataField(pos = 4)
	private String itemId;
	@DataField(pos = 5)
	private String itemCost;
	@DataField(pos = 6)
	private int itemQuantity;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemCost() {
		return itemCost;
	}
	public void setItemCost(String itemCost) {
		this.itemCost = itemCost;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	

}