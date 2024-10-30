package com.bgarage.ims.inventory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgarage.ims.inventory.entity.Policy;
import com.bgarage.ims.inventory.entity.PolicyParameter;
import com.bgarage.ims.inventory.entity.Suppliers;
import com.bgarage.ims.inventory.factory.SupplierPolicyFactory;
import com.bgarage.ims.inventory.repositories.SupplierRepository;
import com.bgarage.ims.inventory.strategy.SupplierPolicy;

import jakarta.annotation.PostConstruct;

@Service
public class PolicyLoader {

	@Autowired
	SupplierPolicyFactory supplierPolicyFactory;

	@Autowired
	SupplierRepository supplierRepository;

	private Map<Long, List<SupplierPolicy>> supplierPolicyMap = new HashMap<>();

	@PostConstruct
	public void init() {
		List<Suppliers> suppliers = supplierRepository.findAll();

		for (Suppliers supplier : suppliers) {

			if (supplier.getPolicies().size() > 0) {

				List<SupplierPolicy> supplierPolicies = new ArrayList<SupplierPolicy>();

				for (Policy policy : supplier.getPolicies()) {
					Map<String, Object> params = new HashMap<>();

					for (PolicyParameter policyParameter : policy.getParameters()) {
						params.put(policyParameter.getParamKey(), policyParameter.getParamValue());
					}

					SupplierPolicy supplierPolicy = supplierPolicyFactory.createPolicy(policy.getPolicyClass(), params);
					supplierPolicies.add(supplierPolicy);
				}
				supplierPolicyMap.put(supplier.getSupplierId(), supplierPolicies);
			}
		}
	}

	public List<SupplierPolicy> getPoliciesForSupplier(String supplierId) {
		return supplierPolicyMap.getOrDefault(Long.parseLong(supplierId), Collections.emptyList());

	}
}
