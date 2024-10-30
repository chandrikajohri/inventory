package com.bgarage.ims.inventory.crudport.models;

import java.util.ArrayList;
import java.util.List;

public class ConsumedPartsRequestBean {

	List<ConsumedPart> consumedPartsList = new ArrayList<ConsumedPart>();

	public List<ConsumedPart> getConsumedPartsList() {
		return consumedPartsList;
	}

	public void setConsumedPartsList(List<ConsumedPart> consumedPartsList) {
		this.consumedPartsList = consumedPartsList;
	}

}
