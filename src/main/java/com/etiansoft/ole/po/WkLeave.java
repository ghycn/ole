package com.etiansoft.ole.po;

import java.util.Date;

/** 请假(Wk_Leave) **/
public class WkLeave {

	/** 请假ID **/
	private Integer leaveId;
	/** 申请人 **/
	private SysUser applicant;
	/** 开始日期 **/
	private Date startDate;
	/** 开始日期是上午还是下午 **/
	private boolean startDateSlot;
	/** 结束日期 **/
	private Date endDate;
	/** 结束日期是上午还是下午 **/
	private boolean endDateSlot;
	/** 请假时长 **/
	private Double duration;
	/** 状态 **/
	private Integer status;
	/** 备注 **/
	private String notes;

	public Integer getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}

	public SysUser getApplicant() {
		return applicant;
	}

	public void setApplicant(SysUser applicant) {
		this.applicant = applicant;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isStartDateSlot() {
		return startDateSlot;
	}

	public void setStartDateSlot(boolean startDateSlot) {
		this.startDateSlot = startDateSlot;
	}

	public boolean isEndDateSlot() {
		return endDateSlot;
	}

	public void setEndDateSlot(boolean endDateSlot) {
		this.endDateSlot = endDateSlot;
	}
}