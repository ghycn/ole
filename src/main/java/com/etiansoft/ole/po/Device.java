package com.etiansoft.ole.po;

import java.util.Date;

/** 设备表(Device) **/
public class Device {

	/** 设备编号 **/
	private Integer deviceCode;
	/** 名称 **/
	private String name;
	/** 总数量 **/
	private Integer total;
	/** 员工持有数量 **/
	private Integer staffHoldNum;
	/** 库存数量 **/
	private Integer stockNum;
	/** 购买时间 **/
	private Date buyingTime;
	/** 到期日期 **/
	private Date expirationDate;
	/** 确定删除的状态（0：已删除 1：未删除） **/
	private Boolean remove;

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStaffHoldNum() {
		return staffHoldNum;
	}

	public void setStaffHoldNum(Integer staffHoldNum) {
		this.staffHoldNum = staffHoldNum;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Date getBuyingTime() {
		return buyingTime;
	}

	public void setBuyingTime(Date buyingTime) {
		this.buyingTime = buyingTime;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}
}