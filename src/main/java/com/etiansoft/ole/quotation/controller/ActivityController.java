package com.etiansoft.ole.quotation.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Activity;
import com.etiansoft.ole.quotation.query.ActivityQuery;
import com.etiansoft.ole.quotation.service.ActivityService;

@Controller
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/activity/list")
	public String list() {
		return "/activity/list";
	}

	@ResponseBody
	@RequestMapping("/activity/data")
	public DataTablePage data(ActivityQuery query, DataTablePage page) {
		return activityService.getData(query, page);
	}

	@RequestMapping("/activity/add")
	public String add(Activity activity, ModelMap model) {
		model.put("activity", activity);
		return "/activity/add";
	}

	@RequestMapping("/activity/edit")
	public String edit(Activity activity, ModelMap model) {
		if (StringUtils.isNotEmpty(activity.getActivityCode())) {
			activity = activityService.findById(activity.getActivityCode());
		}
		model.put("activity", activity);
		return "/activity/edit";
	}

	@RequestMapping("/activity/save")
	public String save(Activity activity) {
		activityService.save(activity);
		return "redirect:/activity/list";
	}

	@RequestMapping("/activity/update")
	public String update(Activity activity) {
		if (StringUtils.isNotEmpty(activity.getActivityCode())) {
			activityService.update(activity);
		}
		return "redirect:/activity/list";
	}
}