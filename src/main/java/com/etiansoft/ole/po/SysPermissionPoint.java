/**
 * @项目名称: ole
 * @文件名称: SysPermissionPoint.java
 * @author tianlihu
 * @Date: 2015-5-9
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.po;


/** 权限(SysPermissionPoint) **/
public class SysPermissionPoint {

	/** 权限点ID **/
	private Integer permissionPointId;
	/** 权限点值 **/
	private String value;

	public Integer getPermissionPointId() {
		return permissionPointId;
	}

	public void setPermissionPointId(Integer permissionPointId) {
		this.permissionPointId = permissionPointId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
