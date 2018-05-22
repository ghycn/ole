package com.etiansoft.ole.cost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.cost.service.CostAnalysisService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.PrProject;

@Controller
public class CostAnalysisApprovingController {

	@Autowired
	private CostAnalysisService costAnalysisService;

	@RequestMapping(value = "/approving/costAnalysis/list")
	public String list() {
		return "/cost/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/costAnalysis/data")
	public DataTablePage data(CostAnalysis costAnalysis, DataTablePage page) {
		return costAnalysisService.getData(costAnalysis, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/costAnalysis/approve")
	public void approve(Integer costAnalysisId) {
		costAnalysisService.changeStatus(costAnalysisId, ExamineStatus.CLOSED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/costAnalysis/reject")
	public void reject(Integer costAnalysisId) {
		costAnalysisService.changeStatus(costAnalysisId, ExamineStatus.REJECTED.getStatus());
	}
}