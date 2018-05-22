package com.etiansoft.ole.pr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.pr.service.PrProjectService;

@Controller
public class PrProjectReportController {

	@Autowired
	private PrProjectService projectService;

	@RequestMapping("/project/report")
	public String report(String userCode, ModelMap model) {
		return "project/report";
	}

	@ResponseBody
	@RequestMapping("/project/report/data")
	public List<Object[]> reportData(HttpSession session, String year, String userCode) {
		return projectService.report(year, userCode);
	}
}