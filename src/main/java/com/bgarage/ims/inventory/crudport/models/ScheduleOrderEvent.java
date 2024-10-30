package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScheduleOrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private SuppliersEventBean supplier;
	private List<PartsEventBean> parts;
	private Map<String, Object> policyParams;

	public ScheduleOrderEvent() {
		super();
	}

	public ScheduleOrderEvent(SuppliersEventBean supplier, List<PartsEventBean> parts,
			Map<String, Object> policyParams) {
		super();
		this.supplier = supplier;
		this.parts = parts;
		this.policyParams = policyParams;
	}

	public Map<String, Object> getPolicyParams() {
		return policyParams;
	}

	public void setPolicyParams(Map<String, Object> policyParams) {
		this.policyParams = policyParams;
	}

	public SuppliersEventBean getSupplier() {
		return supplier;
	}

	public List<PartsEventBean> getParts() {
		return parts;
	}

}
