package com.etiansoft.ole.pr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.pr.query.ProjectQuery;
import com.etiansoft.ole.pr.service.PrProjectService;

@Controller
public class PrProjectApprovingController {

	@Autowired
	private PrProjectService prProjectService;

	@RequestMapping(value = "/approving/project/list")
	public String list() {
		return "/project/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/project/data")
	public DataTablePage data(ProjectQuery query, DataTablePage page,String state) {
		return prProjectService.getData(query, page,state);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/project/approve")
	public void approve(String projectCode) {
		prProjectService.changeStatus(projectCode, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/project/reject")
	public void reject(String projectCode) {
		prProjectService.changeStatus(projectCode, ExamineStatus.REJECTED.getStatus());
	}
}