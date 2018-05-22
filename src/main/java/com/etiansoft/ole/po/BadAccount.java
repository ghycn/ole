package com.etiansoft.ole.po;

import java.util.Date;

/** 坏账(Bad_Account) **/
public class BadAccount {

	/** 坏账ID **/
	private Integer badId;
	/** 项目编号 **/
	private PrProject projectCode;
	/** 报价 **/
	private Quotation quotation;
	/** 坏账原因 **/
	private String reason;
	/** 金额 **/
	private Double total;
	/** 申请人 **/
	private SysUser applicant;
	/** 创建时间 **/
	private Date createTime;
	/** 备注 **/
	private String note;
	/** 审批状态 **/
	private Integer status;

	public Integer getBadId() {
		return badId;
	}

	public void setBadId(Integer badId) {
		this.badId = badId;
	}

	public PrProject getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(PrProject projectCode) {
		this.projectCode = projectCode;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}