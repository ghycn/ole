package com.etiansoft.ole.wk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.wk.query.WkLeaveQuery;
import com.etiansoft.ole.wk.service.WkLeaveService;

@Controller
public class WkLeaveApprovingController {
	@Autowired
	private WkLeaveService wkLeaveService;

	@RequestMapping(value = "/approving/wkLeave/list")
	public String list() {
		return "/wkLeave/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkLeave/data")
	public DataTablePage data(WkLeaveQuery query, DataTablePage page) {
		return wkLeaveService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkLeave/approve")
	public void approve(Integer leaveId) {
		wkLeaveService.changeStatus(leaveId, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkLeave/reject")
	public void reject(Integer leaveId) {
		wkLeaveService.changeStatus(leaveId, ExamineStatus.REJECTED.getStatus());
	}
}
