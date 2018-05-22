package com.etiansoft.ole.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.customer.query.CustomerQuery;
import com.etiansoft.ole.customer.service.CustomerService;
import com.etiansoft.ole.page.DataTablePage;

@Controller
public class CustomerApprovingController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/approving/customer/list")
	public String list() {
		return "/customer/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/customer/data")
	public DataTablePage data(CustomerQuery query, DataTablePage page) {
		return customerService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/customer/approve")
	public void approve(String customerCode) {
		customerService.changeStatus(customerCode, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/customer/reject")
	public void reject(String customerCode) {
		customerService.changeStatus(customerCode, ExamineStatus.REJECTED.getStatus());
	}
}