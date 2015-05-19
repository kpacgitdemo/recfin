package com.cleaversoft.domain;

import java.util.Date;

public class FridgeItem {
	
		String item;
		int amount;
		FridgeItemUnit unit;
		Date usedBy;
		
		public FridgeItem(String item, int amount, FridgeItemUnit unit, Date usedBy) {
			this.item = item;
			this.amount = amount;
			this.unit = unit;
			this.usedBy = usedBy;
		}

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

		public Date getUsedBy() {
			return usedBy;
		}

		public void setUsedBy(Date usedBy) {
			this.usedBy = usedBy;
		} 
		
		

}
