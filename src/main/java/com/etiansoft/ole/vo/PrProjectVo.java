package com.etiansoft.ole.vo;


import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class PrProjectVo implements Vo<PrProject> {

	/** 项目编号 **/
	private String projectCode;
	/** 案件名称 **/
	private String name;
	/** 接案时间 **/
	private String openTime;
	/** 结案时间 **/
	private String closeTime;
	/** 执行时间 **/
	private String executionTime;
	/** 状态 **/
	private Integer status;
	/** 备注 **/
	private String notes;
	/** 发票金额 **/
	private Double invoiceAmount;
	/** 客户编号 **/
	private String customerCode;
	/** 客户名称 **/
	private String customerName;
	/** 开案人编号 **/
	private String openStaffCode;
	/** 开案人名称 **/
	private String openStaffName;
	/** 收款金额 **/
	private String amountReceivable;
	/** 收款日期 **/
	private String collectionDays;
	/** 审批人 **/
	private String approvalOfPersonnel;
	/** 审批人 姓名**/
	private String approvalOfPersonnelName;
	/** 税点**/
	private Integer taxRate;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
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

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOpenStaffCode() {
		return openStaffCode;
	}

	public void setOpenStaffCode(String openStaffCode) {
		this.openStaffCode = openStaffCode;
	}

	public String getOpenStaffName() {
		return openStaffName;
	}

	public void setOpenStaffName(String openStaffName) {
		this.openStaffName = openStaffName;
	}

	public String getAmountReceivable() {
		return amountReceivable;
	}

	public void setAmountReceivable(String amountReceivable) {
		this.amountReceivable = amountReceivable;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getCollectionDays() {
		return collectionDays;
	}

	public void setCollectionDays(String collectionDays) {
		this.collectionDays = collectionDays;
	}

	@Override
	public void from(PrProject po) {
		setProjectCode(po.getProjectCode());
		setName(po.getName());
		setInvoiceAmount(po.getInvoiceAmount());
		setNotes(po.getNotes());
		setOpenTime(DateTool.formatDate(po.getOpenTime()));
		setCloseTime(DateTool.formatDate(po.getCloseTime()));
		setStatus(po.getStatus());
		setAmountReceivable(po.getAmountReceivable());
		setCollectionDays(DateTool.formatDate(po.getCollectionDays()));
		setExecutionTime(DateTool.formatDate(po.getExecutionTime()));
		setApprovalOfPersonnel(po.getApprovalOfPersonnel());
		setApprovalOfPersonnelName(po.getApprovalOfPersonnelName());
		setTaxRate(po.getTaxRate());
		Customer customer = po.getCustomer();
		if (customer != null) {
			setCustomerCode(customer.getCustomerCode());
			setCustomerName(customer.getName());
		}
		SysUser sysUser = po.getOpenStaff();
		if (sysUser != null) {
			setOpenStaffCode(sysUser.getUserCode());
			setOpenStaffName(sysUser.getName());
		}
	}


	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public void to(PrProject po) {

	}
}
