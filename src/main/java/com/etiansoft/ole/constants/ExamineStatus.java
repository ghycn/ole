package com.etiansoft.ole.constants;

public enum ExamineStatus {

	APPROVING(0), APPROVED(1), REJECTED(2), INVALID(3), CLOSED(4);

	private Integer status;

	private ExamineStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}