package com.etiansoft.ole.po;

import java.util.Date;
import java.util.Set;

/** 成本分析单(CostList) **/
public class CostList {

	/** 案件编号**/
	private String costListCode;
	/** 案件名称 **/
	private String name;
	/** 案件执行时间 **/
	private Date exetime;
	/** 总金额 **/
	private Double total;
	/** 案件主负责人 **/
	private String personliable;
	/** 案件次负责人 **/
	private String secondPerson;
	/** 对方发票抬头 **/
	private String invoiceTitle;
	/** 对方公司地址 **/
	private String invoiceAddress;
	/** 对接人姓名 **/
	private String dockingName;
	/** 对接人电话 **/
	private String dockingTel;
	/** 活动编号 **/
	private Activity activityCode;

	private Set<CostListItem> itemCode;

	public String getCostListCode() {
		return costListCode;
	}

	public void setCostListCode(String costListCode) {
		this.costListCode = costListCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getExetime() {
		return exetime;
	}

	public void setExetime(Date exetime) {
		this.exetime = exetime;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getPersonliable() {
		return personliable;
	}

	public void setPersonliable(String personliable) {
		this.personliable = personliable;
	}

	public String getSecondPerson() {
		return secondPerson;
	}

	public void setSecondPerson(String secondPerson) {
		this.secondPerson = secondPerson;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getDockingName() {
		return dockingName;
	}

	public void setDockingName(String dockingName) {
		this.dockingName = dockingName;
	}

	public String getDockingTel() {
		return dockingTel;
	}

	public void setDockingTel(String dockingTel) {
		this.dockingTel = dockingTel;
	}


	public Activity getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Activity activityCode) {
		this.activityCode = activityCode;
	}

	public Set<CostListItem> getItemCode() {
		return itemCode;
	}

	public void setItemCode(Set<CostListItem> itemCode) {
		this.itemCode = itemCode;
	}

}
