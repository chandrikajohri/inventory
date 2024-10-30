package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long partId;

	private String skuCode;
	private String brand;
	private String name;
	private BigDecimal price;
	private Integer thresholdLimit;
	private Integer availableQty;
	private Integer minimumOrder;
	private String partPriority;
	private Long supplierId;

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getThresholdLimit() {
		return thresholdLimit;
	}

	public void setThresholdLimit(Integer thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Integer getMinimumOrder() {
		return minimumOrder;
	}

	public void setMinimumOrder(Integer minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	public String getPartPriority() {
		return partPriority;
	}

	public void setPartPriority(String partPriority) {
		this.partPriority = partPriority;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
