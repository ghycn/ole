package com.etiansoft.ole.cost.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.cost.service.CostAnalysisReportPdfService;
import com.etiansoft.ole.cost.service.CostAnalysisService;
import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.CostAnalysisItem;
import com.etiansoft.ole.po.OtherPFProjectFee;
import com.etiansoft.ole.po.OtherPFProjectFeeType;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.service.QuotationListService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.supplier.service.SupplierService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.PFProjectFeeVo;

@Controller
public class CostAnalysisController {

	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private QuotationListService quotationListService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@Autowired
	private CostAnalysisService costAnalysisService;
	@Autowired
	private CostAnalysisReportPdfService reportPdfService;

	@RequestMapping(value = "/cost/coseAnalysis/list")
	public String coseAnalysisList() {
		return "/cost/coseAnalysisList";
	}

	@RequestMapping(value = "/cost/coseAnalysis")
	public String coseAnalysis(String projectCode, Integer quotationId,Integer applicantCode,Integer approvalOfPersonnel ,ModelMap model) {
		PrProject project = prProjectService.findById(projectCode);
		Quotation quotation = quotationService.findById(quotationId);
		//quotationService.findbyProject(projectCode);

		// 获取杂志费和公关费
		BigDecimal miscel = new BigDecimal(0);
		BigDecimal relation = new BigDecimal(0);
		Set<OtherPFProjectFee> otherPFProjectFees = quotation.getOtherPFProjectFees();
		Iterator<OtherPFProjectFee> iterator = otherPFProjectFees.iterator();
		while (iterator.hasNext()) {
			OtherPFProjectFee otherPfProjectFee = iterator.next();
			PFProjectFee pfProjectFee = otherPfProjectFee.getPfProjectFee();
			if (pfProjectFee != null) {
				if (otherPfProjectFee.getOtherTypeId() == 100000005) {
					BigDecimal amount = new BigDecimal(pfProjectFee.getAmount());
					relation = relation.add(amount);
				} else {
					BigDecimal amount = new BigDecimal(pfProjectFee.getAmount());
					miscel = miscel.add(amount);
				}
			}
		}
		Double amoutTotal=quotationService.getAmountTotal(quotationId);
		Double otherAmoutTotal=quotationService.otherTotalAmount(quotationId);
//		model.put("projectFeeTypeList",pfProjectFeeService.projectFeeTypeList(quotationId));
//		model.put("TypeList",pfProjectFeeService.otherProjectFeeTypeList());
		model.put("TypeIdList",fildTypeIds(pfProjectFeeService.projectFeeTypeList(quotationId),pfProjectFeeService.otherProjectFeeTypeList()));
		CostAnalysis analysis = costAnalysisService.findByQuotationId(quotationId);
		List<CostAnalysisItem> items = null;
		if (analysis != null) {
			items = costAnalysisService.findItemByAnalysisId(analysis.getCostAnalysisId());
		}
		model.put("miscel", miscel.doubleValue());
		model.put("relation", relation.doubleValue());
		model.put("project", project);
		model.put("items", items);
		model.put("amoutTotal",amoutTotal);
		model.put("otherAmoutTotal",otherAmoutTotal);
		model.put("totalAmount",amoutTotal+otherAmoutTotal);
		model.put("analysis", analysis);
		model.put("quotation", quotation);
		model.put("applicantCode", applicantCode);
		return "/cost/coseAnalysis";
	}

	@ResponseBody
	@RequestMapping(value = "/cost/coseAnalysis/getProjectFee")
	public List<PFProjectFeeVo> getProjectFee(Integer quotationListId) {
		QuotationList quotationList = quotationListService.findById(quotationListId);
		Set<PFProjectFee> projectFees = quotationList.getProjectFees();
		List<PFProjectFee> list = new ArrayList<PFProjectFee>();
		list.addAll(projectFees);
		return VoTool.convert(list, PFProjectFeeVo.class);
	}

	@RequestMapping(value = "/cost/coseAnalysis/save")
	public String coseAnalysisSave(CostAnalysis costAnalysis, String projectCode, Integer quotationId) {
		costAnalysisService.saveCostAnalysis(costAnalysis, projectCode, quotationId);
		return "redirect:/cost/coseAnalysis/list";
	}

	@RequestMapping(value = "/cost/coseAnalysis/reportpdf")
	public void reportpdf(HttpServletResponse response, String projectCode, Integer quotationId) {
		try {
			OutputStream os = null;
			InputStream in = null;
			try {
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + new String("成本分析单.pdf".getBytes("gb2312"), "iso8859-1"));
				os = response.getOutputStream();
				reportPdfService.reportPdf(projectCode, quotationId, os);
				os.flush();
			} finally {
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/cost/closed")
	public int closed(String projectCode) {
		return costAnalysisService.closed(projectCode);
	}
	private List<OtherPFProjectFeeType> fildTypeIds(List<PFProjectFee> fList,List<OtherPFProjectFeeType> typeId){
		List<OtherPFProjectFeeType> list=new ArrayList<OtherPFProjectFeeType>();
		
		 for(OtherPFProjectFeeType obj:typeId){
			 obj.setAmount(0.00);
			 double a = 0;
			 if(fList!=null&&fList.size()>0){
			 	for(PFProjectFee pf:fList){
			 		if(pf.getTypeId()==obj.getId()){
			 			a+=pf.getAmount();
			 			obj.setAmount(a);
			 		}
			 	}
			 }
			 list.add(obj);
		 }
		return list;
	}
}