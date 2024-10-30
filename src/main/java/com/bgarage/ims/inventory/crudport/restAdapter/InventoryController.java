package com.bgarage.ims.inventory.crudport.restAdapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bgarage.ims.inventory.crudport.models.ConsumedPartsRequestBean;
import com.bgarage.ims.inventory.crudport.models.PartsDTO;
import com.bgarage.ims.inventory.crudport.models.PartsDTOList;
import com.bgarage.ims.inventory.crudport.models.PartsEventBeanList;
import com.bgarage.ims.inventory.crudport.models.ResponseBean;
import com.bgarage.ims.inventory.crudport.models.SuppliersEventBean;
import com.bgarage.ims.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	private InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@PostMapping("/consume")
	public ResponseBean consumeInventory(@RequestBody ConsumedPartsRequestBean consumedPartsRequestBean) {
		return inventoryService.consumeParts(consumedPartsRequestBean.getConsumedPartsList());

	}

	@PostMapping("/parts")
	public ResponseBean addPart(@RequestBody PartsDTO partsDTO) {
		return inventoryService.addPart(partsDTO);
	}

	@PostMapping("/part-details")
	public PartsDTOList getPartDetails(@RequestBody PartsEventBeanList partsEventBeanList) {
		return inventoryService.getPartDetails(partsEventBeanList);
	}

	@PutMapping("/parts")
	public ResponseBean updateParts(@RequestBody PartsDTO partsDTO) {
		return inventoryService.updateParts(partsDTO);
	}

	@GetMapping("/suppliers")
	public SuppliersEventBean getSupplierDetails(@RequestParam Long supplierId) {
		return inventoryService.getSupplierDetails(supplierId);
	}

	@GetMapping("/parts/supplier")
	public ResponseBean updatePartSupplier(@RequestBody PartsDTO partsDTO) {
		return inventoryService.updatePartSupplier(partsDTO);
	}
}
