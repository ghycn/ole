package com.etiansoft.ole.po;

import java.util.Date;

/** 厂商联络表(SuSupplierContact) **/
public class SuSupplierContact {

	/** 厂商联络表ID **/
	private Integer supplierContactId;
	/** 厂商编号 **/
	private SuSupplier supplierCode;
	/** 联络人名称 **/
	private String name;
	/** 座机 **/
	private String tel;
	/** 分机 **/
	private String extension;
	/** 手机 **/
	private String mobile;
	/** 邮箱 **/
	private String email;
	/** 邮编 **/
	private String postCode;
	/** 地址 **/
	private String address;
	/** 创建时间 **/
	private Date createTime;

	public Integer getSupplierContactId() {
		return supplierContactId;
	}

	public void setSupplierContactId(Integer supplierContactId) {
		this.supplierContactId = supplierContactId;
	}

	public SuSupplier getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(SuSupplier supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
