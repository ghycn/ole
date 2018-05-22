package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.BadAccount;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class BadAccountVo implements Vo<BadAccount> {

	/** 坏账ID **/
	private Integer badId;
	/** 项目编号 **/
	private String projectCode;
	/** 项目名称 **/
	private String projectName;
	/** 坏账原因 **/
	private String reason;
	/** 金额 **/
	private Double total;
	/** 填写人 **/
	private String people;
	/** 创建时间 **/
	private String createTime;
	/** 备注 **/
	private String note;
	/** 审批状态 **/
	private Integer status;
	/** 报价编号 **/
	private Integer quotationId;
	/** 报价名称 **/
	private String quotationName;
	/** 税点**/
	private Integer taxRate;
	
	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	public Integer getBadId() {
		return badId;
	}

	public void setBadId(Integer badId) {
		this.badId = badId;
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

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getQuotationName() {
		return quotationName;
	}

	public void setQuotationName(String quotationName) {
		this.quotationName = quotationName;
	}

	@Override
	public void from(BadAccount po) {
		setBadId(po.getBadId());
		setReason(po.getReason());
		setTotal(po.getTotal());
		setCreateTime(DateTool.formatDate(po.getCreateTime()));
		setStatus(po.getStatus());
		setNote(po.getNote());
		PrProject project = po.getProjectCode();
		if (project != null) {
			setProjectCode(project.getProjectCode());
			setProjectName(project.getName());
			setTaxRate(project.getTaxRate());
		}
		Quotation quotation = po.getQuotation();
		if (quotation != null) {
			setQuotationId(quotation.getQuotationId());
			setQuotationName(quotation.getPrProject().getName() + '-' + quotation.getActivity().getName());
			setTaxRate(quotation.getPrProject().getTaxRate());
		}
		SysUser applicant = po.getApplicant();
		if (applicant != null) {
			setPeople(applicant.getName());
		}
	}

	@Override
	public void to(BadAccount po) {

	}
}