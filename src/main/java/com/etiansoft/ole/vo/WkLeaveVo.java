package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class WkLeaveVo implements Vo<WkLeave> {

	/** 请假ID **/
	private Integer leaveId;
	/** 开始日期 **/
	private String startDate;
	/** 开始日期是上午还是下午 **/
	private boolean startDateSlot;
	/** 结束日期 **/
	private String endDate;
	/** 结束日期是上午还是下午 **/
	private boolean endDateSlot;
	/** 请假时长 **/
	private Double duration;
	/** 状态 **/
	private Integer status;
	/** 申请人编号 **/
	private String applicantCode;
	/** 申请人名称 **/
	private String applicantName;
	/** 备注 **/
	private String notes;
	/** 剩余请假天数 **/
	private Double surplusLeaveDays;

	public Integer getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public boolean isStartDateSlot() {
		return startDateSlot;
	}

	public void setStartDateSlot(boolean startDateSlot) {
		this.startDateSlot = startDateSlot;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isEndDateSlot() {
		return endDateSlot;
	}

	public void setEndDateSlot(boolean endDateSlot) {
		this.endDateSlot = endDateSlot;
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

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getSurplusLeaveDays() {
		return surplusLeaveDays;
	}

	public void setSurplusLeaveDays(Double surplusLeaveDays) {
		this.surplusLeaveDays = surplusLeaveDays;
	}

	@Override
	public void from(WkLeave po) {
		setLeaveId(po.getLeaveId());
		setStartDate(DateTool.formatDate(po.getStartDate()));
		setEndDate(DateTool.formatDate(po.getEndDate()));
		setStartDateSlot(po.isStartDateSlot());
		setEndDateSlot(po.isEndDateSlot());
		setStatus(po.getStatus());
		setDuration(po.getDuration());
		setNotes(po.getNotes());

		SysUser sysUser = po.getApplicant();
		if (sysUser != null) {
			setApplicantCode(sysUser.getUserCode());
			setApplicantName(sysUser.getName());
		}
	}

	@Override
	public void to(WkLeave po) {

	}

}