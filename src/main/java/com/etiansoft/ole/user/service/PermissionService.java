/**
 * @项目名称: ole
 * @文件名称: PermissionService.java
 * @author tianlihu
 * @Date: 2015-5-9
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SysPermissionPointDao;
import com.etiansoft.ole.dao.SysRoleDao;
import com.etiansoft.ole.po.SysPermission;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.TreeNode;

@Service
public class PermissionService extends OleHibernateService<SysPermission> {

	private static List<String> ADMIN_PERMISSION = new ArrayList<String>();
	static {
		ADMIN_PERMISSION.add("menu.rightsManagement");
		ADMIN_PERMISSION.add("/user/list");
		ADMIN_PERMISSION.add("/user/data");
		ADMIN_PERMISSION.add("menu.userManagement");
		ADMIN_PERMISSION.add("/user/exportPdf");
		ADMIN_PERMISSION.add("/user/add");
		ADMIN_PERMISSION.add("/user/save");
		ADMIN_PERMISSION.add("/user/edit");
		ADMIN_PERMISSION.add("/user/update");
		ADMIN_PERMISSION.add("/user/changeStatus");
		ADMIN_PERMISSION.add("/user/setRole");
		ADMIN_PERMISSION.add("/user/setProject");
		ADMIN_PERMISSION.add("/user/saveSetProjects");
		ADMIN_PERMISSION.add("/role/list");
		ADMIN_PERMISSION.add("/role/permissionTree");
		ADMIN_PERMISSION.add("/role/data");
		ADMIN_PERMISSION.add("/role/permission");
		ADMIN_PERMISSION.add("/role/add");
		ADMIN_PERMISSION.add("/role/save");
		ADMIN_PERMISSION.add("/role/edit");
		ADMIN_PERMISSION.add("/role/update");
		ADMIN_PERMISSION.add("/role/savePermissions");
		ADMIN_PERMISSION.add("/role/doDelete");
		ADMIN_PERMISSION.add("menu.roleManagement");

		ADMIN_PERMISSION.add("/personalData/basicInfo");
		ADMIN_PERMISSION.add("/personalData/basicInfoEdit");
		ADMIN_PERMISSION.add("menu.staffProfile");
		ADMIN_PERMISSION.add("/personalData/changePassword");
		ADMIN_PERMISSION.add("/personalData/editPassword");
		ADMIN_PERMISSION.add("menu.changePassword");

		ADMIN_PERMISSION.add("/getHeadIcon");
		ADMIN_PERMISSION.add("/personalData/uploadHeadIcon");

		ADMIN_PERMISSION.add("/index");
		ADMIN_PERMISSION.add("global.index");

		ADMIN_PERMISSION.add("/browse");
		ADMIN_PERMISSION.add("menu.leaveDays");
		ADMIN_PERMISSION.add("/user/leaveList");
		ADMIN_PERMISSION.add("/user/doDel");
		ADMIN_PERMISSION.add("/user/editDay");
		ADMIN_PERMISSION.add("/user/updateDay");
		
		ADMIN_PERMISSION.add("menu.equipmentManagement");
		ADMIN_PERMISSION.add("/approving/device/list");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/data");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/add");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/saveOrUpdate");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/edit");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/delete");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/retuBack");
		ADMIN_PERMISSION.add("/approving/deviceBorrow/info");
	}

	@Autowired
	private SysRoleDao roleDao;
	@Autowired
	private SysPermissionPointDao sysPermissionPointDao;

	@Transactional(readOnly = true)
	public List<String> findUserPermissions(SysUser user) {
		if (user.getAdmin()) {
			return ADMIN_PERMISSION;
		}

		List<String> permissions = sysPermissionPointDao.findUserPermissionPoints(user.getUserCode());
//		List<String> list = new ArrayList<String>();
//		for(int i=0;i<permissions.size();i++){
//			String str=permissions.get(i);
//			if(!str.startsWith("m")&&!str.endsWith("list")){
//				list.add(str);
//			}
//		}
//		permissions.removeAll(list);
//		permissions.add("global.index");
//		permissions.add("/index");
		permissions.add("/browse");
//		
//		System.out.println(permissions.size());
		return permissions;
	}

	@Transactional(readOnly = true)
	public List<TreeNode> permissionTree(Integer roleId) {
		List<String> permissionIds = getRolePermissionIds(roleId);

		List<SysPermission> permissions = findAll();
		Map<String, List<TreeNode>> map = makeMap(permissions, permissionIds);
		List<TreeNode> roots = map.get("0");
		if (roots == null) {
			return Collections.emptyList();
		}
		for (TreeNode root : roots) {
			buildTree(root, map);
		}
		return roots;
	}

	public void savePermissions(Integer roleId, String permissions) {
		SysRole role = roleDao.findById(roleId);
		role.getPermissions().clear();

		if (!StringUtils.isEmpty(permissions)) {
			String[] permissionIds = permissions.split(",");
			for (String permissionId : permissionIds) {
				SysPermission permission = findById(StringUtils.trim(permissionId));
				role.getPermissions().add(permission);
			}
		}
		save(role);
	}

	public List<String> getRolePermissionIds(Integer roleId) {
		return sysPermissionPointDao.findRolePermissions(roleId);
	}

	private void buildTree(TreeNode parent, Map<String, List<TreeNode>> map) {
		String permissionCode = parent.getKey();
		List<TreeNode> children = map.get(permissionCode);
		parent.setChildren(children);
		if (children == null) {
			return;
		}
		for (TreeNode child : children) {
			buildTree(child, map);
		}
	}

	private Map<String, List<TreeNode>> makeMap(List<SysPermission> permissions, List<String> permissionIds) {
		Map<String, List<TreeNode>> map = new HashMap<String, List<TreeNode>>();
		for (SysPermission permission : permissions) {
			String pid = permission.getParentId();
			List<TreeNode> nodes = map.get(pid);
			if (nodes == null) {
				nodes = new ArrayList<TreeNode>();
				map.put(pid, nodes);
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setTitle(permission.getName());
			treeNode.setKey(String.valueOf(permission.getPermissionId()));
			if (permissionIds.contains(permission.getPermissionId())) {
				treeNode.setSelect(true);
			}
			nodes.add(treeNode);
		}
		return map;
	}

	public List<String> findRoleJurisdiction(SysUser loginUser) {
		 Set<SysRole> roleSet = loginUser.getRoles();
		 Integer roleId=null;
		 for(SysRole role:roleSet){
			 roleId=role.getRoleId();
		 }
		 List<String> list = sysPermissionPointDao.findMenuName(roleId);
		 
		return list;
	}

	public List<String> findRoleJurisdictions(SysUser loginUser, Integer menuId) {
		 Set<SysRole> roleSet = loginUser.getRoles();
		 Integer roleId=null;
		 for(SysRole role:roleSet){
			 roleId=role.getRoleId();
		 }
		 List<String> list = sysPermissionPointDao.findMenuNames(roleId,menuId);
		 
		return list;
	}
}
