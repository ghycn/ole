/**
 * @项目名称: ole
 * @文件名称: RoleVo.java
 * @author tianlihu
 * @Date: 2015-5-9
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.SysRole;
import com.etiansoft.tools.vo.Vo;

public class SysRoleVo implements Vo<SysRole> {

	/** 角色ID **/
	private Integer roleId;
	/** 名称 **/
	private String name;
	/** 备注 **/
	private String notes;

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

	@Override
	public void from(SysRole po) {
		setRoleId(po.getRoleId());
		setName(po.getName());
		setNotes(po.getNotes());
	}

	@Override
	public void to(SysRole po) {

	}

}
