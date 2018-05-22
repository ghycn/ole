package com.etiansoft.ole.po;

public class CostAnalysisItem {
	private Integer costAnalysisItemId;
	private CostAnalysis costAnalysis;
	private QuotationList quotationList;
	private double taxQuotation;

	public Integer getCostAnalysisItemId() {
		return costAnalysisItemId;
	}

	public void setCostAnalysisItemId(Integer costAnalysisItemId) {
		this.costAnalysisItemId = costAnalysisItemId;
	}

	public CostAnalysis getCostAnalysis() {
		return costAnalysis;
	}

	public void setCostAnalysis(CostAnalysis costAnalysis) {
		this.costAnalysis = costAnalysis;
	}

	public QuotationList getQuotationList() {
		return quotationList;
	}

	public void setQuotationList(QuotationList quotationList) {
		this.quotationList = quotationList;
	}

	public double getTaxQuotation() {
		return taxQuotation;
	}

	public void setTaxQuotation(double taxQuotation) {
		this.taxQuotation = taxQuotation;
	}
}