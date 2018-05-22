package com.etiansoft.ole.po;

/** 系统配置(SysConfiguration) **/
public class SysConfiguration {

	private Integer id;
	private String taxRate;
	private Integer isUsed;
	
	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
}