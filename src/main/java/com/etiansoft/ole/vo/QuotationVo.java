package com.etiansoft.ole.vo;

import java.io.Serializable;

import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class QuotationVo implements Serializable, Vo<Quotation> {

	private static final long serialVersionUID = 1L;
	/** 报价ID **/
	private Integer quotationId;
	/** 活动编号 **/
	private String activityCode;
	/** 客户名称 **/
	private String customerName;
	/** 申请人 **/
	private String applicant;
	/** 日期 **/
	private String date;
	/** 开案时间 **/
	private String caseTime;
	/** 含税小计 **/
	private Double taxTotal;
	/** 状态 **/
	private Integer status;
	/** 成本分析单状态 **/
	private Integer costStatus;
	/** 备注 **/
	private String note;
	/** 项目编号 **/
	private String projectCode;
	/** 项目名称 **/
	private String projectName;
	/** 报价名称 **/
	private String quotationName;
	/** 请款小计 **/
	private Double amountTotal;
	/** 税点 **/
	private Integer taxRate;
	/** 审批人 **/
	private String approvalOfPersonnel;
	/** 审批人 姓名**/
	private String approvalOfPersonnelName;
	/**提交人编号**/
	private String applicantCode;
	

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(String caseTime) {
		this.caseTime = caseTime;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCostStatus() {
		return costStatus;
	}

	public void setCostStatus(Integer costStatus) {
		this.costStatus = costStatus;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getQuotationName() {
		return quotationName;
	}

	public void setQuotationName(String quotationName) {
		this.quotationName = quotationName;
	}

	@Override
	public void from(Quotation po) {
		setQuotationId(po.getQuotationId());
		setActivityCode(po.getActivity().getActivityCode());
		setCaseTime(DateTool.formatDate(po.getCaseTime()));
		setDate(DateTool.formatDate(po.getDate()));
		double str = po.getTaxTotal()==null?0:po.getTaxTotal();
		setTaxTotal(str);
		setStatus(po.getStatus());
		setNote(po.getNote());
		setCostStatus(po.getCostStatus());
		Customer customer = po.getCustomer();
		if (customer != null) {
			setCustomerName(customer.getName());
		}
		PrProject project = po.getPrProject();
		setProjectCode(project.getProjectCode());
		setProjectName(project.getName());
		setQuotationName(po.getActivity().getName());
		setTaxRate(project.getTaxRate());
		setApprovalOfPersonnelName(project.getApprovalOfPersonnelName());
		setApprovalOfPersonnel(project.getApprovalOfPersonnel());
		SysUser user = po.getApplicant();
		setAmountTotal(po.getAmountTotal());
		if (user != null) {
			setApplicant(user.getName());
			setApplicantCode(user.getUserCode());
		}
	}

	@Override
	public void to(Quotation po) {
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	public String getApprovalOfPersonnel() {
		return approvalOfPersonnel;
	}

	public void setApprovalOfPersonnel(String approvalOfPersonnel) {
		this.approvalOfPersonnel = approvalOfPersonnel;
	}

	public String getApprovalOfPersonnelName() {
		return approvalOfPersonnelName;
	}

	public void setApprovalOfPersonnelName(String approvalOfPersonnelName) {
		this.approvalOfPersonnelName = approvalOfPersonnelName;
	}
	
}