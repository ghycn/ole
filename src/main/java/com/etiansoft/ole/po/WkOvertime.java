package com.etiansoft.ole.po;

import java.util.Date;

/** 加班(Wk_Overtime) **/
public class WkOvertime {

	/** 加班ID **/
	private Integer overtimeId;
	/** 用户编码 **/
	private SysUser applicant;
	/** 开始日期 **/
	private Date startDate;
	/** 开始日期是上午还是下午 **/
	private boolean startDateSlot;
	/** 结束日期 **/
	private Date endDate;
	/** 结束日期是上午还是下午 **/
	private boolean endDateSlot;
	/** 加班时长 **/
	private Double duration;
	/** 状态 **/
	private Integer status;
	/** 备注 **/
	private String notes;
	/** 审批人**/
	private String approvalOfPersonnel;
	/** 审批人名称**/
	private String approvalOfPersonnelName;
	public Integer getOvertimeId() {
		return overtimeId;
	}

	public void setOvertimeId(Integer overtimeId) {
		this.overtimeId = overtimeId;
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

	public String getApprovalOfPersonnel() {
		return approvalOfPersonnel;
	}

	public void setApprovalOfPersonnel(String approvalOfPersonnel) {
		this.approvalOfPersonnel = approvalOfPersonnel;
	}

	public String getApprovalOfPersonnelName() {
		return approvalOfPersonnelName;
	}

	public void setApprovalOfPersonnelName(String approvalOfPersonnelName) {
		this.approvalOfPersonnelName = approvalOfPersonnelName;
	}
}