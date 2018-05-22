package com.etiansoft.ole.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.supplier.query.SupplierQuery;
import com.etiansoft.ole.supplier.service.SupplierService;

@Controller
public class SupplierApprovingController {
	@Autowired
	private SupplierService supplierService;

	@RequestMapping(value = "/approving/edSupplier/list")
	public String list() {
		return "/edSupplier/approving/list";
	}

	@ResponseBody
	@RequestMapping(value = "/approving/edSupplier/data")
	public DataTablePage data(SupplierQuery query, DataTablePage page) {
		return supplierService.getData(query, page);
	}

	@ResponseBody
	@RequestMapping(value = "/approving/edSupplier/approve")
	public void approve(Integer supplierCode) {
		supplierService.changeStatus(supplierCode, ExamineStatus.APPROVED.getStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/approving/edSupplier/reject")
	public void reject(Integer supplierCode) {
		supplierService.changeStatus(supplierCode, ExamineStatus.REJECTED.getStatus());
	}
}
