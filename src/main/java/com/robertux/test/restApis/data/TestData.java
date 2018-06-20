package com.robertux.test.restApis.data;

import java.util.Date;

public class TestData {
	private long id;
	private String name;
	private Date startDate;
	private double amount;
	
	public TestData() {
		this.name = "";
		this.startDate = new Date();
	}
	
	public TestData(long id, String name, Date startDate, double amount) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.amount = amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "TestData [id=" + id + ", name=" + name + ", startDate=" + startDate + ", amount=" + amount + "]";
	}
}
