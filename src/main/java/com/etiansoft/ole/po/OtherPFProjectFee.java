package com.etiansoft.ole.po;

import java.io.Serializable;

public class OtherPFProjectFee implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer otherTypeId;
	private PFProjectFee pfProjectFee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOtherTypeId() {
		return otherTypeId;
	}

	public void setOtherTypeId(Integer otherTypeId) {
		this.otherTypeId = otherTypeId;
	}

	public PFProjectFee getPfProjectFee() {
		return pfProjectFee;
	}

	public void setPfProjectFee(PFProjectFee pfProjectFee) {
		this.pfProjectFee = pfProjectFee;
	}
}