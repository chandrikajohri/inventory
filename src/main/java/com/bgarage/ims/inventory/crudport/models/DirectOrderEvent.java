package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;
import java.util.List;

public class DirectOrderEvent implements Serializable{

	private static final long serialVersionUID = 1L;
	private final SuppliersEventBean supplier;
	private final List<PartsEventBean> parts;

	public DirectOrderEvent(SuppliersEventBean supplier, List<PartsEventBean> parts) {
		super();
		this.supplier = supplier;
		this.parts = parts;
	}

	public SuppliersEventBean getSupplier() {
		return supplier;
	}

	public List<PartsEventBean> getParts() {
		return parts;
	}

	

}
