package com.etiansoft.ole.pr.service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.CustomerDao;
import com.etiansoft.ole.dao.PFProjectFeeDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.dao.SysUserDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.pr.query.ProjectQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.vo.PFProjectFeeVo;
import com.etiansoft.ole.vo.PrProjectVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class PrProjectService extends OleHibernateService<PrProject> {
	@Autowired
	private PFProjectFeeDao pfProjectFeeDao;
	@Autowired
	private PrProjectDao prProjectDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private  JdbcTemplate jdbcTemplate;

	public DataTablePage getData(ProjectQuery query, DataTablePage page,String state) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = prProjectDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query,state), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, PrProjectVo.class);
		return dataTablePage;
	}

	public void save(PrProject project, String customerCode, String userCode, String openTime, String executionTime, String closeTime,String approvalOfPersonnel) {
		Customer customer = customerDao.findById(customerCode);
		project.setCustomer(customer);
		SysUser loginUser = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		project.setOpenStaff(loginUser);
		project.setOpenTime(DateTool.parseDate(openTime));
		project.setExecutionTime(DateTool.parseDate(executionTime));
		project.setCloseTime(DateTool.parseDate(closeTime));
		project.setStatus(ExamineStatus.APPROVING.getStatus());
		project.setCreateTime(new Date());
		project.setUpdateTime(new Date());
		project.setApprovalOfPersonnel(approvalOfPersonnel);
		String name=sysUserDao.findById(approvalOfPersonnel).getName();
		project.setApprovalOfPersonnelName(name);
		
		String sql = " select substring(tax_rate, 1,length(tax_rate)-1) tax_rate from  sys_configuration where is_used = 1  ";
		Map map = this.jdbcTemplate.queryForMap(sql);
		String taxRate = (String) map.get("tax_rate");
		project.setTaxRate(new Integer(taxRate));
		prProjectDao.save(project);
	}

	public void update(PrProject project, String openTime, String executionTime, String closeTime) {
		PrProject dbProject = prProjectDao.findById(project.getProjectCode());
		dbProject.setName(project.getName());
		dbProject.setOpenTime(DateTool.parseDate(openTime));
		dbProject.setExecutionTime(DateTool.parseDate(executionTime));
		dbProject.setCloseTime(DateTool.parseDate(closeTime));
		dbProject.setNotes(project.getNotes());
		dbProject.setStatus(ExamineStatus.APPROVING.getStatus());
		prProjectDao.update(dbProject);
	}

	public void changeStatus(String projectCode, Integer status) {
		prProjectDao.changeStatus(projectCode, status);
	}

	public List<PrProject> findbyStatus(Integer status) {
		return prProjectDao.findByStatus(status);
	}

	@SuppressWarnings("unchecked")
	private String makeHql(ProjectQuery query,String state) {
		HttpSession session = ServletContextProvider.getSession();
		String hql = "from PrProject pr where 1=1";
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			if("1".equals(state)){//如果是待批示案件查詢  
				hql += " and pr.approvalOfPersonnel = '"+user.getUserCode()+"' ";
			}			
			String openStaff = query.getOpenStaff();
			if (StringUtils.isNotEmpty(openStaff)) {
				hql += " and pr.openStaff.name like :openStaff";
			}
		} else {
			//hql += " and pr.openStaff.userCode='" + user.getUserCode() + "'";
			Integer r = null;
			Iterator<SysRole> set = user.getRoles().iterator();
			while (set.hasNext()) {
				SysRole role=set.next();
				r = role.getRoleId();
			}
/**
 * 

			if(r==6){//如果是具有審批權限的角色
				if("1".equals(state)){//如果是待批示案件查詢  
					hql += " and pr.approvalOfPersonnel = '"+user.getUserCode()+"' ";
				}
				if("2".equals(state)){//如果是案件一欄查詢 
					//hql += " and pr.openStaff = '"+user.getUserCode()+"' ";
					hql += " and (pr.openStaff = '"+user.getUserCode()+"' or pr.approvalOfPersonnel = '"+user.getUserCode()+"') ";
				}
				if("3".equals(state)){//请款申请
					hql += " and pr.openStaff = '"+user.getUserCode()+"' ";
					//hql += " and (pr.openStaff = '"+user.getUserCode()+"' or pr.approvalOfPersonnel = '"+user.getUserCode()+"') ";
				}
			}else{
				if("1".equals(state)){//如果是待批示案件查詢  
					hql += " and pr.approvalOfPersonnel = '"+user.getUserCode()+"' ";
				}
				if("2".equals(state)){//如果是案件一欄查詢 
					hql += " and pr.openStaff = '"+user.getUserCode()+"' ";
					//申请人跟审批人都可以看
					//hql += " and (pr.openStaff = '"+user.getUserCode()+"' or pr.approvalOfPersonnel = '"+user.getUserCode()+"') ";
				}
				if("3".equals(state)){//请款申请
					hql += " and pr.openStaff = '"+user.getUserCode()+"' ";
					//申请人跟审批人都可以看
					//hql += " and (pr.openStaff = '"+user.getUserCode()+"' or pr.approvalOfPersonnel = '"+user.getUserCode()+"') ";
				}
			}
 */			
		}
		String openStaff = query.getOpenStaff();
		if (StringUtils.isNotEmpty(openStaff)) {
			hql += " and pr.openStaff.name like :openStaff";
		}
//
//		if (query.getFlag() != null && query.getFlag()) {
//			List<String> permissions = (List<String>) session.getAttribute(Constants.PERMISSIONS);
//			if (!permissions.contains(Constants.ALL_PROJECTS_PERMISSION)) {
//				Set<PrProject> projects = user.getProjects();
//				StringBuilder builder = new StringBuilder();
//				for (PrProject project : projects) {
//					builder.append("'").append(project.getProjectCode()).append("',");
//				}
//				String projectCodes = null;
//				if (builder.length() > 0) {
//					projectCodes = builder.substring(0, builder.length() - 1);
//				}
//				if (projectCodes == null) {
//					hql += " and 1!=1";
//				} else {
//					hql += " and pr.projectCode in (" + projectCodes + ")";
//				}
//			}
//		}

		String projectCode = query.getProjectCode();
		if (StringUtils.isNotEmpty(projectCode)) {
			hql += " and pr.projectCode like :projectCode";
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			hql += " and name like :projectName";
		}
		String status = query.getStatus();
		try {
			Integer.parseInt(status);
			hql += " and status = :status";
		} catch (Exception e) {
		}
		String amount = query.getSelectDropDown();
		if ("1".equals(amount)) {
			hql += " and pr.invoiceAmount>0 and pr.invoiceAmount<101";
		} else if ("2".equals(amount)) {
			hql += " and pr.invoiceAmount>100 and pr.invoiceAmount<1001";
		} else if ("3".equals(amount)) {
			hql += " and pr.invoiceAmount>1000 and pr.invoiceAmount<10001";
		} else if ("4".equals(amount)) {
			hql += " and pr.invoiceAmount>10000";
		}
		if("3".equals(state)){//如果是请款申请
			String projectState=query.getProjectState();
				if ("1".equals(projectState)) {//已结案
					hql += " and pr.status = 4 ";
				} else if ("2".equals(projectState)) {//未结案
					hql += " and pr.status in (0,1)";
				}
		}
		hql +="   order by pr.createTime desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(ProjectQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String projectCode = query.getProjectCode();
		if (StringUtils.isNotEmpty(projectCode)) {
			params.put("projectCode", "%" + projectCode + "%");
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			params.put("projectName", "%" + projectName + "%");
		}
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			String openStaff = query.getOpenStaff();
			if (StringUtils.isNotEmpty(openStaff)) {
				params.put("openStaff", "%" + openStaff + "%");
			}
		}
		String openStaff = query.getOpenStaff();
		if (StringUtils.isNotEmpty(openStaff)) {
			params.put("openStaff", "%" + openStaff + "%");
		}
		String status = query.getStatus();
		try {
			Integer iStatus = Integer.parseInt(status);
			params.put("status", iStatus);
		} catch (Exception e) {
		}
		return params;
	}

	public List<Object[]> report(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.getCurrentYear();
		}
		return prProjectDao.report(year, userCode);
	}

	public void changeInvoiceAmount(String projectCode) {
		prProjectDao.changeInvoiceAmount(projectCode);
	}

	public DataTablePage getHistoryData(ProjectQuery query, DataTablePage page,String projectCode) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = pfProjectFeeDao.findPage(currentPage, page.getiDisplayLength(), makeHistoryHql(query), makeHistoryQuery(query,projectCode));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, PFProjectFeeVo.class);
		return dataTablePage;
	}

	private Map<String, Object> makeHistoryQuery(ProjectQuery query,String projectCode) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("projectCode", projectCode);
		String status = query.getStatus();
		if(StringUtils.isNotEmpty(status)){
			Integer iStatus = Integer.parseInt(status);
			params.put("status", iStatus);
		}
		String openStaff = query.getOpenStaff();
		if(StringUtils.isNotEmpty(openStaff)){
			params.put("openStaff", "%"+openStaff+"%");
		}	
		return params;
	}

	private String makeHistoryHql(ProjectQuery query) {
//		String hql = "from Quotation a inner join PFProjectFee b  on a.QUOTATION_ID = b.QUOTATION_ID where a.projectCode = :projectCode ";
		String hql = " from PFProjectFee a  where a.prProject.projectCode = :projectCode ";
		String status = query.getStatus();
		if(StringUtils.isNotEmpty(status)){
			hql += " and a.status =:status ";
		}
		String openStaff = query.getOpenStaff();
		if(StringUtils.isNotEmpty(openStaff)){
			hql += " and a.prProject.openStaff.name like :openStaff ";
		}
		return hql;
	}
	/**
	 * 关闭案件
	 * @param projectCode
	 * @return
	 */
	public String closeProject(String projectCode) {
		String sql = " select  status from  cost_analysis where project_code = '"+projectCode+"'  ";
		List list = jdbcTemplate.queryForList(sql);
		boolean state = true;
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			if((Integer)map.get("status")!=4){
				state=false;
				break;
			}
		}
		if(list.size()==0){
			state=false;
		}
		if(state==true){
			String s = " update  pr_project set status = 4  where  project_code = '"+projectCode+"' "; 
			jdbcTemplate.update(s);
			return "1";
		}else{
			return "2";
		}
			
	}
	/**
	 * 查询待批示数量
	 * @param string
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int queryCount(String sql, String userCode) {
		if(sql.contains("?")){
			Object[]obj={userCode};
			return this.jdbcTemplate.queryForInt(sql,obj);
		}else{
			return this.jdbcTemplate.queryForInt(sql);
		}

	}

	public void updatePrProjectStatus(PrProject prProject) {
		// TODO Auto-generated method stub
		prProjectDao.update(prProject);
	}
}