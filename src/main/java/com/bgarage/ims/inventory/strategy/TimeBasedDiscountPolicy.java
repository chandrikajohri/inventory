package com.bgarage.ims.inventory.strategy;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bgarage.ims.inventory.crudport.messageAdapter.EventPublisher;
import com.bgarage.ims.inventory.crudport.models.DirectOrderEvent;
import com.bgarage.ims.inventory.crudport.models.PartsEventBean;
import com.bgarage.ims.inventory.crudport.models.ScheduleOrderEvent;
import com.bgarage.ims.inventory.crudport.models.SuppliersEventBean;
import com.bgarage.ims.inventory.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TimeBasedDiscountPolicy implements SupplierPolicy {

	private static final Logger logger = LoggerFactory.getLogger(TimeBasedDiscountPolicy.class);

	@Autowired
	EventPublisher eventPublisher;

	@Value("${bgarage.kafka.topic.place-order}")
	private String placeOrderTopic;

	@Value("${bgarage.kafka.topic.schedule-order}")
	private String scheduleOrderTopic;

	private LocalTime discountStartTime;
	private LocalTime discountEndTime;

	public TimeBasedDiscountPolicy(Map<String, Object> params) {

		this.discountStartTime = LocalTime.parse((String) params.get("discountStartTime"));
		this.discountEndTime = LocalTime.parse((String) params.get("discountEndTime"));
	}

	@Override
	public boolean applyPolicy(SuppliersEventBean supplierEventBean, List<PartsEventBean> supplierPartsEventBeanList) {

		boolean policyApplied = false;

		try {
			if (isDiscountWindowOnGoing(LocalTime.now())) {

				DirectOrderEvent placeOrderEvent = new DirectOrderEvent(supplierEventBean, supplierPartsEventBeanList);
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(placeOrderEvent);

				eventPublisher.publishEvent(placeOrderTopic, jsonString);
				logger.info("publish directOrderEvent " + jsonString);

				policyApplied = true;
			} else {

				Map<String, Object> policyParams = new HashMap<String, Object>();
				policyParams.put("discountStartTime", discountStartTime.toString());
				policyParams.put("discountEndTime", discountEndTime.toString());

				ScheduleOrderEvent scheduleOrderEvent = new ScheduleOrderEvent(supplierEventBean,
						supplierPartsEventBeanList, policyParams);
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(scheduleOrderEvent);

				eventPublisher.publishEvent(scheduleOrderTopic, jsonString);
				logger.info("publish scheduleOrderEvent " + jsonString);

				policyApplied = true;
			}
		} catch (Exception e) {
			throw new ServiceException("applyPolicy", "Error while applying policy");
		}
		return policyApplied;

	}

	private boolean isDiscountWindowOnGoing(LocalTime time) {
		if (discountStartTime.isBefore(discountEndTime)) {
			return time.isAfter(discountStartTime) && time.isBefore(discountEndTime);
		} else {
			return time.isAfter(discountStartTime) || time.isBefore(discountEndTime);
		}
	}

}
