package com.bgarage.ims.inventory.service;

import java.util.List;

import com.bgarage.ims.inventory.crudport.models.ConsumedPart;
import com.bgarage.ims.inventory.crudport.models.InventoryPartRequest;
import com.bgarage.ims.inventory.crudport.models.PartsDTO;
import com.bgarage.ims.inventory.crudport.models.ResponseBean;

public interface InventoryOperations {

	public ResponseBean consumeParts(List<ConsumedPart> consumedParts) throws Exception;

	public ResponseBean addPart(PartsDTO partsDTO);

	public void modifySupplier(InventoryPartRequest inventoryPart);

	public void modifyPartThresholdLimit(InventoryPartRequest inventoryPart);

	public void modifyPartMinimumOrder(InventoryPartRequest inventoryPart);

	public void modifyPartPriority(InventoryPartRequest inventoryPart);

}
