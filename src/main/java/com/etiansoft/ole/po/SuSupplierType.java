package com.etiansoft.ole.po;

import com.etiansoft.ole.annotation.Sheet;

/** 厂商分类表(SuSupplierType) **/
@Sheet(name = "厂商分类")
public class SuSupplierType {

	/** 厂商分类ID **/
	private Integer typeId;
	/** 分类名称 **/
	private String name;
	/** 确定删除的状态（0：已删除 1：未删除） **/
	private Boolean remove;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

}