package com.etiansoft.ole.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Integer menuId;
	private String menuName;
	private String url;
	private Integer parentId;
	private String icon;
	private Integer sort;

	private List<SysMenuItem> menuItems = new ArrayList<SysMenuItem>();
	
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<SysMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<SysMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
}