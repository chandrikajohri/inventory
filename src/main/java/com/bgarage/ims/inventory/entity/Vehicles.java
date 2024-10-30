package com.bgarage.ims.inventory.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Vehicles implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vehicleId;

	private String vehicleType;

	@OneToMany(mappedBy = "vehicleType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Parts> parts = new HashSet<>();

	public Long getVehicleId() {
		return vehicleId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Set<Parts> getParts() {
		return parts;
	}

	public void setParts(Set<Parts> parts) {
		this.parts = parts;
	}

}
