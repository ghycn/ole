package com.etiansoft.ole.cost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.cost.service.CostAnalysisService;

@Controller
public class CostAnalysisReportController {

	@Autowired
	private CostAnalysisService costAnalysisService;

	@RequestMapping("/costAnalysis/totalCost/report")
	public String totalCostReport() {
		return "cost/totalCostReport";
	}

	@ResponseBody
	@RequestMapping("/costAnalysis/totalCost/report/data")
	public List<Object[]> totalCostReportData(String year, String userCode) {
		return costAnalysisService.totalCostReport(year, userCode);
	}

	@RequestMapping("/costAnalysis/dailyaverage/report")
	public String dailyaverageReport() {
		return "cost/dailyaverageReport";
	}

	@ResponseBody
	@RequestMapping("/costAnalysis/dailyaverage/report/data")
	public List<Object[]> dailyaverageReportData(String year, String userCode) {
		return costAnalysisService.dailyaverageReport(year, userCode);
	}

	@RequestMapping("/costAnalysis/grossProfit/report")
	public String grossProfitReport() {
		return "cost/grossProfitReport";
	}

	@ResponseBody
	@RequestMapping("/costAnalysis/grossProfit/report/data")
	public List<Object[]> grossProfitReportData(String year, String userCode) {
		return costAnalysisService.grossProfitReport(year, userCode);
	}
}