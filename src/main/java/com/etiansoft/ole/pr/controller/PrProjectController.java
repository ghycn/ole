package com.etiansoft.ole.pr.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.tools.ant.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.Utils;

import com.etiansoft.ole.cost.service.CostAnalysisService;
import com.etiansoft.ole.customer.service.CustomerService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.pr.query.ProjectQuery;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.pr.service.SerialNumberService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.user.service.PermissionService;
import com.etiansoft.ole.user.service.RoleService;
import com.etiansoft.ole.vo.CustomerTypeVo;
import com.etiansoft.ole.vo.CustomerVo;
import com.etiansoft.ole.vo.PrProjectVo;

@Controller
public class PrProjectController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private CostAnalysisService costAnalysisService;
	@Autowired
	private PermissionService permissionService;
	@RequestMapping(value = "/project/list")
	public String list(ModelMap model) {
		return "/project/list";
	}
 
	@ResponseBody
	@RequestMapping(value = "/project/data")
	public DataTablePage data(ProjectQuery query, DataTablePage page,String state) {
		return prProjectService.getData(query, page,state);
	}

	@RequestMapping(value = "/project/info")
	public String info(PrProjectVo project, ModelMap model) {
		project = prProjectService.findVoById(project.getProjectCode(), PrProjectVo.class);
		Integer	taxRate = project.getTaxRate();
		Double invoiceAmount = project.getInvoiceAmount()==null?0:project.getInvoiceAmount();
		project.setInvoiceAmount(invoiceAmount*((taxRate*0.01)+1));
		model.put("project", project);
		return "/project/info";
	}

	@RequestMapping(value = "/project/add")
	public String add(ModelMap model) {
		int serialNumber = serialNumberService.findSerialNumber();
		serialNumberService.updateId(serialNumber);
//		List<CustomerVo> customers = customerService.findAllVos(CustomerVo.class);
		List<CustomerTypeVo> customersTypeList = customerService.queryCustomersType();//查询客户类型
		List<CustomerVo> customers = customerService.queryCustomerList();
//		SysRole boss = roleService.findById(1);//老板角色  --具有审批权限的角色
//		Set<SysUser> users = boss.getUsers();
//		SysRole leader = roleService.findById(6);//领导  --具有审批权限的角色
//		Set<SysUser> leaders = leader.getUsers();
//		users.addAll(leaders);//具有审批权限的用户合并到一起
		List<SysUser> users = sysUserService.queryBossName();//查询具有审批权限的人
		model.put("users", users);
		model.put("customers", customers);
		model.put("customersTypeList", customersTypeList);
		model.put("serialNumber", serialNumber);
		return "project/add";
	}
	//根据客户类型查询客户
	@ResponseBody
	@RequestMapping(value="/project/queryCustomers")
	public List<CustomerVo> queryCustomers (String customerTypeName) throws UnsupportedEncodingException{
		   URLDecoder urlDecoder = new URLDecoder();    
		   String type = urlDecoder.decode(customerTypeName,"UTF-8");
		   List<CustomerVo>  customersList = customerService.queryCustomerListByType(type);
		   return customersList;
	}
	
	@RequestMapping(value = "/project/save")
	public String save(PrProject project, String customerCode, String userCode, String openTime, String executionTime, String closeTime,String approvalOfPersonnel) {
		prProjectService.save(project, customerCode, userCode, openTime, executionTime, closeTime,approvalOfPersonnel);
		return "redirect:/project/list";
	}

	@RequestMapping(value = "/project/edit")
	public String edit(String projectCode, ModelMap model) {
		PrProjectVo project = prProjectService.findVoById(projectCode, PrProjectVo.class);
		List<CustomerVo> customers = customerService.findAllVos(CustomerVo.class);
		model.put("customers", customers);
		model.put("project", project);
		return "project/edit";
	}

	@RequestMapping(value = "/project/update")
	public String update(PrProject project, String openTime, String executionTime, String closeTime) {
		prProjectService.update(project, openTime, executionTime, closeTime);
		return "redirect:/project/list";
	}
	/**
	 * 查询请款记录
	 */
	@RequestMapping(value = "/project/checkHistory")
	public String checkHistory(String projectCode, ModelMap model) {
		Integer taxRate = prProjectService.findById(projectCode).getTaxRate();//税点
		model.put("projectCode", projectCode);
		model.put("taxRate", taxRate);
		return "/project/payout_history";
	}
	@ResponseBody
	@RequestMapping(value="/project/getHistoryData")
	public DataTablePage getHistoryData(ProjectQuery query, DataTablePage page,String projectCode) {
		return prProjectService.getHistoryData(query, page, projectCode);
	}
	/**
	 * 关闭案件
	 */
	@ResponseBody
	@RequestMapping(value="/project/closeProject")
	public String closeProject (String projectCode){
		String state = prProjectService.closeProject(projectCode);
		return state;
	}
	
	/**
	 * 查询待批示数量
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/project/queryCount")
	public Integer queryCount(HttpSession session) throws IOException{
		
		Integer roleId = (Integer) session.getAttribute("roleId");
		SysUser loginUser = (SysUser) session.getAttribute("loginUser");
		String  userCode = loginUser.getUserCode();
		List<String> jurisdiction = permissionService.findRoleJurisdictions(loginUser,2);//角色权限菜单
//		String path = session.getServletContext().getContextPath();
        InputStream in = Main.class.getResourceAsStream("/sql.properties");
        //创建一个Properties容器 
        Properties props = new Properties(); 
        props.load(in);
        Enumeration en = props.propertyNames();
		List<String> list = new ArrayList<String>();

        while (en.hasMoreElements()) {
        	String key = (String) en.nextElement();
    		for(int i=0;i<jurisdiction.size();i++){
    	     	if(jurisdiction.get(i).equals(key)){
            		String property = props.getProperty (key);
            		list.add(property);
            	}
    		}
        }
		int num = 0;
		for(int i=0;i<list.size();i++){
			String sql = list.get(i);
			int count = prProjectService.queryCount(sql,userCode);
			num += count;
		}
		return num;
	}
	/**
	 * 案件关闭（彻底关闭）
	 * 
	 */
	@ResponseBody
	@RequestMapping("/project/closed")
	public void projectClosed(String  projectCode){
		PrProject prProject = prProjectService.findById(projectCode);
		prProject.setStatus(3);//案件关闭状态
		prProjectService.updatePrProjectStatus(prProject);
		
	}
}