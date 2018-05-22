package com.etiansoft.ole.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.sys.service.SerialNumberUserService;
import com.etiansoft.ole.user.query.UserQuery;
import com.etiansoft.ole.user.service.RoleService;
import com.etiansoft.ole.user.service.UserService;
import com.etiansoft.ole.util.PdfReportUtil;
import com.etiansoft.ole.vo.SysRoleVo;
import com.etiansoft.ole.vo.SysUserVo;
import com.etiansoft.ole.wk.service.WkLeaveService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SerialNumberUserService serialNumberUserService;
	@Autowired
	private PrProjectService projectService;
	@Autowired
	private WkLeaveService wkLeaveService;

	@RequestMapping("/user/list")
	public String list() {
		return "user/list";
	}

	@ResponseBody
	@RequestMapping("/user/data")
	public DataTablePage data(UserQuery query, DataTablePage page) {
		DataTablePage dataTablePage = userService.getData(query, page);
		
		List<WkLeave> wklist = wkLeaveService.queryVacateDay();//查询每个人请假天数
		
		@SuppressWarnings("unchecked")
		List<SysUserVo> aaData = (List<SysUserVo>) dataTablePage.getAaData();
		List<SysUserVo> list = new ArrayList<>();
		Map<String,SysUserVo> map = new HashMap<>();
		for(SysUserVo sysUserVo:aaData){
			map.put(sysUserVo.getUserCode(), sysUserVo);
		}
		for(WkLeave wkLeave:wklist){
			if(map.containsKey(wkLeave.getNotes())){
				map.get(wkLeave.getNotes()).setLeaveDays(wkLeave.getDuration());
			}
		}
		for(Entry<String, SysUserVo> entry:map.entrySet()){
			list.add(entry.getValue());
		}
		Collections.sort(list);
		dataTablePage.setAaData(list);
		return dataTablePage;
	}

	@RequestMapping("/user/exportPdf")
	public void exportPdf(HttpServletResponse response) throws Exception {
		List<SysUserVo> list = userService.findBydel();
		PdfReportUtil.export(list, SysUserVo.class, response);
	}

	@RequestMapping("/user/add")
	public String add(ModelMap model) {
		List<SysRoleVo> roleList = roleService.findByVoRemove();
		model.put("roles", roleList);
		return "user/add";
	}

	@RequestMapping("/user/save")
	public String save(SysUser sysUser, String birth,String hiredate) {
		int id = serialNumberUserService.findSerialNumber();
		sysUser.setUserCode(String.valueOf(id));
		userService.save(sysUser, birth,hiredate);
		serialNumberUserService.updateId(id);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/edit")
	public String edit(String userCode, ModelMap model) {
		if (userCode != null) {
			model.put("edit", true);
			SysUserVo user = (SysUserVo) userService.findVoById(userCode, SysUserVo.class);
			model.put("user", user);
			model.put("roles", roleService.findByVoRemove());
		}
		return "user/edit";
	}

	@RequestMapping("/user/update")
	public String updateUser(SysUser user, String birth,String hiredate) {
		userService.update(user, birth,hiredate);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/changeStatus")
	public String changeStatus(String userCode, boolean status) {
		userService.changeStatus(userCode, status);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/setProject")
	public String setProject(String userCode, ModelMap model) {
		List<PrProject> projects = projectService.findAll();
		SysUser user = userService.findById(userCode);
		Set<PrProject> userProjects = user.getProjects();
		model.put("userProjects", userProjects);
		model.put("userCode", userCode);
		model.put("projects", projects);
		return "user/setProject";
	}

	@RequestMapping("/user/saveSetProjects")
	public String saveSetProject(String projectCodes, String userCode) {
		String[] projectCode = projectCodes.split(",");
		if (ArrayUtils.isNotEmpty(projectCode)) {
			Set<PrProject> projectSet = new HashSet<PrProject>();
			SysUser user = userService.findById(userCode);
			for (int i = 0; i < projectCode.length; i++) {
				PrProject project = projectService.findById(projectCode[i]);
				projectSet.add(project);
			}
			user.setProjects(projectSet);
			userService.update(user);
		}
		return "redirect:/user/list";
	}

	@RequestMapping("/user/setRole")
	public String setRole(String userCode, Integer[] roleIds, ModelMap model) {
		List<SysRole> roles = roleService.findAll();
		SysUser user = userService.findById(userCode);
		if (roleIds != null && roleIds.length > 0) {
			Set<SysRole> roleSet = new HashSet<SysRole>();
			for (int i = 0; i < roleIds.length; i++) {
				SysRole role = roleService.findById(roleIds[i]);
				roleSet.add(role);
			}
			user.setRoles(roleSet);
			userService.update(user);
			return "redirect:/user/list";
		}
		Set<SysRole> sysRoles = user.getRoles();
		List<Integer> userRoleIds = new ArrayList<Integer>();
		for (SysRole role : sysRoles) {
			userRoleIds.add(role.getRoleId());
		}
		model.put("userRoleIds", userRoleIds);
		model.put("userCode", userCode);
		model.put("roles", roles);
		return "user/setRole";
	}

	@RequestMapping("/user/doDelete")
	public String doDelete(String userCode, Integer state) {
		userService.update(userCode, state);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/leaveList")
	public String leaveList() {
		userService.findAll();
		return "user/leaveList";
	}

	@RequestMapping("/user/doDel")
	public String doDel(String userCode, Integer state) {
		userService.update(userCode, state);
		return "redirect:/user/leaveList";
	}

	@RequestMapping("/user/editDay")
	public String editDay(String userCode, ModelMap model) {
		if (userCode != null) {
			model.put("edit", true);
			SysUser user = userService.findById(userCode);
			model.put("user", user);
			model.put("roles", roleService.findByVoRemove());
		}
		return "user/editDay";

	}

	@RequestMapping("/user/updateDay")
	public String updateDay(SysUser user) {
		userService.updateDays(user);
		return "redirect:/user/leaveList";
	}
}
