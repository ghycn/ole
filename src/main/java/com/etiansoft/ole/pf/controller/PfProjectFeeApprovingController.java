package com.etiansoft.ole.pf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.pf.query.PfProjectFeeQuery;
import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.quotation.service.QuotationService;

@Controller
public class PfProjectFeeApprovingController {
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@RequestMapping(value = "/approving/projectFee/list")
	public String list() {
		return "/projectFee/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/projectFee/data")
	public DataTablePage data(PfProjectFeeQuery Query, DataTablePage page) {
		return pfProjectFeeService.getData(Query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/projectFee/approve")
	public String  approve(Integer projectFeeId,String quotationListId) {
		System.out.println(quotationListId);
		if(!"".equals(quotationListId)&& quotationListId!=null){//如果标价项id不为空
			Double  quotationListAmount= pfProjectFeeService.querySelectQuotationListAmount(quotationListId);//查询选择报价项的金额
			Double ProjectFeeAmoount = pfProjectFeeService.queryProjectFeeAmoount(quotationListId);//查询已请款总额
			
			if(quotationListAmount-ProjectFeeAmoount>0){
				pfProjectFeeService.changeStatus(projectFeeId, ExamineStatus.APPROVED.getStatus());
				return "1";
			}else{
				return "2";
			}
		}else{
			PFProjectFee pfProjectFee = pfProjectFeeService.findById(projectFeeId);
			Integer quotationId = pfProjectFee.getQuotation().getQuotationId();//报价单id
			Double taxTotal = quotationService.findById(quotationId).getTaxTotal();
			Integer taxRate = pfProjectFee.getQuotation().getPrProject().getTaxRate();
			double a = taxTotal*((new Double(taxRate)*0.01)+1);//报价单总额
			double b = pfProjectFeeService.queryAlreadyPleaseAmount(quotationId);//查询该报价单已请款金额
			if(a-b>0){
				pfProjectFeeService.changeStatus(projectFeeId, ExamineStatus.APPROVED.getStatus());
				return "1";
			}else{
				return "2";
			}
		}

	}
	@RequestMapping(value = "/approving/projectFee/pass")
	public String  approve(Integer projectFeeId) {
			pfProjectFeeService.changeStatus(projectFeeId, ExamineStatus.APPROVED.getStatus());
			return "/projectFee/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/projectFee/reject")
	public void reject(Integer projectFeeId) {
		pfProjectFeeService.changeStatus(projectFeeId, ExamineStatus.REJECTED.getStatus());
	}
}
