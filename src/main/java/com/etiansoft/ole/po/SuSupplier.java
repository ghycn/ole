package com.etiansoft.ole.po;

import java.util.List;

/** 厂商(SuSupplier) **/
public class SuSupplier {

	/** 厂商编号 **/
	private Integer supplierCode;
	/** 名称 **/
	private String name;
	/** 分类 **/
	private String type;
	/** 银行账号 **/
	private String bankAccount;
	/** 银行户名 **/
	private String accountName;
	/** 开户行 **/
	private String bankName;
	/** 支行名称 **/
	private String branchName;
	/** 所在地 **/
	private String address;
	/** 官网 **/
	private String officialWebsite;
	/** 备注 **/
	private String note;
	/** 状态 **/
	private Integer status;
	/** 确定删除的状态（0：已删除 1：未删除） **/
	private Boolean remove;

	private List<SuSupplierContact> contacts;

	public Integer getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(Integer supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<SuSupplierContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<SuSupplierContact> contacts) {
		this.contacts = contacts;
	}

	public Integer getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}
}