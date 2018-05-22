package com.etiansoft.ole.util;

import java.io.Serializable;
import java.util.List;

public class ZTree implements Serializable {

	private static final long serialVersionUID = -9166902060235885761L;
	
	private Integer id;
	private Integer pid;
	private String name;
	private boolean open;
	private boolean doCheck=true;
	private boolean checked;
	
	private List<ZTree> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isDoCheck() {
		return doCheck;
	}
	public void setDoCheck(boolean doCheck) {
		this.doCheck = doCheck;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ZTree> getChildren() {
		return children;
	}
	public void setChildren(List<ZTree> children) {
		this.children = children;
	}
	
	

}
