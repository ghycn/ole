package com.etiansoft.ole.bad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.bad.query.BadAccountQuery;
import com.etiansoft.ole.bad.service.BadAccountService;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;

@Controller
public class BadAccountApprovingController {

	@Autowired
	private BadAccountService badAccountService;

	@RequestMapping(value = "/approving/badAccount/list")
	public String list() {
		return "/badAccount/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/badAccount/data")
	public DataTablePage data(BadAccountQuery badAccount, DataTablePage page) {
		return badAccountService.getData(badAccount, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/badAccount/approve")
	public void approve(Integer badId) {
		badAccountService.changeStatus(badId, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/badAccount/reject")
	public void reject(Integer badId) {
		badAccountService.changeStatus(badId, ExamineStatus.REJECTED.getStatus());
	}
}
