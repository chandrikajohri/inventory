package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class SuppliersEventBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long supplierId;

	private String name;

	private BigDecimal discount;

	private String location;

	public SuppliersEventBean() {
		super();
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
