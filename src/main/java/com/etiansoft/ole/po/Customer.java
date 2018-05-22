package com.etiansoft.ole.po;

import java.util.List;

/** 客户表(Customer) **/
public class Customer {

	/** 客户联络表ID **/
	private String customerCode;
	/** 分離 **/
	private String type;
	/** 状态 **/
	private Integer status;
	/** 客户名称 **/
	private String name;
	/** 所在地 **/
	private String customerAddress;
	/** 官網 **/
	private String officialWebsite;
	/** 备注 **/
	private String note;
	/** 确定删除的状态（0：已删除 1：未删除） **/
	private Boolean remove;

	private List<CustomerContact> contacts;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<CustomerContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CustomerContact> contacts) {
		this.contacts = contacts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}
}