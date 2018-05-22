package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.CollectMoney;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class CollectMoneyVo implements Vo<CollectMoney> {

	/** 收款编号 **/
	private Integer collectId;
	/** 是否开具发票 **/
	private Boolean invoice;
	/** 收款金额 **/
	private Double amount;
	/** 收款日期 **/
	private String date;
	/** 是否到账 **/
	private Boolean toAccount;
	/** 备注 **/
	private String notes;
	/** 案件编号 **/
	private String projectCode;
	/** 案件名称 **/
	private String projectName;
	/** 报价编号 **/
	private Integer quotationId;
	/** 报价单名称 **/
	private String quotationName;

	public Integer getCollectId() {
		return collectId;
	}

	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	public void from(CollectMoney po) {
		setCollectId(po.getCollectId());
		setAmount(po.getAmount());
		setDate(DateTool.formatDate(po.getDate()));
		setInvoice(po.getInvoice());
		setNotes(po.getNotes());
		setToAccount(po.getToAccount());
		PrProject project = po.getProject();
		if (project != null) {
			setProjectCode(project.getProjectCode());
			setProjectName(project.getName());
		}
		Quotation quotation = po.getQuotaion();
		if (quotation != null) {
			setQuotationName(quotation.getPrProject().getName() + '-' + quotation.getActivity().getName());
		}
	}

	@Override
	public void to(CollectMoney po) {

	}

}