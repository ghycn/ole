package com.etiansoft.ole.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.query.QuotationQuery;
import com.etiansoft.ole.quotation.service.QuotationService;

@Controller
public class QuotationApprovingController {

	@Autowired
	private QuotationService quotationService;
	@Autowired
	private PrProjectService projectService;

	@RequestMapping(value = "/approving/quotation/list")
	public String list() {
		return "/quotation/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/quotation/data")
	public DataTablePage data(QuotationQuery query, DataTablePage page) {
		return quotationService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/quotation/approve")
	public void approve(Integer quotationId) {
		quotationService.changeStatus(quotationId, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/quotation/reject")
	public void reject(Integer quotationId) {
		Quotation quotation = quotationService.findById(quotationId);
		PrProject prProject = quotation.getPrProject();
		quotationService.changeMoney(prProject.getProjectCode(),prProject.getInvoiceAmount(),quotation.getTaxTotal());
		quotationService.changeStatus(quotationId,ExamineStatus.REJECTED.getStatus());
	}
}
