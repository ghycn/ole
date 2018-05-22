package com.etiansoft.ole.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.etiansoft.ole.po.SysConfiguration;
import com.etiansoft.ole.sys.service.SysConfigurationService;

@Controller
public class SysConfigurationController {

	@Autowired
	private SysConfigurationService configService;

	@RequestMapping("/config/info")
	public String info(ModelMap model) {
		model.put("configs", configService.getConfig());
		return "config/info";
	}
	
	@RequestMapping("/config/update")
	public String update(SysConfiguration config,String taxRate,ModelMap model) {
		//configService.update(config);
		config.setTaxRate(taxRate);
		config.setIsUsed(0);
		configService.add(taxRate);
		model.put("configs", configService.getConfig());
		return "redirect:/config/info";
	}
	
	@RequestMapping("/config/setIsUsed")
	public String setIsUsed (ModelMap model,Integer id){
		configService.setIsUsed(id);
		model.put("configs", configService.getConfig());
		return "redirect:/config/info";

	}
}