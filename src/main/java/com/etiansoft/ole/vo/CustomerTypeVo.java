package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.CustomerType;
import com.etiansoft.tools.vo.Vo;

public class CustomerTypeVo implements Vo<CustomerType> {

	/** 客户分类ID **/
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

	@Override
	public void from(CustomerType po) {
		setTypeId(po.getTypeId());
		setName(po.getName());
		setRemove(po.getRemove());
	}

	@Override
	public void to(CustomerType po) {

	}
}
