package com.bgarage.ims.inventory.crudport.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PartsDTOList implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<PartsDTO> partsResponseBeans = new ArrayList<PartsDTO>();

	public PartsDTOList() {
	}

	public List<PartsDTO> getPartsResponseBeans() {
		return partsResponseBeans;
	}

	public void setPartsResponseBeans(List<PartsDTO> partsResponseBeans) {
		this.partsResponseBeans = partsResponseBeans;
	}

}
