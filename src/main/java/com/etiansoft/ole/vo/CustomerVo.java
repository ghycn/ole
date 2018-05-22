package com.etiansoft.ole.vo;

import com.etiansoft.ole.annotation.Cell;
import com.etiansoft.ole.annotation.Sheet;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.tools.vo.Vo;

@Sheet(name = "客戶一覽")
public class CustomerVo implements Vo<Customer> {

	/** 客户联络表ID **/
	@Cell(name = "客户联络表ID")
	private String customerCode;
	/** 分離 **/
	@Cell(name = "分類 ")
	private String type;
	/** 状态 **/
	private Integer status;
	/** 客户名称 **/
	@Cell(name = "客户名称")
	private String name;
	/** 所在地 **/
	@Cell(name = "所在地")
	private String customerAddress;
	/** 官網 **/
	@Cell(name = "官網 ")
	private String officialWebsite;
	/** 备注 **/
	@Cell(name = "备注")
	private String note;
	/** 确定删除的状态（0：已删除 1：未删除） **/
	private Boolean remove;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

	@Override
	public void from(Customer po) {
		setCustomerCode(po.getCustomerCode());
		setName(po.getName());
		setOfficialWebsite(po.getOfficialWebsite());
		setType(po.getType());
		setCustomerAddress(po.getCustomerAddress());
		setStatus(po.getStatus());
		setNote(po.getNote());
		setRemove(po.getRemove());
	}

	@Override
	public void to(Customer po) {

	}

}
