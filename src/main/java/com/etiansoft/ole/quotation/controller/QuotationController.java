package com.etiansoft.ole.quotation.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.ant.util.StringUtils;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.customer.service.CustomerService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.CustomerContact;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SysConfiguration;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.query.QuotationQuery;
import com.etiansoft.ole.quotation.service.ActivityService;
import com.etiansoft.ole.quotation.service.QuotationListService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.supplier.service.SupplierService;
import com.etiansoft.ole.sys.service.SysConfigurationService;
import com.etiansoft.ole.util.ExportQuotationUtil;
import com.etiansoft.ole.vo.ActivityVo;
import com.etiansoft.ole.vo.CustomerVo;
import com.etiansoft.ole.vo.PrProjectVo;
import com.etiansoft.ole.vo.QuotationListVo;
import com.etiansoft.ole.vo.QuotationVo;
import com.etiansoft.ole.vo.SuSupplierTypeVo;
import com.etiansoft.ole.vo.SuSupplierVo;

@Controller
public class QuotationController {

	@Autowired
	private QuotationService quotationService;
	@Autowired
	private QuotationListService quotationListService;
	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private SysConfigurationService configService;
	@Autowired
	private SupplierService supplierService;

	@RequestMapping("/quotation/list")
	public String list(String projectCode, String status,String userCode,String approvalOfPersonnel, ModelMap model) {
		List<QuotationVo> quotations = quotationService.findbyProject(projectCode);
		Integer taxRate = prProjectService.findById(projectCode).getTaxRate();
		model.put("quotations", quotations);
		model.put("projectCode", projectCode);
		model.put("status", status);
		model.put("userCode", userCode);
		model.put("approvalOfPersonnel", approvalOfPersonnel);
		model.put("taxRate", taxRate);
		return "quotation/list";
	}
	
	@ResponseBody
	@RequestMapping("/quotation/listData")
	public void listData(String projectCode, String status,HttpServletResponse response)  throws Exception {
		try {
			StringBuffer result = new StringBuffer();
			result.append("[");
			List<QuotationVo> quotations = quotationService.findbyProject(projectCode); 
			if (quotations != null && quotations.size() != 0) {
				for (QuotationVo map : quotations) {
					double taxTotal = map.getTaxTotal()==null?0:map.getTaxTotal();
					result.append("{\"quotationId\":\"" + map.getQuotationId() + "\",");
					result.append("\"quotationName\":\"" + map.getQuotationName() + "\",");
					result.append("\"projectName\":\"" + map.getProjectName() + "\",");
					result.append("\"date\":\"" + map.getDate() + "\",");
					result.append("\"applicant\":\"" + map.getApplicant() + "\",");
					result.append("\"caseTime\":\"" + map.getCaseTime() + "\",");
					result.append("\"taxTotal\":\"" + taxTotal*(map.getTaxRate()*0.01+1) + "\",");
					result.append("\"costStatus\":\"" + map.getCostStatus() + "\",");
					result.append("\"amountTotal\":\"" + map.getAmountTotal()+ "\",");
					result.append("\"note\":\"" + map.getNote() + "\",");
					result.append("\"status\":" +map.getStatus() + "},");
				}
				result.deleteCharAt(result.length() - 1);
			}
			result.append("]");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result.toString());
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@ResponseBody
	@RequestMapping("/quotation/data")
	public DataTablePage data(QuotationQuery query, DataTablePage page) {
		return quotationService.getData(query, page);
	}

	@RequestMapping("/quotation/addQuotation")
	public String addQuotation(String projectCode, ModelMap model) {
		List<CustomerVo> customers = customerService.findAllVos(CustomerVo.class);
		List<ActivityVo> activitys = activityService.findAllVos(ActivityVo.class);
		String customerCode = prProjectService.findVoById(projectCode, PrProjectVo.class).getCustomerCode();
		String customerName = prProjectService.findVoById(projectCode, PrProjectVo.class).getCustomerName();
		List<CustomerContact> customerContact = customerService.findContactByCustomerCode(customerCode);
//		PrProjectVo project = prProjectService.findVoById(projectCode, PrProjectVo.class);
//		project.getCustomerCode();
		model.put("customerName", customerName);
		model.put("customerCode", customerCode);
		model.put("customers", customers);
		model.put("activitys", activitys);
		model.put("projectCode", projectCode);
		model.put("customerContact", customerContact);
		model.put("projectName", prProjectService.findVoById(projectCode, PrProjectVo.class).getName());
		return "quotation/addQuotation";
	}

	@ResponseBody
	@RequestMapping("/quotation/save")
	public HashMap<String, Object> save(Quotation quotation, String casetime) {
		quotationService.save(quotation, casetime);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("quotationId", quotation.getQuotationId());
		return map;
	}

	@RequestMapping("/quotation/edit")
	public String edit(Integer quotationId, Integer trIndex, ModelMap model) {
		if (quotationId != null) {
			model.put("edit", true);
			List<CustomerVo> customers = customerService.findAllVos(CustomerVo.class);
			List<ActivityVo> activitys = activityService.findAllVos(ActivityVo.class);
			String projectCode = quotationService.findById(quotationId).getPrProject().getProjectCode();
			String customerCode = prProjectService.findVoById(projectCode, PrProjectVo.class).getCustomerCode();
			String customerName = prProjectService.findVoById(projectCode, PrProjectVo.class).getCustomerName();
			model.put("trIndex", trIndex);
			model.put("customers", customers);
			model.put("customerCode", customerCode);
			model.put("customerName", customerName);
			model.put("activitys", activitys);
			Quotation quotation = quotationService.findById(quotationId);
			model.put("quotation", quotation);
			model.put("projectName", quotation.getPrProject().getName());
		}
		return "quotation/edit";
	}

	@ResponseBody
	@RequestMapping("/quotation/update")
	public HashMap<String, Object> update(String casetime, Quotation quotation) {
		quotationService.update(casetime, quotation);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("quotationId", quotation.getQuotationId());
		return map;
	}

	@RequestMapping("/quotation/quotationItem")
	public String quotationItem(Integer quotationId, String status,String userCode, ModelMap model) {
		Quotation quotation = quotationService.findById(quotationId);
		if (quotation != null) {
			if (quotation.getCustomer() != null) {
				if(org.apache.commons.lang.StringUtils.isNotEmpty(quotation.getContact())){
						CustomerContact contact = customerService.findCustomerContact(quotation.getContact());
						if(contact!=null){
							model.put("contact",contact);
						}
				}else{
					List<CustomerContact> contact = customerService.findContactByCustomerCode(quotation.getCustomer().getCustomerCode());
					if (contact.size() > 0) {
						model.put("contact", contact.get(0));
					}
				}
		
			}
		}
		String taxRate = customerService.queryTaxRate(quotationId);//查询税点
		model.put("userCode", userCode);
		model.put("status", status);
		model.put("quotationId", quotationId);
		model.put("taxRate", taxRate);
		return "quotation/quotationItem";
	}

	@ResponseBody
	@RequestMapping("/quotation/quotationItem/data")
	public List<QuotationListVo> quotationItemData(Integer quotationId) {
//		Quotation quotation = quotationService.findById(quotationId);
//		Set  set = quotation.getQuotationList();
//		System.out.println(set.size());
		List<QuotationList> quotationList = quotationService.queryQuotationList(quotationId);
		return quotationListService.createTree(quotationList);
	}

	@ResponseBody
	@RequestMapping("/quotationItem/data")
	public DataTablePage data(Integer quotationId, DataTablePage page) {
		return quotationListService.getData(quotationId, page);
	}

	@RequestMapping("/quotationItem/editItem")
	public String editItem(Integer quotationListId, Integer quotationId, ModelMap model) {
		if (quotationListId != null) {
			model.put("edit", true);
			model.put("quotationItem", quotationListService.findAllByCode(quotationListId));
		}
		model.put("quotationId", quotationId);
		return "quotation/ItemEdit";
	}

	@RequestMapping("/quotationItem/update")
	public String ItemUpdate(QuotationList quotationItem, Integer quotationId) {
		Quotation quotation = quotationService.findById(quotationId);
		quotationListService.update(quotationItem);
		quotationService.changeTaxTotal(quotationId);
		quotationService.changeInvoiceAmount(quotation);
		return "redirect:/quotation/quotationItem?quotationId=" + quotationId;
	}

	@ResponseBody
	@RequestMapping("/quotationItem/doDelete")
	public void doDelete(Integer quotationListId, Integer quotationId) {
		Quotation quotation = quotationService.findById(quotationId);
		quotationListService.deleteByCode(quotationListId);
		quotationService.changeTaxTotal(quotationId);
		quotationService.changeInvoiceAmount(quotation);
	}

	@RequestMapping("/quotationItem/add")
	public String addItem(Integer quotationId, ModelMap model) {
		List<SuSupplierTypeVo> supplierTypeList = supplierService.querySupplierTypeList();
		List<SuSupplierVo> supplierList = supplierService.querySupplierList();
		model.put("quotationId", quotationId);
		model.put("parentList", quotationService.findByQuotationId(quotationId));
		//supplierService.findAllVos(SuSupplierVo.class);
		model.put("supplierList", supplierList);
		model.put("supplierTypeList", supplierTypeList);
		return "quotation/addItem";
	}
	@ResponseBody
	@RequestMapping("/quotationItem/querySuppliers")
	public  List<SuSupplierVo> querySuppliers(String  type) throws UnsupportedEncodingException{
		   URLDecoder urlDecoder = new URLDecoder();    
		   type = urlDecoder.decode(type,"UTF-8");
		   List<SuSupplierVo> suSupplierList = supplierService.querySuSupplierListByType(type);
		   return suSupplierList;
	}
	@ResponseBody
	@RequestMapping("/quotationItem/saveItem")
	public HashMap<String, Object> saveItem(QuotationList quotationItem, Integer quotationId) {
		
		
		String category=quotationListService.queryQuotationListByQuotationId(quotationId,quotationItem.getCategory());
		
		if("".equals(category)){
			quotationItem.setParent(0);
		}
		
		
		if(quotationItem.getQuotationListId()!=null){
			quotationItem.setParent(0);
		}
		quotationListService.saveItem(quotationItem);
		quotationService.updateItem(quotationItem, quotationId);
		quotationService.changeTaxTotal(quotationId);
		Quotation quo = quotationService.findById(quotationId);
		quotationService.changeInvoiceAmount(quo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("quotationListId", quotationItem.getQuotationListId());
		return map;
	}
			
	@ResponseBody
	@RequestMapping("/quotation/export")
	public String export(HttpServletResponse response, Integer quotationId) throws Exception {
		Customer customer = new Customer();
		CustomerContact contact = new CustomerContact();
		Quotation quotation = quotationService.findById(quotationId);
		if (quotation != null) {
			if (quotation.getCustomer() != null) {
				customer = quotation.getCustomer();
				List<CustomerContact> contacts = customerService.findContactByCustomerCode(quotation.getCustomer().getCustomerCode());
				if (contacts.size() > 0) {
					contact = contacts.get(0);
				}
			}
		}
		SysConfiguration config = new SysConfiguration();
		String taxRate = customerService.queryTaxRate(quotationId);//查询税点
		config.setTaxRate(taxRate);
//		List<SysConfiguration> configList = configService.getConfig();//税点list
//		SysConfiguration config = new SysConfiguration();
//		for(int i = 0;i<configList.size();i++){
//			if(configList.get(i).getIsUsed()==1){
//				config =  configList.get(i);
//			}
//		}
		
		OutputStream os = null;
		InputStream in = MessageUtil.class.getClassLoader().getResourceAsStream("excelTemplate/quotation.xls");
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String("quotation.xls".getBytes("gb2312"), "iso8859-1"));
			os = response.getOutputStream();
			if (quotation != null && quotation.getQuotationList().size() > 0) {
				HSSFWorkbook wbCreat = new HSSFWorkbook();
				HSSFWorkbook wb = new HSSFWorkbook(in);
				try {
					ExportQuotationUtil.export(wbCreat, wb, true, quotation, customer, contact, config);
				} catch (Exception e) {
					throw new Exception("导出Excel出错：" + e);
				}
				HSSFSheet sheet = wbCreat.getSheet("sheet1");
				ByteArrayOutputStream byteArrayOut =  new ByteArrayOutputStream();
				//String  InputimagePath  =  ; //"C:/Users/Administrator/Desktop/ole.png"
				String InputimagePath = StringUtils.class.getClassLoader().getResource("/").getPath()+ ("excelTemplate/ole.png");
				BufferedImage  bufferImg  =  ImageIO.read(new  File(InputimagePath));
				ImageIO.write(bufferImg,"png",byteArrayOut);
				//设置图片大小，位置
				HSSFClientAnchor  anchor  =  new  HSSFClientAnchor(0,0,50,40,(short) 6,0,(short)7,2);
				HSSFPatriarch patri = sheet.createDrawingPatriarch(); 
				patri.createPicture(anchor ,wbCreat.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				//wb.getSheet("");
				wbCreat.write(os);
			}
		} finally {
			os.flush();
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(in);
		}
		return "success";
	}
}