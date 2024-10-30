package com.bgarage.ims.inventory.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.bgarage.ims.inventory.strategy.SupplierPolicy;

@Service
public class SupplierPolicyFactory {

	public SupplierPolicy createPolicy(String policyClass, Map<String, Object> params) {

		try {
			Class<?> clazz = Class.forName(policyClass);
			Constructor<?> cnstr = clazz.getConstructor(Map.class);

			Map<String, Object> paramTransformer = new HashMap<String, Object>();
			for (Entry<String, Object> entry : params.entrySet()) {
				paramTransformer.put(entry.getKey(), valueParser((String) entry.getValue()));
			}

			return (SupplierPolicy) cnstr.newInstance(paramTransformer);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create policy: " + policyClass);
		}

	}

	private Object valueParser(String value) {

		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return Boolean.parseBoolean(value);
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e1) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e2) {
				return value;
			}
		}

	}
}
