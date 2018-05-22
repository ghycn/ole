package com.etiansoft.ole.po;

import java.util.Set;

/** 报价项(QuotationList) **/
public class QuotationList {

	/** 报价项ID **/
	private Integer quotationListId;
	/** 類別 **/
	private String category;
	/** 报价项 **/
	private String item;
	/** 尺寸 **/
	private String size;
	/** 数量 **/
	private Integer quantity;
	/** 单价 **/
	private Double unitPrice;
	/** 小计 **/
	private Double subTotal;
	/** 规格 **/
	private String spec;
	/** 备注 **/
	private String note;
	/** 父级 **/
	private Integer parent;
	/** 厂商 **/
	private SuSupplier supplier;
	/** 请款 **/
	private Set<PFProjectFee> projectFees;
	/** 报价**/
    private Integer quotationId;
	/** 厂商code **/
	private Integer supplierCode;
	
	public Integer getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(Integer supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Integer getQuotationListId() {
		return quotationListId;
	}

	public void setQuotationListId(Integer quotationListId) {
		this.quotationListId = quotationListId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public SuSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SuSupplier supplier) {
		this.supplier = supplier;
	}

	public Set<PFProjectFee> getProjectFees() {
		return projectFees;
	}

	public void setProjectFees(Set<PFProjectFee> projectFees) {
		this.projectFees = projectFees;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
	
}