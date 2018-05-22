package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.Activity;
import com.etiansoft.tools.vo.Vo;

public class ActivityVo implements Vo<Activity> {
	/** 活动编码 **/
	private String activityCode;
	/** 名称 **/
	private String name;

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void from(Activity po) {
		setActivityCode(po.getActivityCode());
		setName(po.getName());
	}

	@Override
	public void to(Activity po) {
	}
}