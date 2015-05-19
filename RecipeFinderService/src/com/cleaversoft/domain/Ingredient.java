package com.cleaversoft.domain;

public class Ingredient {
	
	String item;
	int amount;
	FridgeItemUnit unit;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public FridgeItemUnit getUnit() {
		return unit;
	}
	public void setUnit(FridgeItemUnit unit) {
		this.unit = unit;
	}
	
	
}
