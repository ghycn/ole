package com.etiansoft.ole.bad.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.bad.query.BadAccountQuery;
import com.etiansoft.ole.bad.service.BadAccountService;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.BadAccount;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.vo.BadAccountVo;
import com.etiansoft.ole.vo.QuotationVo;

@Controller
public class BadAccountController {

	@Autowired
	private BadAccountService badAccountService;
	@Autowired
	private QuotationService quotationService;

	@RequestMapping(value = "/badAccount/applyList")
	public String list() {
		return "/badAccount/applyList";
	}

	@ResponseBody
	@RequestMapping("/badAccount/applyListData")
	public DataTablePage data(BadAccountQuery badAccount, DataTablePage page) {
		return badAccountService.getData(badAccount, page);
	}

	@RequestMapping("/badAccount/apply/add")
	public String applyAdd(BadAccountVo badAccount, ModelMap model) {
		List<QuotationVo> quotations = quotationService.findbyStatus(ExamineStatus.APPROVED.getStatus());
		model.put("quotations", quotations);
		model.put("badAccount", badAccount);
		return "/badAccount/apply";
	}

	@RequestMapping("/badAccount/apply/edit")
	public String applyEdit(BadAccountVo badAccount, ModelMap model) {
		if (badAccount.getBadId() != null) {
			badAccount = badAccountService.findVoById(badAccount.getBadId(), BadAccountVo.class);
		}
		List<QuotationVo> quotations = quotationService.findbyStatus(ExamineStatus.APPROVED.getStatus());
		model.put("quotations", quotations);
		model.put("badAccount", badAccount);
		return "/badAccount/apply";
	}

	@RequestMapping("/badAccount/apply/addOrUpdate")
	public String applyAddOrUpdate(String date, BadAccount badAccount, HttpSession session) {
		badAccount.setStatus(ExamineStatus.APPROVING.getStatus());
		if (badAccount.getBadId() != null) {
			badAccountService.update(badAccount);
		} else {
			badAccountService.save(date, badAccount);
		}
		return "redirect:/badAccount/applyList";
	}

	@RequestMapping("/badAccount/invalid")
	public String invalid(Integer badId) {
		badAccountService.changeStatus(badId, ExamineStatus.INVALID.getStatus());
		return "redirect:/badAccount/applyList";
	}
}
