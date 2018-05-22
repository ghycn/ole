package com.etiansoft.ole.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/** 设备借用表(DeviceBorrow) **/
public class DeviceBorrow {

	/** 借用编号 **/
	private Integer borrowCode;
	/** 借出个数 **/
	private Integer lendNum;
	/** 申请时间 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyTime;
	/** 借出时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date borrowTime;
	/** 归还时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnTime;
	/** 状态 (true归还, false未归还) **/
	private boolean status;
	/** 借出人 */
	private SysUser applicant;
	/** 设备 */
	private Device device;
	/** 备注 */
	private String notes;

	public Integer getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(Integer borrowCode) {
		this.borrowCode = borrowCode;
	}

	public Integer getLendNum() {
		return lendNum;
	}

	public void setLendNum(Integer lendNum) {
		this.lendNum = lendNum;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}