package com.etiansoft.ole.quotation.query;

public class QuotationQuery {

	private String prProject;
	private String projectName;
	private String openStaff;
	private Integer status;
	private String applicant;
	private Boolean flag;

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getPrProject() {
		return prProject;
	}

	public void setPrProject(String prProject) {
		this.prProject = prProject;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
}
