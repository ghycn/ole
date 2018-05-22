package com.etiansoft.ole.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.user.query.RoleQuery;
import com.etiansoft.ole.user.service.PermissionService;
import com.etiansoft.ole.user.service.RoleService;
import com.etiansoft.ole.util.TreeNode;
import com.etiansoft.ole.vo.SysRoleVo;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/role/list")
	public String list() {
		return "role/list";
	}

	@ResponseBody
	@RequestMapping(value = "/role/data", method = RequestMethod.POST)
	public DataTablePage data(RoleQuery query, DataTablePage page) {
		return roleService.getData(query, page);
	}

	@RequestMapping("/role/add")
	public String add() {
		return "role/add";
	}

	@RequestMapping("/role/save")
	public String save(SysRole sysRole) {
		roleService.save(sysRole);
		return "redirect:/role/list";
	}

	@RequestMapping("/role/edit")
	public String edit(Integer roleId, ModelMap model) {
		if (roleId != null) {
			model.put("edit", true);
			model.put("role", roleService.findVoById(roleId, SysRoleVo.class));
		}
		return "role/edit";
	}

	@RequestMapping("/role/update")
	public String update(SysRole role) {
		roleService.update(role);
		return "redirect:/role/list";
	}

	@RequestMapping("/role/permission")
	public String permission(Integer roleId, ModelMap model) {
		String permissions = permissionService.getRolePermissionIds(roleId).toString();
		model.put("roleId", roleId);
		model.put("permissions", permissions.substring(1, permissions.length() - 1).replaceAll(" ", ""));
		return "role/permission";
	}

	@ResponseBody
	@RequestMapping(value = "/role/permissionTree", method = RequestMethod.POST)
	public List<TreeNode> permissionTree(Integer roleId) {
		return permissionService.permissionTree(roleId);
	}

	@ResponseBody
	@RequestMapping(value = "/role/savePermissions", method = RequestMethod.POST)
	public String savePermissions(Integer roleId, String permissions) {
		try {
			permissionService.savePermissions(roleId, permissions);
			return "success";
		} catch (Throwable e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@RequestMapping("/role/doDelete")
	public String doDelete(Integer roleId) {
		roleService.deleteByCode(roleId);
		return "redirect:/role/list";
	}
}
