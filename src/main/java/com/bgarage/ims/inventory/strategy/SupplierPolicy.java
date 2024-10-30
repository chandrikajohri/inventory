package com.bgarage.ims.inventory.strategy;

import java.util.List;

import com.bgarage.ims.inventory.crudport.models.PartsEventBean;
import com.bgarage.ims.inventory.crudport.models.SuppliersEventBean;

public interface SupplierPolicy {

	boolean applyPolicy(SuppliersEventBean supplier, List<PartsEventBean> supplierParts);
}
