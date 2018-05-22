package com.etiansoft.ole.po;

import java.util.List;

public class CostAnalysis {
	private Integer costAnalysisId;
	private PrProject project;
	private Quotation quotation;
	private String contractNo;
	private Double totalAmount;
	private Double totalCost;
	private Double grossProfit;
	private String profitRate;
	private Double noTaxQuotation;
	private Double taxQuotation;
	private Double outCost;
	private Double inCost;
	private Double invoiceTax;
	private Double enterpriseTax;
	private Integer desingDays;
	private Double desingAmount;
	private Integer activeSupportDays;
	private Double activeSupportAmount;
	private Double otherAmount;
	private Double relationAmount;
	private Integer status;
	private String notes;
	private List<CostAnalysisItem> costAnalysisItems;

	public Integer getCostAnalysisId() {
		return costAnalysisId;
	}

	public void setCostAnalysisId(Integer costAnalysisId) {
		this.costAnalysisId = costAnalysisId;
	}

	public PrProject getProject() {
		return project;
	}

	public void setProject(PrProject project) {
		this.project = project;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public String getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public Double getNoTaxQuotation() {
		return noTaxQuotation;
	}

	public void setNoTaxQuotation(Double noTaxQuotation) {
		this.noTaxQuotation = noTaxQuotation;
	}

	public Double getTaxQuotation() {
		return taxQuotation;
	}

	public void setTaxQuotation(Double taxQuotation) {
		this.taxQuotation = taxQuotation;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	public Double getInCost() {
		return inCost;
	}

	public void setInCost(Double inCost) {
		this.inCost = inCost;
	}

	public Double getInvoiceTax() {
		return invoiceTax;
	}

	public void setInvoiceTax(Double invoiceTax) {
		this.invoiceTax = invoiceTax;
	}

	public Double getEnterpriseTax() {
		return enterpriseTax;
	}

	public void setEnterpriseTax(Double enterpriseTax) {
		this.enterpriseTax = enterpriseTax;
	}

	public Integer getDesingDays() {
		return desingDays;
	}

	public void setDesingDays(Integer desingDays) {
		this.desingDays = desingDays;
	}

	public Double getDesingAmount() {
		return desingAmount;
	}

	public void setDesingAmount(Double desingAmount) {
		this.desingAmount = desingAmount;
	}

	public Integer getActiveSupportDays() {
		return activeSupportDays;
	}

	public void setActiveSupportDays(Integer activeSupportDays) {
		this.activeSupportDays = activeSupportDays;
	}

	public Double getActiveSupportAmount() {
		return activeSupportAmount;
	}

	public void setActiveSupportAmount(Double activeSupportAmount) {
		this.activeSupportAmount = activeSupportAmount;
	}

	public Double getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Double getRelationAmount() {
		return relationAmount;
	}

	public void setRelationAmount(Double relationAmount) {
		this.relationAmount = relationAmount;
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

	public List<CostAnalysisItem> getCostAnalysisItems() {
		return costAnalysisItems;
	}

	public void setCostAnalysisItems(List<CostAnalysisItem> costAnalysisItems) {
		this.costAnalysisItems = costAnalysisItems;
	}
}