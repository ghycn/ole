package com.etiansoft.ole.po;

import java.util.Set;

/** 角色(SysRole) **/
public class SysRole {

	/** 角色ID **/
	private Integer roleId;
	/** 名称 **/
	private String name;
	/** 备注 **/
	private String notes;

	private Set<SysUser> users;
	private Set<SysPermission> permissions;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public Set<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}
}
