package com.bgarage.ims.inventory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bgarage.ims.inventory.crudport.messageAdapter.EventPublisher;
import com.bgarage.ims.inventory.crudport.models.ConsumedPart;
import com.bgarage.ims.inventory.crudport.models.DirectOrderEvent;
import com.bgarage.ims.inventory.crudport.models.InventoryPartRequest;
import com.bgarage.ims.inventory.crudport.models.PartsDTO;
import com.bgarage.ims.inventory.crudport.models.PartsDTOList;
import com.bgarage.ims.inventory.crudport.models.PartsEventBean;
import com.bgarage.ims.inventory.crudport.models.PartsEventBeanList;
import com.bgarage.ims.inventory.crudport.models.ResponseBean;
import com.bgarage.ims.inventory.crudport.models.SuppliersEventBean;
import com.bgarage.ims.inventory.entity.Parts;
import com.bgarage.ims.inventory.entity.Priority;
import com.bgarage.ims.inventory.entity.Suppliers;
import com.bgarage.ims.inventory.exception.ServiceException;
import com.bgarage.ims.inventory.mapper.PartsEventBeanMapper;
import com.bgarage.ims.inventory.mapper.SupplierEventBeanMapper;
import com.bgarage.ims.inventory.repositories.PartsRepository;
import com.bgarage.ims.inventory.repositories.SupplierRepository;
import com.bgarage.ims.inventory.strategy.SupplierPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class InventoryService implements InventoryOperations {

	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

	@Autowired
	PartsRepository partsRepository;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	EventPublisher eventPublisher;

	private PolicyLoader policyLoader;

	@Value("${bgarage.kafka.topic.create-direct-order}")
	private String directOrderTopic;

	@Value("${bgarage.kafka.topic.create-schedule-order}")
	private String scheduleOrderTopic;

	public InventoryService(PolicyLoader policyLoader) {
		this.policyLoader = policyLoader;
	}

	@Override
	@Transactional
	public ResponseBean consumeParts(List<ConsumedPart> consumedParts) {
		List<Parts> listOfLowStockParts = new ArrayList<Parts>();

		try {
			// update consumed quantity for each part
			for (ConsumedPart consumedPart : consumedParts) {
				Optional<Parts> partOptional = partsRepository.findById(Long.parseLong(consumedPart.getPartId()));
				if (partOptional.isPresent()) {
					Parts part = partOptional.get();
					if (part.getAvailableQty() - consumedPart.getConsumedQuantity() > 0) {
						int newQty = part.getAvailableQty() - consumedPart.getConsumedQuantity();
						part.setAvailableQty(newQty);
						partsRepository.save(part);
						// filter parts low on stock
						if (newQty <= part.getThresholdLimit()) {
							listOfLowStockParts.add(part);
						}
					} else {
						throw new ServiceException("consumeParts", "Available parts are less than consumed parts");
					}
				}
			}

			if (!listOfLowStockParts.isEmpty() && listOfLowStockParts.size() > 0) {
				processLowStockParts(listOfLowStockParts);
			}
			return ResponseBean.ResponseBuilder.response().withStatus("inventory consumed")
					.withStatusCode(HttpStatus.OK.value()).withData(null).build();
		} catch (Exception e) {

			throw new ServiceException("consumeParts", "Error while updating consumed inventory");

		}
	}

	private void processLowStockParts(List<Parts> listOfLowStockParts) {

		try {
			Map<Long, List<Parts>> partsBySupplierMap = new HashMap<Long, List<Parts>>();
			for (Parts lowStockPart : listOfLowStockParts) {
				if (partsBySupplierMap.containsKey(lowStockPart.getSupplier().getSupplierId())) {
					partsBySupplierMap.get(lowStockPart.getSupplier().getSupplierId()).add(lowStockPart);
				} else {
					List<Parts> partBySupplierList = new ArrayList<Parts>();
					partBySupplierList.add(lowStockPart);
					partsBySupplierMap.put(lowStockPart.getSupplier().getSupplierId(), partBySupplierList);
				}
			}
			for (Entry<Long, List<Parts>> entry : partsBySupplierMap.entrySet()) {

				long supplierId = entry.getKey();
				List<Parts> supplierPartsList = entry.getValue();

				Optional<Suppliers> supplier = supplierRepository.findById(supplierId);
				SuppliersEventBean supplierEventBean = SupplierEventBeanMapper.mapToEventBean(supplier.get());

				if (supplier.get().getPolicies().isEmpty()) {
					List<PartsEventBean> directOrderParts = new ArrayList<PartsEventBean>();

					for (Parts supplierPart : supplierPartsList) {
						PartsEventBean partsEventBean = PartsEventBeanMapper.mapToEventBean(supplierPart);
						directOrderParts.add(partsEventBean);
					}
					DirectOrderEvent directOrderEvent = new DirectOrderEvent(supplierEventBean, directOrderParts);
					ObjectMapper objectMapper = new ObjectMapper();
					String jsonString = objectMapper.writeValueAsString(directOrderEvent);
					logger.info("publish directOrderEvent " + jsonString);
					eventPublisher.publishEvent(directOrderTopic, jsonString);

				} else {
					List<Parts> criticalParts = new ArrayList<Parts>();
					List<PartsEventBean> criticalPartsEventBeanList = new ArrayList<PartsEventBean>();
					List<PartsEventBean> noncriticalPartsEventBeanList = new ArrayList<PartsEventBean>();
					List<Parts> nonCriticalParts = new ArrayList<Parts>();

					for (Parts supplierPart : supplierPartsList) {
						PartsEventBean partsEventBean = PartsEventBeanMapper.mapToEventBean(supplierPart);
						if (supplierPart.getPartPriority().equalsIgnoreCase(Priority.HIGH.toString())) {
							criticalParts.add(supplierPart);
							criticalPartsEventBeanList.add(partsEventBean);
						} else {
							nonCriticalParts.add(supplierPart);
							noncriticalPartsEventBeanList.add(partsEventBean);
						}
					}

					if (!criticalParts.isEmpty()) {
						DirectOrderEvent directOrderEvent = new DirectOrderEvent(supplierEventBean,
								criticalPartsEventBeanList);

						ObjectMapper objectMapper = new ObjectMapper();
						String jsonString = objectMapper.writeValueAsString(directOrderEvent);
						eventPublisher.publishEvent(directOrderTopic, jsonString);
						logger.info("publish directOrderEvent " + jsonString);
					}

					if (!nonCriticalParts.isEmpty()) {
						applyPoliciesAndHandleOrder(supplierEventBean, noncriticalPartsEventBeanList);
					}
				}

			}
		} catch (Exception e) {
			throw new ServiceException("consumeParts", "Error while updating consumed inventory");
		}

	}

	private void applyPoliciesAndHandleOrder(SuppliersEventBean suppliersEventBean,
			List<PartsEventBean> nonCriticalPartsEventBean) {

		List<SupplierPolicy> policiesForSupplier = policyLoader
				.getPoliciesForSupplier(suppliersEventBean.getSupplierId().toString());

		for (SupplierPolicy supplierPolicy : policiesForSupplier) {
			supplierPolicy.applyPolicy(suppliersEventBean, nonCriticalPartsEventBean);
		}

	}

	@Override
	public ResponseBean addPart(PartsDTO partsDTO) {
		Parts parts = PartsEventBeanMapper.mapDTOToEntity(partsDTO);
		partsRepository.save(parts);
		return ResponseBean.ResponseBuilder.response().withStatus("part added").withStatusCode(HttpStatus.OK.value())
				.withData(null).build();

	}

	public PartsDTOList getPartDetails(PartsEventBeanList partsEventBeanList) {
		List<Parts> parts = partsRepository.findAllById(partsEventBeanList.getPartsEventBeanList());
		List<PartsDTO> responseBeans = new ArrayList<PartsDTO>();
		for (Parts part : parts) {
			responseBeans.add(PartsEventBeanMapper.mapToResponseBean(part));
		}
		PartsDTOList partsResponseBeanList = new PartsDTOList();
		partsResponseBeanList.setPartsResponseBeans(responseBeans);
		return partsResponseBeanList;
	}

	public SuppliersEventBean getSupplierDetails(Long supplierId) {
		Optional<Suppliers> supplier = supplierRepository.findById(supplierId);
		if (supplier.isPresent()) {
			return SupplierEventBeanMapper.mapToResponseBean(supplier.get());
		}
		return null;
	}

	@Override
	public void modifySupplier(InventoryPartRequest inventoryPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyPartThresholdLimit(InventoryPartRequest inventoryPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyPartMinimumOrder(InventoryPartRequest inventoryPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyPartPriority(InventoryPartRequest inventoryPart) {
		// TODO Auto-generated method stub

	}

	public ResponseBean updateParts(PartsDTO partsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseBean updatePartSupplier(PartsDTO partsDTO) {
		// TODO Auto-generated method stub
		return null;

	}

}
