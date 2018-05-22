package com.etiansoft.ole.quotation.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.CostAnalysisDao;
import com.etiansoft.ole.dao.CustomerDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.dao.QuotationDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.query.QuotationQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.QuotationListVo;
import com.etiansoft.ole.vo.QuotationVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class QuotationService extends OleHibernateService<Quotation> {

	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private PrProjectDao projectDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CostAnalysisDao costAnalysisDao;
	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public DataTablePage getData(QuotationQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = quotationDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, QuotationVo.class);
		List<QuotationVo> datas = (List<QuotationVo>) dataTablePage.getAaData();
		for (QuotationVo quotation : datas) {
			CostAnalysis costAnalysis = costAnalysisDao.findByQuotationId(quotation.getQuotationId());
			if (costAnalysis != null) {
				quotation.setCostStatus(costAnalysis.getStatus());
			} else {
				quotation.setCostStatus(null);
			}
		}
		return dataTablePage;
	}

	@SuppressWarnings("unchecked")
	private String makeHql(QuotationQuery query) {
		HttpSession session = ServletContextProvider.getSession();
		String hql = "from Quotation qu where 1=1";
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			hql += " and qu.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";

			String openStaff = query.getOpenStaff();
			
			if (StringUtils.isNotEmpty(openStaff)) {
				hql += " and qu.prProject.openStaff.name like :openStaff";
			}
		} else {
//			hql += " and qu.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";
			Integer r = null;
			Iterator<SysRole> set = user.getRoles().iterator();
			while (set.hasNext()) {
				SysRole role=set.next();
				r = role.getRoleId();
			}
			if(r==6){//如果是具有審批權限的角色
				hql += " and (qu.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";
				hql += " or qu.prProject.openStaff='" + user.getUserCode() + "')";

			}else{
				hql += " and qu.prProject.openStaff='" + user.getUserCode() + "'";
			}
		}

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
//					hql += "and prProject.projectCode in (" + projectCodes + ")";
//				}
//			}
//		}

//		String prProject = query.getPrProject();
//		if (StringUtils.isNotEmpty(prProject)) {
//			hql += " and prProject.projectCode like :prProject";
//		}
		String applicant = query.getApplicant();
		if (StringUtils.isNotEmpty(applicant)) {
			hql += " and applicant.name like :applicant";
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			hql += " and prProject.name like :projectName";
		}
		if (user.isBoss()) {
			if (StringUtils.isNotEmpty(applicant)) {
				hql += " and applicant.name like :applicant";
			}
		} else {
//			hql += " and applicant.userCode like '" + user.getUserCode() + "'";
		}
		Integer status = query.getStatus();
		if (status != null) {
			hql += " and status=:status";
		}
		hql += " order by caseTime desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(QuotationQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String prProject = query.getPrProject();
		if (StringUtils.isNotEmpty(prProject)) {
			params.put("prProject", "%" + prProject + "%");
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			params.put("projectName", "%" + projectName + "%");
		}
		String applicant = query.getApplicant();
		if (StringUtils.isNotEmpty(applicant)) {
			params.put("applicant", "%" + applicant + "%");
		}
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			if (StringUtils.isNotEmpty(applicant)) {
				params.put("applicant", "%" + applicant + "%");
			}
		}
		Integer status = query.getStatus();
		if (status != null) {
			params.put("status", status);
		}
		return params;
	}

	public void save(Quotation quotation, String caseTime) {
		SysUser applicant = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		quotation.setDate(new Date());
		quotation.setApplicant(applicant);
		quotation.setCaseTime(DateUtil.parse(caseTime, "yyyy-MM-dd"));
		quotation.setStatus(ExamineStatus.APPROVING.getStatus());
		quotationDao.save(quotation);
	}

	public Object findAllByCode(Integer quotationId) {
		return quotationDao.findById(quotationId);
	}

	public List<QuotationVo> findbyStatus(Integer status) {
		List<Quotation> quotations = quotationDao.findbyStatus(status);
		return VoTool.convert(quotations, QuotationVo.class);
	}

	public void update(String casetime, Quotation quotation) {
		Quotation quo = quotationDao.findById(quotation.getQuotationId());
		quo.setCaseTime(DateUtil.parse(casetime, "yyyy-MM-dd"));
		quo.setStatus(ExamineStatus.APPROVING.getStatus());
//		quo.setCustomer(quotation.getCustomer());
		quo.setNote(quotation.getNote());
		quo.setActivity(quotation.getActivity());
		System.out.println(quo.getCaseTime());
		quotationDao.update(quo);
	}

	public void changeStatus(Integer quotationId, Integer status) {
		quotationDao.changeStatus(quotationId, status);
	}

	public List<QuotationVo> findbyProject(String projectCode) {
		List<Quotation> quotations = quotationDao.findByProject(projectCode);
		if(quotations!=null&&quotations.size()>0){
			
			for(Quotation q:quotations){
				CostAnalysis costAnalysis = costAnalysisDao.findByQuotationId(q.getQuotationId());
				if (costAnalysis != null) {
					q.setCostStatus(costAnalysis.getStatus());
				} else {
					q.setCostStatus(null);
				}
				Double amountTotal= quotationDao.totalAmount(q.getQuotationId());
				Double otherAmountTotal=quotationDao.otherTotalAmount(q.getQuotationId());
				q.setAmountTotal(amountTotal+otherAmountTotal);
			}
		}
		return VoTool.convert(quotations, QuotationVo.class);
	}

	public void changeTaxTotal(Integer quotationId) {
		quotationDao.changeTaxTotal(quotationId);
	}

	public void updateItem(QuotationList quotationItem, Integer quotationId) {
		Quotation quotation = quotationDao.findById(quotationId);
		Set<QuotationList> list = quotation.getQuotationList();
		list.add(quotationItem);
		quotation.setQuotationList(list);
		super.update(quotation);
	}

	public void changeInvoiceAmount(Quotation quotation) {
		String projectCode = quotation.getPrProject().getProjectCode();
		if (StringUtils.isNotEmpty(projectCode)) {
			prProjectService.changeInvoiceAmount(projectCode);
		}
	}

	public List<QuotationListVo> findByQuotationId(Integer quotationId) {
		Quotation quotation = quotationDao.findById(quotationId);
		if (quotation != null) {
			Set<QuotationList> quotationList = quotation.getQuotationList();
//			List<QuotationList> quotList=new ArrayList<QuotationList>();
//			if(quotationList.size()>0){
//				for(QuotationList st:quotationList){
//					quotList.add(st);
//				}
//				
//			}
//			Collections.sort(quotList,Collator.getInstance(java.util.Locale.CHINA));//注意：是根据的汉字的拼音的字母排序的，而不是根据汉字一般的排序方法
//			Collections.reverse(quotList);//不指定排序规则时，也是按照字母的来排序的
			if (quotationList.size() > 0) {
				List<QuotationList> list = new ArrayList<QuotationList>(quotationList.size());
//				list.addAll(quotList);
				list.addAll(quotationList);
				return VoTool.convert(list, QuotationListVo.class);
			}
		}
		return Collections.emptyList();
	}
	public List<QuotationList> getQuotationList(Integer quotationId) {
		List<QuotationList> list = quotationDao.getQuotationList(quotationId);
			if (list.size() > 0) {
				return list;
			}
		return Collections.emptyList();
	}
	
	public List<String> getQuotationListId(Integer quotationId){
		return quotationDao.getQuotationListId(quotationId);
	}
	public Double getAmountTotal(Integer quotationId){
		Double amountTotal= quotationDao.totalAmount(quotationId);
		return amountTotal;
	}
	public Double otherTotalAmount(Integer quotationId){
		Double otherAmountTotal=quotationDao.otherTotalAmount(quotationId);
		return otherAmountTotal;
	}

	public void changeMoney(String  projectCode,Double prInvoiceAmount, Double quInvoiceAmount) {
		prInvoiceAmount=prInvoiceAmount==null?0:prInvoiceAmount;
		quInvoiceAmount=quInvoiceAmount==null?0:quInvoiceAmount;
		double money = prInvoiceAmount-quInvoiceAmount;
		String sql = " update pr_project set  invoice_amount = "+money+"  where  project_code = '"+projectCode+"' ";
		this.jdbcTemplate.update(sql);
	}
	@SuppressWarnings("unchecked")
	public List<QuotationList> queryQuotationList(Integer quotationId) {
			String sql = " select a.quotation_list_id, a.project_code, a.category, " +
					" a.item,a.size,a.quantity,a.unit_price,a.sub_total,a.spec,a.note,a.quotation_id,a.parent,a.supplier_id from " +
					" quotation_list as a inner join quotation as b on a.quotation_id = b.quotation_id where " +
					" b.quotation_id = "+quotationId+" order by a.category ,a.quotation_list_id asc ";
			List<QuotationList> list =  this.jdbcTemplate.query(sql, new RowMapper(){

				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					QuotationList quotationList = new QuotationList();
					quotationList.setQuotationListId(rs.getInt("quotation_list_id"));
					quotationList.setCategory(rs.getString("category"));
					quotationList.setItem(rs.getString("item"));
					quotationList.setSize(rs.getString("size"));
					quotationList.setQuantity(rs.getInt("quantity"));
					quotationList.setUnitPrice(rs.getDouble("unit_price"));
					quotationList.setSubTotal(rs.getDouble("sub_total"));
					quotationList.setSpec(rs.getString("spec"));
					quotationList.setNote(rs.getString("note"));
					quotationList.setQuotationId(rs.getInt("quotation_id"));
					quotationList.setParent(rs.getInt("parent"));
					quotationList.setSupplierCode(rs.getInt("supplier_id"));
					return quotationList;
				}
			});
//			if(list!=null && list.size()>0){
//				for(int i=0;i<list.size();i++){
//					Integer supplierCod = list.get(i).getSupplierCode();
//					String supplierSql = " SELECT SUPPLIER_CODE supplierCode,name FROM su_supplier WHERE su_supplier.SUPPLIER_CODE = "+supplierCod+" ";
//					Map map = this.jdbcTemplate.queryForMap(supplierSql);
//					SuSupplier suSupplier = new SuSupplier();
//					suSupplier.setName((String)(map.get("name")));
//					suSupplier.setSupplierCode((Integer)(map.get("supplierCode")));
// 					list.get(i).setSupplier(suSupplier);
//				}
//			}
			return list;
	}
}
