package com.etiansoft.ole.vo;

import java.util.List;

import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.tools.vo.Vo;

public class QuotationListVo implements Vo<QuotationList> {

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
	/** 厂商名称 **/
	private String supplierName;
	private Integer supplierCode;
	private String contactName;
	private String contactMobile;
	private double amount;
	private List<QuotationListVo> children;

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

	public Integer getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(Integer supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<QuotationListVo> getChildren() {
		return children;
	}

	public void setChildren(List<QuotationListVo> children) {
		this.children = children;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public void from(QuotationList po) {
		setQuotationListId(po.getQuotationListId());
		setCategory(po.getCategory());
		setQuantity(po.getQuantity());
		setItem(po.getItem());
		setNote(po.getNote());
		setSize(po.getSize());
		setSpec(po.getSpec());
		setUnitPrice(po.getUnitPrice());
		setSubTotal(po.getSubTotal());
		setParent(po.getParent());
		SuSupplier supplier = po.getSupplier();
		if (supplier != null) {
			setSupplierCode(supplier.getSupplierCode());
			setSupplierName(supplier.getName());
		}
	}

	@Override
	public void to(QuotationList po) {

	}
}