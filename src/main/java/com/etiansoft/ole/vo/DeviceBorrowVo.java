package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.DeviceBorrow;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class DeviceBorrowVo implements Vo<DeviceBorrow> {

	/** 借用编号 **/
	private Integer borrowCode;
	/** 借用编号 **/
	private String name;
	/** 借出时间 **/
	private String borrowTime;
	/** 归还时间 **/
	private String returnTime;
	/** 状态 (true 0归还, false 1未归还) **/
	private boolean status;
	/** 借出人编号 */
	private String applicantCode;
	/** 借出人姓名 */
	private String applicantName;

	public Integer getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(Integer borrowCode) {
		this.borrowCode = borrowCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void from(DeviceBorrow po) {
		setBorrowCode(po.getBorrowCode());
		setBorrowTime(DateTool.formatDate(po.getBorrowTime()));
		setReturnTime(DateTool.formatDate(po.getReturnTime()));
		SysUser application = po.getApplicant();
		setStatus(po.isStatus());
		if (application != null) {
			setApplicantCode(application.getUserCode());
			setApplicantName(application.getName());
		}
	}

	@Override
	public void to(DeviceBorrow po) {

	}

}
