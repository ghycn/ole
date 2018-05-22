package com.etiansoft.ole.po;

import java.util.Set;

/** 权限(SysPermission) **/
public class SysPermission {

	/** 权限ID **/
	private String permissionId;
	/** 父权限ID **/
	private String parentId;
	/** 名称 **/
	private String name;
	/** 备注 **/
	private String notes;

	private Set<SysPermissionPoint> points;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<SysPermissionPoint> getPoints() {
		return points;
	}

	public void setPoints(Set<SysPermissionPoint> points) {
		this.points = points;
	}
}
