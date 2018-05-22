package com.etiansoft.ole.sys.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.user.service.RoleService;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.ole.wk.service.WkLeaveService;
import com.etiansoft.ole.wk.service.WkOverTimeService;
import com.etiansoft.tools.common.MD5;

@Controller
public class SysPersonalDataController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@Autowired
	private WkLeaveService wkLeaveService;
	@Autowired
	private WkOverTimeService overTimeService;
	@Autowired
	private PrProjectService projectService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/personalData/basicInfo")
	public String basicInfo(String headIcon, ModelMap model, HttpSession session) throws Exception {
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		model.put("user", user);
		model.put("headIcon", headIcon);
		int month = 0;
		if (user.getEntryDate() != null) {
			month = getMonthSpace(DateTool.currentDate(), DateTool.formatDate(user.getEntryDate()));
		}
		model.put("month", month);
		return "personalData/basicInfo";
	}

	public static int getMonthSpace(String date1, String date2) throws ParseException {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));
//		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		result = c1.get(Calendar.YEAR)-c2.get(Calendar.YEAR);
		int result1 = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		
		return result*12+result1;//result == 0 ? 1 : Math.abs(result);
	}

	@RequestMapping("/personalData/basicInfoEdit")
	public String basicInfoEdit(SysUser user, String birth ,HttpSession session) throws Exception {
		sysUserService.update(user, session, birth);
		return "redirect:/personalData/basicInfo";
	}

	@RequestMapping("/personalData/uploadHeadIcon")
	public String uploadHeadIcon(@RequestParam MultipartFile file, int x, int y, int w, int h, ModelMap model) throws Exception {
		String path = sysUserService.upload(file, x, y, w, h);
		model.put("headIcon", path);
		return "redirect:/personalData/basicInfo";
	}

	@RequestMapping("/personalData/leaveSubtotal")
	public String leaveSubtotal(ModelMap model, HttpSession session) {
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		Double leaveDays = wkLeaveService.countDays(user.getUserCode());
		int overtimeDays = overTimeService.countDays(user.getUserCode());
		Integer vacationDays = user.getVacationDays()==null?0:user.getVacationDays();
		model.put("leaveDays", leaveDays);
		model.put("overtimeDays", overtimeDays);
		model.put("vacationDays", vacationDays);
		return "personalData/leaveSubtotal";
	}

	@RequestMapping("/personalData/caseList")
	public String caseList(ModelMap model, HttpSession session) {
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		model.put("loginName", user.getName());
		return "personalData/caseList";
	}

	@RequestMapping("/personalData/changePassword")
	public String changePassword(String userCode, HttpSession session, ModelMap model) {
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (StringUtils.isEmpty(userCode)) {
			userCode = user.getUserCode();
		} else {
			model.put("flag", true);
		}
		model.put("userCode", userCode);
		return "personalData/changePassword";
	}

	@ResponseBody
	@RequestMapping("/personalData/editPassword")
	public String editPassword(String oldPassword, String password, String userCode, String type, HttpSession session, ModelMap model) {
		SysUser user = sysUserService.findById(userCode);
		SysUser sysUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if ("admin".equals(type)) {
			return validatePassword(password, userCode, session, user, sysUser);
		} else {
			if (!StringUtils.equals(MD5.md5(oldPassword), user.getPassword())) {
				return "oldPassError";
			} else {
				return validatePassword(password, userCode, session, user, sysUser);
			}
		}
	}

	private String validatePassword(String password, String userCode, HttpSession session, SysUser user, SysUser sysUser) {
		String newPassword = MD5.md5(password);
		sysUserService.updatePassword(user, newPassword);
		user.setPassword(newPassword);
		if (userCode.equals(sysUser.getUserCode())) {
			return "success";
		}
		return "otherSuccess";
	}
}