package com.etiansoft.ole.pr.query;

public class ProjectQuery {

	private String projectCode;
	private String projectName;
	private String openStaff;
	private String status;
	private String type;
	private Boolean flag;
	private String state;//邏輯狀態    1表示從待批示案件進入後台 2表示從案件一欄進入後台3表示从请款申请进入
	private String selectDropDown;
	private String projectState;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOpenStaff() {
		return openStaff;
	}

	public void setOpenStaff(String openStaff) {
		this.openStaff = openStaff;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getSelectDropDown() {
		return selectDropDown;
	}

	public void setSelectDropDown(String selectDropDown) {
		this.selectDropDown = selectDropDown;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	
}