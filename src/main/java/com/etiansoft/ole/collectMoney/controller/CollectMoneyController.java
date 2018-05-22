package com.etiansoft.ole.collectMoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.collectMoney.query.CollectMoneyQuery;
import com.etiansoft.ole.collectMoney.service.CollectMoneyService;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.CollectMoney;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.service.ActivityService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.vo.CollectMoneyVo;
import com.etiansoft.ole.vo.QuotationVo;

@Controller
public class CollectMoneyController {

	@Autowired
	private CollectMoneyService collectMoneyService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/collectMoney/list")
	public String list() {
		return "/collectMoney/list";
	}

	@ResponseBody
	@RequestMapping(value = "/collectMoney/data")
	public DataTablePage data(CollectMoneyQuery query, DataTablePage page) {
		return collectMoneyService.getData(query, page);
	}

	@RequestMapping(value = "/collectMoney/add")
	public String add(ModelMap model) {
		List<QuotationVo> quotations = quotationService.findbyStatus(ExamineStatus.APPROVED.getStatus());
		model.put("quotations", quotations);
		return "/collectMoney/add";
	}

	@RequestMapping(value = "/collectMoney/save")
	public String save(CollectMoney collectMoney, String date, Integer quotationId) {
		collectMoneyService.save(collectMoney, date, quotationId);
		return "redirect:/collectMoney/list";
	}

	@RequestMapping(value = "/collectMoney/edit")
	public String edit(Integer collectId, ModelMap model) {
		if (collectId != null) {
			List<QuotationVo> quotations = quotationService.findbyStatus(ExamineStatus.APPROVED.getStatus());
			CollectMoneyVo collectMoney = collectMoneyService.findVoById(collectId, CollectMoneyVo.class);
			model.put("edit", true);
			model.put("quotations", quotations);
			model.put("collectMoney", collectMoney);
		}
		return "/collectMoney/edit";
	}

	@RequestMapping(value = "/collectMoney/update")
	public String update(CollectMoney collectMoney, String date, Integer quotationId) {
		collectMoneyService.update(collectMoney, date, quotationId);
		return "redirect:/collectMoney/list";
	}

}
