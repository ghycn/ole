package com.etiansoft.ole.wk.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.ole.vo.WkLeaveVo;
import com.etiansoft.ole.wk.query.WkLeaveQuery;
import com.etiansoft.ole.wk.service.WkLeaveService;

@Controller
public class WkLeaveController {

	@Autowired
	private WkLeaveService wkLeaveService;

	@RequestMapping("/wkLeave/applyList")
	public String applyList() {
		return "wkLeave/applyList";
	}

	@ResponseBody
	@RequestMapping("/wkLeave/applyListData")
	public DataTablePage applyListData(WkLeaveQuery query, DataTablePage page) {
		return wkLeaveService.getData(query, page);
	}

	@RequestMapping("/wkLeave/apply/add")
	public String applyAdd(WkLeave wkLeave, ModelMap model, HttpSession session) {
		model.put("wkLeave", wkLeave);
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		Integer vacationDays = user.getVacationDays();//年假时长
		Double leaveDays = wkLeaveService.countDays(user.getUserCode());
		model.put("surplusLeaveDays", vacationDays-leaveDays);
		return "wkLeave/apply";
	}

	
	
	@RequestMapping("/wkLeave/apply/edit")
	public String applyEdit(WkLeaveVo wkLeave, ModelMap model) {
		if (wkLeave.getLeaveId() != null) {
			wkLeave = wkLeaveService.findVoById(wkLeave.getLeaveId(), WkLeaveVo.class);
		}
		model.put("wkLeave", wkLeave);
		return "wkLeave/apply";
	}

	@RequestMapping("/wkLeave/apply/addOrUpdate")
	public String applyAddOrUpdate(String start, String end, WkLeave wkLeave, HttpSession session) {
		SysUser applicant = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		wkLeave.setApplicant(applicant);
		if (wkLeave.getLeaveId() != null) {
			wkLeaveService.update(start, end, wkLeave);
		} else {
			wkLeaveService.save(wkLeave, start, end);
		}
		return "redirect:/wkLeave/applyList";
	}

	@RequestMapping("/wkLeave/invalid")
	public String invalid(Integer leaveId) {
		wkLeaveService.changeStatus(leaveId, ExamineStatus.INVALID.getStatus());
		return "redirect:/wkLeave/applyList";
	}

	@RequestMapping("/wkLeave/list")
	public String list() {
		return "wkLeave/list";
	}

	@ResponseBody
	@RequestMapping("/wkLeave/data")
	public DataTablePage data(WkLeaveQuery query, DataTablePage page) {
		return wkLeaveService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping("/approving/wkleave/approve")
	public void approve(Integer leaveId) {
		wkLeaveService.changeStatus(leaveId, ExamineStatus.APPROVED.getStatus());
	}
}