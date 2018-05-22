package com.etiansoft.ole.wk.controller;

import java.util.List;

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
import com.etiansoft.ole.po.WkOvertime;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.vo.WkOvertimeVo;
import com.etiansoft.ole.wk.query.WkOverTimeQuery;
import com.etiansoft.ole.wk.service.WkOverTimeService;

@Controller
public class WkOverTimeController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WkOverTimeService wkOverTimeService;

	@RequestMapping("/wkOvertime/applyList")
	public String list() {
		return "wkOvertime/applyList";
	}

	@ResponseBody
	@RequestMapping("/wkOvertime/applyListData")
	public DataTablePage data(WkOverTimeQuery query, DataTablePage page) {
		return wkOverTimeService.getData(query, page);
	}

	@RequestMapping("/wkOvertime/apply/add")
	public String applyAdd(WkOvertime wKOvertime, ModelMap model) {
		List<SysUser> users = sysUserService.queryBossName();//查询具有审批权限的人
		model.put("users", users);
		model.put("wkOvertime", wKOvertime);
		return "wkOvertime/apply";
	}

	@RequestMapping("/wkOvertime/apply/edit")
	public String applyEdit(WkOvertimeVo wKOvertime, ModelMap model) {
		List<SysUser> users = null;
		if (wKOvertime.getOvertimeId() != null) {
		    users = sysUserService.queryBossName();//查询具有审批权限的人
			wKOvertime = wkOverTimeService.findVoById(wKOvertime.getOvertimeId(), WkOvertimeVo.class);
		}
		model.put("users", users);
		model.put("wkOvertime", wKOvertime);
		return "wkOvertime/apply";
	}

	@RequestMapping("/wkOvertime/apply/addOrUpdate")
	public String applyAddOrUpdate(String start, String end, WkOvertime wKOvertime, HttpSession session) {
		SysUser applicant = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		wKOvertime.setApplicant(applicant);
		if (wKOvertime.getOvertimeId() != null) {
			wkOverTimeService.update(start, end, wKOvertime);
		} else {
			wkOverTimeService.save(start, end, wKOvertime);
		}
		return "redirect:/wkOvertime/applyList";
	}

	@RequestMapping("/wkOvertime/invalid")
	public String invalid(Integer overtimeId) {
		wkOverTimeService.changeStatus(overtimeId, ExamineStatus.INVALID.getStatus());
		return "redirect:/wkOvertime/applyList";
	}
	@RequestMapping("/wkOvertime/apply/info")
	public String info (Integer overtimeId,ModelMap model){
		WkOvertime wKOvertime = wkOverTimeService.findById(overtimeId);
		model.put("wKOvertime", wKOvertime);
		return "wkOvertime/info";
	}
}
