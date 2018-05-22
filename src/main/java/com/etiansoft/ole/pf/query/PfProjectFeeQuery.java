package com.etiansoft.ole.pf.query;

public class PfProjectFeeQuery {

	private String applicant;
	private String prProject;
	private String prProjectName;
	private String type;
	private String amount;
	private Integer status;
	private Boolean flag;
	private Integer typeId;
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrProjectName() {
		return prProjectName;
	}

	public void setPrProjectName(String prProjectName) {
		this.prProjectName = prProjectName;
	}

	public String getPrProject() {
		return prProject;
	}

	public void setPrProject(String prProject) {
		this.prProject = prProject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
}
