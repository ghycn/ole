package com.etiansoft.ole.wk.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.wk.service.WkOverTimeService;

@Controller
public class WkOverTimeReportController {

	@Autowired
	private WkOverTimeService wkOverTimeService;
	@Autowired
	private SysUserService userService;

	@RequestMapping("/wkOvertime/report")
	public String report(String userCode, ModelMap model) {
		List<SysUser> users = userService.findAll();
		model.put("users", users);
		model.put("userCode", userCode);
		return "wkOvertime/report";
	}

	@ResponseBody
	@RequestMapping("/wkOvertime/report/data")
	public List<Object[]> reportData(HttpSession session, String year, String userCode) {
		return wkOverTimeService.report(year, userCode);
	}
}