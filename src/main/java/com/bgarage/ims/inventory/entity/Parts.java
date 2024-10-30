package com.bgarage.ims.inventory.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bgarage.ims.audit.Audit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Parts extends Audit implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partId;

	private String skuCode;
	private String brand;
	private String name;
	private BigDecimal price;
	private Integer thresholdLimit;
	private Integer availableQty;
	private Integer minimumOrder;
	private String partPriority;

	@ManyToOne
	@JoinColumn(name = "supplierId", nullable = false)
	private Suppliers supplier;

	@ManyToOne
	@JoinColumn(name = "vehicleId", nullable = false)
	private Vehicles vehicleType;

	public Long getPartId() {
		return partId;
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

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Vehicles getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Vehicles vehicleType) {
		this.vehicleType = vehicleType;
	}

}
