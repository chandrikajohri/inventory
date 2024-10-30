package com.bgarage.ims.inventory.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Policy implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long policyId;

	private String policyClass;

	@ManyToOne
	@JoinColumn(name = "supplierId", nullable = false)
	private Suppliers supplier;

	@OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PolicyParameter> parameters = new ArrayList<>();

	public String getPolicyClass() {
		return policyClass;
	}

	public void setPolicyClass(String policyClass) {
		this.policyClass = policyClass;
	}

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public List<PolicyParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<PolicyParameter> parameters) {
		this.parameters = parameters;
	}

	public Long getPolicyId() {
		return policyId;
	}

	
}
