package com.sapient.learn.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public class Entity {
	
	@DataField(pos = 1)
	private int entityType;
	@DataField(pos = 2)
	private String entityId;
	@DataField(pos = 3)
	private String entityName;
	@DataField(pos = 4)
	private String reportingName;
	@DataField(pos = 5)
	private String inceptioDate;
	@DataField(pos = 6)
	private String openDate;
	@DataField(pos = 7)
	private String closeDate;
	@DataField(pos = 8)
	private String currency;
	@DataField(pos = 9)
	private String agent;
	public int getEntityType() {
		return entityType;
	}
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getReportingName() {
		return reportingName;
	}
	public void setReportingName(String reportingName) {
		this.reportingName = reportingName;
	}
	public String getInceptioDate() {
		return inceptioDate;
	}
	public void setInceptioDate(String inceptioDate) {
		this.inceptioDate = inceptioDate;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	

}