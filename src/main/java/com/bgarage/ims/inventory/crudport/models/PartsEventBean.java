package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;

public class PartsEventBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long partId;

	

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	
}
