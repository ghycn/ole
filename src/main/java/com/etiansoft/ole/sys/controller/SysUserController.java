package com.etiansoft.ole.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.sys.query.SysUserQuery;
import com.etiansoft.ole.sys.service.SysUserService;

@Controller
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/contact/list")
	public String list() {
		return "contact/list";
	}

	@ResponseBody
	@RequestMapping("/contact/data")
	public DataTablePage data(SysUserQuery query, DataTablePage page) {
		return sysUserService.getData(query, page);
	}
}
