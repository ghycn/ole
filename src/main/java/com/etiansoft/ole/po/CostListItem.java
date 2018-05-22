package com.etiansoft.ole.po;

/** 成本分析单子项(CostListItem) **/
public class CostListItem {

	/** 编号 **/
	private String itemCode;
	/** 报价项目名 **/
	private String name;
	/** 报价 **/
	private Double quotation;
	/** 制作厂家 **/
	private String producFactory;
	/** 对接人 **/
	private String person;
	/** 含税报价 **/
	private Double taxQuotation;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQuotation() {
		return quotation;
	}

	public void setQuotation(Double quotation) {
		this.quotation = quotation;
	}

	public String getProducFactory() {
		return producFactory;
	}

	public void setProducFactory(String producFactory) {
		this.producFactory = producFactory;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Double getTaxQuotation() {
		return taxQuotation;
	}

	public void setTaxQuotation(Double taxQuotation) {
		this.taxQuotation = taxQuotation;
	}
}
