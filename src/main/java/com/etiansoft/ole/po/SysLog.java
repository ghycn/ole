package com.etiansoft.ole.po;

import java.util.Date;

/** 日志(SysLog) **/
public class SysLog {

	/** 日志ID **/
	private Integer logId;
	/** 操作人 **/
	private String operator;
	/** 操作时间 **/
	private Date operateTime;
	/** 操作IP **/
	private String opertateIp;
	/** 操作内容 **/
	private String content;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOpertateIp() {
		return opertateIp;
	}

	public void setOpertateIp(String opertateIp) {
		this.opertateIp = opertateIp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
