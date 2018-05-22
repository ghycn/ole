package com.etiansoft.ole.po;

import java.util.Date;

/** 客户联络表(CustomerContact) **/
public class CustomerContact {

	/** 客户联络表ID **/
	private Integer customerContactId;
	/** 客户编号 **/
	private Customer customerCode;
	/** 联络人名称 **/
	private String customContactName;
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

	public Integer getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(Integer customerContactId) {
		this.customerContactId = customerContactId;
	}

	public Customer getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(Customer customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomContactName() {
		return customContactName;
	}

	public void setCustomContactName(String customContactName) {
		this.customContactName = customContactName;
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
