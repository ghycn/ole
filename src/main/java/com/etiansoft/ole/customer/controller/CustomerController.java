package com.etiansoft.ole.customer.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.customer.query.CustomerQuery;
import com.etiansoft.ole.customer.service.CustomerService;
import com.etiansoft.ole.customer.service.CustomerTypeService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.CustomerContact;
import com.etiansoft.ole.po.CustomerType;
import com.etiansoft.ole.util.PdfReportUtil;
import com.etiansoft.ole.vo.CustomerTypeVo;
import com.etiansoft.ole.vo.CustomerVo;
import com.etiansoft.ole.vo.YearInfoVo;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerTypeService cusTypeService;

	@RequestMapping("/custom/list")
	public String list() {
		return "custom/list";
	}

	@ResponseBody
	@RequestMapping("/custom/data")
	public DataTablePage data(CustomerQuery customer, DataTablePage page) {
		return customerService.getData(customer, page);
	}

	@RequestMapping("/custom/info")
	public String info(String customerCode, String yearValue,ModelMap model) {
		model.put("customer", customerService.findVoById(customerCode, CustomerVo.class));
		List<CustomerContact> contacts = customerService.findContactByCustomerCode(customerCode);
		Double money = customerService.queryMoney(customerCode,yearValue);
		List<YearInfoVo> yearList = customerService.queryYearInfo();
		model.put("contacts", contacts);
		model.put("money", money);
		model.put("yearList", yearList);
		model.put("customerCode", customerCode);
		return "custom/info";
	}

	@RequestMapping("/custom/exportPdf")
	public void exportPdf(HttpServletResponse response) throws Exception {
		List<CustomerVo> list = customerService.findBydel();
		PdfReportUtil.export(list, CustomerVo.class, response);
	}

	@RequestMapping("/custom/add")
	public String addCus(ModelMap model) {
		List<CustomerTypeVo> customerTypes = cusTypeService.findByVoRemove();
		model.put("customerTypes", customerTypes);
		return "/custom/add";
	}

	@RequestMapping("/custom/save")
	public String save(Customer customer) {
		customerService.saveCustomer(customer);
		return "redirect:/custom/list";
	}

	@RequestMapping("/custom/edit")
	public String edit(String customerCode, ModelMap model) {
		if (StringUtils.isNotEmpty(customerCode)) {
			model.put("edit", true);
			model.put("customer", customerService.findAllByCode(customerCode));
			model.put("customerTypes", cusTypeService.findByVoRemove());
		}
		return "custom/edit";
	}

	@RequestMapping("/custom/update")
	public String update(Customer customer) {
		customerService.updateAll(customer);
		return "redirect:/custom/list";
	}

	@RequestMapping("/custom/doDelete")
	public String doDelete(String customerCode) {
		if (customerCode != null) {
			customerService.updateDelete(customerCode);
		}
		return "redirect:/custom/list";
	}

	@ResponseBody
	@RequestMapping("/custom/deleteContact")
	public String deleteContact(CustomerContact contact) {
		if (contact.getCustomerContactId() != null) {
			customerService.deleteContanct(contact);
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("/customType/deleteType")
	public String deleteType(Integer typeId) {
		cusTypeService.update(typeId);
		return "success";
	}

	@RequestMapping(value = "/custom/type")
	public String add(ModelMap model, HttpServletResponse response, String name) {
		List<CustomerTypeVo> types = cusTypeService.findByVoRemove();
		model.put("types", types);
		return "/custom/customType";
	}

	@ResponseBody
	@RequestMapping(value = "/custom/inputCusType")
	public String inputCusType(String name) {
		if (name != null && name.length() > 0) {
			cusTypeService.saveCusType(name);
			return "true";
		}
		return "false";
	}

	@RequestMapping(value = "/custom/cusTypeSave")
	public String cusTypeSave(CustomerType customerType) {
		String names = customerType.getName();
		if (StringUtils.isNotEmpty(names)) {
			cusTypeService.saveMuli(names);
			return "redirect:/custom/list";
		}
		return "redirect:/custom/list";
	}
	@ResponseBody
	@RequestMapping("/custom//chooseYear")
	public Double chooseYear(String customerCode, String yearValue) {
		Double money = customerService.queryMoney(customerCode,yearValue);
		return money;
	}

}