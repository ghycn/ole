package com.etiansoft.ole.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/** 报价(Quotation) **/
public class Quotation implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 报价ID **/
	private Integer quotationId;
	/** 活动编号 **/
	private Activity activity;
	/** 项目编号 **/
	private PrProject prProject;
	/** 报价项 **/
	private Set<QuotationList> quotationList;
	/** 客户 **/
	private Customer customer;
	/** 联系人 **/
	private String contact;
	/** 座机 **/
	private String tel;
	/** 申请人 **/
	private SysUser applicant;
	/** 开案时间 **/
	private Date caseTime;
	/** 含税小计 **/
	private Double taxTotal;
	/** 电话 **/
	private String phone;
	/** 日期 **/
	private Date date;
	/** 传真 **/
	private String fax;
	/** 地址 **/
	private String address;
	/** 状态 **/
	private Integer status;
	/** 备注 **/
	private String note;
	/** 发票金额 **/
	private Double invoiceAmount;
	/** 杂志费和公关费的请款 **/
	private Set<OtherPFProjectFee> otherPFProjectFees;
	/** 请款小计 **/
	private Double amountTotal;
	/** 成本分析单状态 **/
	private Integer costStatus;
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public PrProject getPrProject() {
		return prProject;
	}

	public void setPrProject(PrProject prProject) {
		this.prProject = prProject;
	}

	public Set<QuotationList> getQuotationList() {
		return quotationList;
	}

	public void setQuotationList(Set<QuotationList> quotationList) {
		this.quotationList = quotationList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}

	public Date getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Set<OtherPFProjectFee> getOtherPFProjectFees() {
		return otherPFProjectFees;
	}

	public void setOtherPFProjectFees(Set<OtherPFProjectFee> otherPFProjectFees) {
		this.otherPFProjectFees = otherPFProjectFees;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Integer getCostStatus() {
		return costStatus;
	}

	public void setCostStatus(Integer costStatus) {
		this.costStatus = costStatus;
	}
	
}