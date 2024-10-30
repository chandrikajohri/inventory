package com.bgarage.ims.inventory.crudport.models;

public class ConsumedPart {
	
	private String partId;
	private int consumedQuantity;
	
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public int getConsumedQuantity() {
		return consumedQuantity;
	}
	public void setConsumedQuantity(int consumedQuantity) {
		this.consumedQuantity = consumedQuantity;
	}
	

}
