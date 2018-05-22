package com.etiansoft.ole.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/** 项目(PrProject) **/
public class PrProject implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 项目编号 **/
	private String projectCode;
	/** 客户编号 **/
	private Customer customer;
	/** 案件名称 **/
	private String name;
	/** 开案人 **/
	private SysUser openStaff;
	/** 接案时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date openTime;
	/** 执行时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date executionTime;
	/** 结案时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closeTime;
	/** 状态 **/
	private Integer status;
	/** 备注 **/
	private String notes;
	/** 创建人 **/
	private SysUser createStaff;
	/** 修改人 **/
	private SysUser updateStaff;
	/** 创建日期 **/
	private Date createTime;
	/** 修改日期 **/
	private Date updateTime;
	/** 是否开具发票 **/
	private Boolean invoice;
	/** 收款金额 **/
	private String amountReceivable;
	/** 收款日期 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date collectionDays;
	/** 是否到账 **/
	private String toAccount;
	/** 发票金额 **/
	private Double invoiceAmount;
	/** 审批人**/
	private String approvalOfPersonnel;
	/** 审批人名称**/
	private String approvalOfPersonnelName;
	/** 税点**/
	private Integer taxRate;
	

	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	public String getApprovalOfPersonnelName() {
		return approvalOfPersonnelName;
	}

	public void setApprovalOfPersonnelName(String approvalOfPersonnelName) {
		this.approvalOfPersonnelName = approvalOfPersonnelName;
	}

	public String getApprovalOfPersonnel() {
		return approvalOfPersonnel;
	}

	public void setApprovalOfPersonnel(String approvalOfPersonnel) {
		this.approvalOfPersonnel = approvalOfPersonnel;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SysUser getOpenStaff() {
		return openStaff;
	}

	public void setOpenStaff(SysUser openStaff) {
		this.openStaff = openStaff;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public SysUser getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(SysUser createStaff) {
		this.createStaff = createStaff;
	}

	public SysUser getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(SysUser updateStaff) {
		this.updateStaff = updateStaff;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getInvoice() {
		return invoice;
	}

	public void setInvoice(Boolean invoice) {
		this.invoice = invoice;
	}

	public String getAmountReceivable() {
		return amountReceivable;
	}

	public void setAmountReceivable(String amountReceivable) {
		this.amountReceivable = amountReceivable;
	}

	public Date getCollectionDays() {
		return collectionDays;
	}

	public void setCollectionDays(Date collectionDays) {
		this.collectionDays = collectionDays;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
}