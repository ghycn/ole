package com.etiansoft.ole.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.po.SysMenu;
import com.etiansoft.ole.po.SysMenuItem;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.sys.service.MenuService;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.user.service.PermissionService;
import com.etiansoft.tools.common.MD5;

@SuppressWarnings("unchecked")
@Controller
public class IndexController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private SysUserService userService;
	@Autowired
	private PermissionService permissionService;
	@Value("${uploadPath}")
	public String uploadPath;

	@RequestMapping(value = "/index")
	public String index(HttpSession session) {
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (loginUser.getAdmin()) {
			return "redirect:/browse?menuId=14";
		}
		return "index";
	}

	@RequestMapping(value = "/login")
	public String login(SysUser user, HttpSession session, ModelMap model) throws Exception {
		if (StringUtils.isEmpty(user.getUserCode()) || StringUtils.isEmpty(user.getPassword())) {
			return "login";
		}
		SysUser loginUser = userService.findById(user.getUserCode());
		if (loginUser == null) {
			model.put("error", "global.userNoExist");
			return "login";
		} else if (loginUser.getState() == 0) {
			model.put("error", "global.loginError");
			return "login";
		} else if (!loginUser.getStatus()) {
			model.put("error", "global.loginLocked");
			return "login";
		} else if (!StringUtils.equals(MD5.md5(user.getPassword()), loginUser.getPassword())) {
			model.put("error", "global.passwordError");
			return "login";
		}

		// 强制加载当前用户的角色
		boolean flag = false;
		Set<SysRole> roles = loginUser.getRoles();
		for (SysRole role : roles) {
			Integer roleId = role.getRoleId();
			session.setAttribute("roleId", roleId);
			if (roleId == 1) {
				flag = true;
				break;
			}
		}

		List<SysMenu> menus = menuService.findMenus(loginUser);
		List<String> permissions = permissionService.findUserPermissions(loginUser);
		List<String> jurisdiction = permissionService.findRoleJurisdiction(loginUser);//角色权限菜单
		session.setAttribute("menus", menus);
		session.setAttribute(Constants.IS_BOSS, flag);
		session.setAttribute(Constants.LOGIN_USER, loginUser);
		session.setAttribute(Constants.PERMISSIONS, permissions);
		if(loginUser.getAdmin()){
			session.setAttribute(Constants.JURISDICTION, permissions);//如果是admin用户 则开放所有权限
		}else{
			session.setAttribute(Constants.JURISDICTION, jurisdiction);//如果是其他用户  则只有对应权限
		}
		session.setAttribute("typeMap", getTypeMap());
		return "redirect:/index";
	}

	private Map<String, String> getTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("100000000", "打车费");
		map.put("100000001", "住宿费");
		map.put("100000002", "餐饮");
		map.put("100000003", "机票");
		map.put("100000004", "火车费");
		map.put("100000005", "公关费");
		return map;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "/getHeadIcon")
	public void getHeadIcon(HttpServletResponse response, String path) throws Exception {
		File file = new File(uploadPath + path);
		if (file.exists()) {
			OutputStream os = null;
			InputStream in = null;
			try {
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + new String("headIcon.png".getBytes("gb2312"), "iso8859-1"));
				in = new FileInputStream(uploadPath + path);
				os = response.getOutputStream();
				IOUtils.copy(in, os);
				os.flush();
			} finally {
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(in);
			}
		}
	}

	@RequestMapping(value = "/browse")
	public String browse(Integer menuId, HttpSession session) {
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if(!loginUser.getAdmin()){
			List<String> jurisdiction = permissionService.findRoleJurisdictions(loginUser,menuId);//角色权限菜单
			 Set<SysRole> roleSet = loginUser.getRoles();
			 Integer roleId=null;
			 for(SysRole role:roleSet){
				 roleId=role.getRoleId();
			 }
//			 if(menuId==2&&roleId==6){
//				 jurisdiction.remove("wkLeave.ApplicationForLeave");
//			 }
			session.setAttribute(Constants.JURISDICTION, jurisdiction);
		}
		SysMenu menu = menuService.findById(menuId);
		List<SysMenuItem> menuItems = menu.getMenuItems();
		List<String> permissions = (List<String>) session.getAttribute(Constants.PERMISSIONS);
		for (SysMenuItem menuItem : menuItems) {
			if (menuItem != null) {
				if (permissions.contains(menuItem.getUrl())) {
					return "redirect:" + menuItem.getUrl();
				}
			}
		}
		return "redirect:/403.html";
	}
}