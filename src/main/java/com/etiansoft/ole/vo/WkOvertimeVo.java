package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkOvertime;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

/** 加班(Wk_Overtime) **/
public class WkOvertimeVo implements Vo<WkOvertime> {

	/** 加班ID **/
	private Integer overtimeId;
	/** 开始日期 **/
	private String startDate;
	/** 结束日期 **/
	private String endDate;
	/** 加班时长 **/
	private Double duration;
	/** 状态 **/
	private Integer status;
	/** 申请人编号 **/
	private String applicantCode;
	/** 申请人姓名 **/
	private String applicantName;
   /** 备注 **/
	private String notes;
	
	private String approvalOfPersonnelName;//审批人名称
	private String approvalOfPersonnel;//审批人id
	


	public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}

	public Integer getOvertimeId() {
		return overtimeId;
	}

	public void setOvertimeId(Integer overtimeId) {
		this.overtimeId = overtimeId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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
	
	public String getApprovalOfPersonnelName() {
		return approvalOfPersonnelName;
	}

	public void setApprovalOfPersonnelName(String approvalOfPersonnelName) {
		this.approvalOfPersonnelName = approvalOfPersonnelName;
	}
	
	public String getApprovalOfPersonnel() {
		return approvalOfPersonnel;
	}

	public void setApprovalOfPersonnel(String approvalOfPersonnel) {
		this.approvalOfPersonnel = approvalOfPersonnel;
	}

	@Override
	public void from(WkOvertime po) {
		setOvertimeId(po.getOvertimeId());
		setStartDate(DateTool.formatDate(po.getStartDate()));
		setEndDate(DateTool.formatDate(po.getEndDate()));
		setStatus(po.getStatus());
		setDuration(po.getDuration());
		setNotes(po.getNotes());
		setApprovalOfPersonnelName(po.getApprovalOfPersonnelName());
		setApprovalOfPersonnel(po.getApprovalOfPersonnel());
		SysUser sysUser = po.getApplicant();
		if (sysUser != null) {
			setApplicantCode(sysUser.getUserCode());
			setApplicantName(sysUser.getName());
		}
	}

	@Override
	public void to(WkOvertime po) {

	}
}