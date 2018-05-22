package com.etiansoft.ole.po;

import java.util.Date;
import java.util.Set;

/** 项目款(PFProjectFee) **/
public class PFProjectFee {

	/** 项目款ID **/
	private Integer projectFeeId;
	/** 项目编号 **/
	private PrProject prProject;
	/** 报价编号 **/
	private Quotation quotation;
	/** 名称 **/
	private String name;
	/** 请款人 **/
	private SysUser applicant;
	/** 含税金额 **/
	private Double amount;
	/** 申请日期 **/
	private Date applyDate;
	/** 付款状态 (1已付款，0未付款) **/
	private Integer payStatus;
	/** 状态 (0申请,1通过,2驳回,3废弃,4关账) **/
	private Integer status;
	/** 备注 **/
	private String notes;
	/** 用户人 **/
	private SysUser createStaff;
	/** 创建时间 **/
	private Date createTime;
	/** 厂商名称 **/
	private SuSupplier supplier;
	/** 请款类型 **/
	private Set<QuotationList> quotationLists;
	
	/** 其它类型编号 **/
	private Integer typeId;
	/** 其它类型名称 **/
	private String typeName;
	public Integer getProjectFeeId() {
		return projectFeeId;
	}

	public void setProjectFeeId(Integer projectFeeId) {
		this.projectFeeId = projectFeeId;
	}

	public PrProject getPrProject() {
		return prProject;
	}

	public void setPrProject(PrProject prProject) {
		this.prProject = prProject;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
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

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}

	public SysUser getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(SysUser createStaff) {
		this.createStaff = createStaff;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SuSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SuSupplier supplier) {
		this.supplier = supplier;
	}

	public Set<QuotationList> getQuotationLists() {
		return quotationLists;
	}

	public void setQuotationLists(Set<QuotationList> quotationLists) {
		this.quotationLists = quotationLists;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}