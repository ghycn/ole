package com.etiansoft.ole.wk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.wk.query.WkOverTimeQuery;
import com.etiansoft.ole.wk.service.WkOverTimeService;

@Controller
public class WkOverTimeApprovingController {

	@Autowired
	private WkOverTimeService wkOverTimeService;

	@RequestMapping(value = "/approving/wkOvertime/list")
	public String list() {
		return "/wkOvertime/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkOvertime/data")
	public DataTablePage data(WkOverTimeQuery query, DataTablePage page) {
		return wkOverTimeService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkOvertime/approve")
	public void approve(Integer overtimeId) {
		wkOverTimeService.changeStatus(overtimeId, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/wkOvertime/reject")
	public void reject(Integer overtimeId) {
		wkOverTimeService.changeStatus(overtimeId, ExamineStatus.REJECTED.getStatus());
	}
}
