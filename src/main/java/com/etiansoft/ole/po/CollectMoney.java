package com.etiansoft.ole.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/** 收款小計(CollectMoney) **/
/**
 * @author Administrator
 * 
 */
public class CollectMoney {

	/** 收款编号 **/
	private Integer collectId;
	/** 案件 **/
	private PrProject project;
	/** 报价 **/
	private Quotation quotaion;
	/** 活动名称 **/
	private String activityName;
	/** 是否开具发票 **/
	private Boolean invoice;
	/** 收款金额 **/
	private Double amount;
	/** 收款日期 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	/** 是否到账 **/
	private Boolean toAccount;
	/** 备注 **/
	private String notes;
	/** 申请人 **/
	private SysUser applicant;

	public Integer getCollectId() {
		return collectId;
	}

	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}

	public PrProject getProject() {
		return project;
	}

	public void setProject(PrProject project) {
		this.project = project;
	}

	public Quotation getQuotaion() {
		return quotaion;
	}

	public void setQuotaion(Quotation quotaion) {
		this.quotaion = quotaion;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Boolean getInvoice() {
		return invoice;
	}

	public void setInvoice(Boolean invoice) {
		this.invoice = invoice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getToAccount() {
		return toAccount;
	}

	public void setToAccount(Boolean toAccount) {
		this.toAccount = toAccount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}
}