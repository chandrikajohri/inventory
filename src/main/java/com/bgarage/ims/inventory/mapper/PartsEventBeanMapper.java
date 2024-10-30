package com.bgarage.ims.inventory.mapper;

import com.bgarage.ims.inventory.crudport.models.PartsDTO;
import com.bgarage.ims.inventory.crudport.models.PartsEventBean;
import com.bgarage.ims.inventory.entity.Parts;

public class PartsEventBeanMapper {

	public static PartsEventBean mapToEventBean(Parts parts) {
		if (parts == null) {
			return null;
		}
		PartsEventBean eventBean = new PartsEventBean();
		eventBean.setPartId(parts.getPartId());

		return eventBean;
	}

	public static PartsDTO mapToResponseBean(Parts parts) {
		if (parts == null) {
			return null;
		}
		PartsDTO eventBean = new PartsDTO();
		eventBean.setPartId(parts.getPartId());
		eventBean.setSkuCode(parts.getSkuCode());
		eventBean.setBrand(parts.getBrand());
		eventBean.setName(parts.getName());
		eventBean.setPrice(parts.getPrice());
		eventBean.setThresholdLimit(parts.getThresholdLimit());
		eventBean.setAvailableQty(parts.getAvailableQty());
		eventBean.setMinimumOrder(parts.getMinimumOrder());
		eventBean.setPartPriority(parts.getPartPriority().toString());

		if (parts.getSupplier() != null) {
			eventBean.setSupplierId(parts.getSupplier().getSupplierId());
		} else {
			eventBean.setSupplierId(null);
		}

		return eventBean;
	}

	public static Parts mapDTOToEntity(PartsDTO parts) {
		if (parts == null) {
			return null;
		}
		Parts eventBean = new Parts();
		eventBean.setPartId(parts.getPartId());
		eventBean.setSkuCode(parts.getSkuCode());
		eventBean.setBrand(parts.getBrand());
		eventBean.setName(parts.getName());
		eventBean.setPrice(parts.getPrice());
		eventBean.setThresholdLimit(parts.getThresholdLimit());
		eventBean.setAvailableQty(parts.getAvailableQty());
		eventBean.setMinimumOrder(parts.getMinimumOrder());
		eventBean.setPartPriority(parts.getPartPriority().toString());

		return eventBean;
	}
}
