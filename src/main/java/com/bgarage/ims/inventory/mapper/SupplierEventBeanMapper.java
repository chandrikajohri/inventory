package com.bgarage.ims.inventory.mapper;

import com.bgarage.ims.inventory.crudport.models.SuppliersEventBean;
import com.bgarage.ims.inventory.entity.Suppliers;

public class SupplierEventBeanMapper {

	public static SuppliersEventBean mapToEventBean(Suppliers supplier) {
		if (supplier == null) {
			return null;
		}
		SuppliersEventBean eventBean = new SuppliersEventBean();
		eventBean.setSupplierId(supplier.getSupplierId());
		return eventBean;
	}

	public static SuppliersEventBean mapToResponseBean(Suppliers supplier) {
		if (supplier == null) {
			return null;
		}
		SuppliersEventBean eventBean = new SuppliersEventBean();
		eventBean.setSupplierId(supplier.getSupplierId());
		eventBean.setName(supplier.getName());
		eventBean.setDiscount(supplier.getDiscount());
		eventBean.setLocation(supplier.getLocation());
		return eventBean;
	}
}
