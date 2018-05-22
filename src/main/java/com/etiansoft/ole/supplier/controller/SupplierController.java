package com.etiansoft.ole.supplier.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.SuSupplierContactDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SuSupplierContact;
import com.etiansoft.ole.po.SuSupplierType;
import com.etiansoft.ole.supplier.query.SupplierQuery;
import com.etiansoft.ole.supplier.service.SuSupplierTypeService;
import com.etiansoft.ole.supplier.service.SupplierService;
import com.etiansoft.ole.util.PdfReportUtil;
import com.etiansoft.ole.vo.SuSupplierTypeVo;
import com.etiansoft.ole.vo.SuSupplierVo;
import com.etiansoft.ole.vo.YearInfoVo;

@Controller
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SuSupplierContactDao supplierContactDao;
	@Autowired
	private SuSupplierTypeService supplierTypeService;

	@Autowired
	@RequestMapping("/supplier/list")
	public String list() {
		return "supplier/list";
	}

	@ResponseBody
	@RequestMapping("/supplier/data")
	public DataTablePage data(SupplierQuery supplier, DataTablePage page) {
		return supplierService.getData(supplier, page);
	}

	@RequestMapping("/supplier/info")
	public String info(Integer supplierCode, ModelMap model,String yearValue) {
		model.put("supplier", supplierService.findById(supplierCode));
		List<SuSupplierContact> contacts = supplierService.findContactBySupplierCode(supplierCode);
		Double money = supplierService.queryMoney(supplierCode,yearValue);
		List<YearInfoVo> yearList = supplierService.queryYearInfo();
		model.put("contacts", contacts);
		model.put("money", money);
		model.put("yearList", yearList);
		model.put("supplierCode", supplierCode);
		return "supplier/info";
	}

	@RequestMapping("/supplier/exportPdf")
	public void exportPdf(HttpServletResponse response) throws Exception {
		List<SuSupplierVo> list = supplierService.findByRemove();
		PdfReportUtil.export(list, SuSupplierVo.class, response);
	}

	@RequestMapping("/supplier/add")
	public String addSupplier(ModelMap model) {
		List<SuSupplierTypeVo> typeList = supplierTypeService.findByVoRemove();
		model.put("types", typeList);
		return "supplier/add";
	}

	@RequestMapping("/supplier/save")
	public String save(SuSupplier supplier) {
		supplier.setStatus(ExamineStatus.APPROVING.getStatus());
		supplierService.saveSupplier(supplier);
		return "redirect:/supplier/list";
	}

	@RequestMapping("/supplier/edit")
	public String edit(Integer supplierCode, ModelMap model) {
		if (supplierCode != null) {
			model.put("edit", true);
			model.put("supplier", supplierService.findAllByCode(supplierCode));
			model.put("types", supplierTypeService.findByVoRemove());
		}
		return "supplier/edit";
	}

	@RequestMapping("/supplier/update")
	public String update(SuSupplier supplier) {
		supplierService.updateAll(supplier);
		return "redirect:/supplier/list";
	}

	@RequestMapping("/supplier/doDelete")
	public String doDelete(Integer supplierCode) {
		if (supplierCode != null) {
			supplierService.updateDelete(supplierCode);
		}
		return "redirect:/supplier/list";
	}

	@ResponseBody
	@RequestMapping("/supplier/deleteContact")
	public String deleteContact(Integer supplierContactId) {
		if (supplierContactId != null) {
			supplierContactDao.deleteBysupplierContactId(supplierContactId);
		}
		return "success";
	}

	@RequestMapping(value = "/susupplier/type")
	public String add(ModelMap model) {
		model.put("types", supplierTypeService.findByVoRemove());
		return "/supplier/supplierType";
	}

	@RequestMapping(value = "/supplier/supplierTypeSave")
	public String supplierTypeSave(SuSupplierType supplierType) {
		String names = supplierType.getName();
		if (StringUtils.isNotEmpty(names)) {
			supplierTypeService.saveMuli(names);
			return "redirect:/supplier/list";
		}
		return "redirect:/supplier/list";
	}

	@ResponseBody
	@RequestMapping(value = "/supplier/inputSupType")
	public String inputSupType(String name) {
		if (name != null && name.length() > 0) {
			supplierTypeService.inputSupType(name);
			return "true";
		}
		return "false";
	}

	@ResponseBody
	@RequestMapping("/supplierType/deleteType")
	public String deleteType(Integer typeId) {
		supplierTypeService.update(typeId);
		return "success";
	}
	@ResponseBody
	@RequestMapping("/supplier/chooseYear")
	public Double chooseYear(String yearValue,Integer supplierCode) {
		Double money = supplierService.queryMoney(supplierCode,yearValue);
		return money;
	}
}