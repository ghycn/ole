package com.etiansoft.ole.pf.service;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.ActivityDao;
import com.etiansoft.ole.dao.OtherPFProjectFeeDao;
import com.etiansoft.ole.dao.PFProjectFeeDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.dao.QuotationDao;
import com.etiansoft.ole.dao.SuSupplierDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.pf.query.PfProjectFeeQuery;
import com.etiansoft.ole.po.OtherPFProjectFee;
import com.etiansoft.ole.po.OtherPFProjectFeeType;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.QuotationListPFProjectFee;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.PFProjectFeeVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class PfProjectFeeService extends OleHibernateService<PFProjectFee> {

	@Autowired
	private PFProjectFeeDao pfProjectFeeDao;
	@Autowired
	private PrProjectDao projectDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private SuSupplierDao supplierDao;
	@Autowired
	private OtherPFProjectFeeDao otherPFProjectFeeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DataTablePage getData(PfProjectFeeQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = pfProjectFeeDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, PFProjectFeeVo.class);
		return dataTablePage;
	}
	public DataTablePage getAppFeeData(Integer quotationId, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = pfProjectFeeDao.findPage(currentPage, page.getiDisplayLength(), hql(quotationId), makeQueryHql(quotationId));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, PFProjectFeeVo.class);
		return dataTablePage;
	}
	public List<PFProjectFeeVo> findByQuotationId(Integer quotationId) {
		List<PFProjectFee> pfProjectFees=pfProjectFeeDao.findByQuotationId(quotationId);
		return VoTool.convert(pfProjectFees, PFProjectFeeVo.class);
	}
	@SuppressWarnings("unchecked")
	private String hql(Integer quotationId){
		String hql = "from PFProjectFee cm where 1=1";
			hql += " and cm.quotation.quotationId =:quotationId";
		return hql;
	}
	private LinkedHashMap<String, Object> makeQueryHql(Integer quotationId){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("quotationId", quotationId);
		return params;
	}
	@SuppressWarnings("unchecked")
	private String makeHql(PfProjectFeeQuery query) {
		HttpSession session = ServletContextProvider.getSession();
		String hql = "from PFProjectFee cm where 1=1";
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		
		if (user.isBoss()) {
			hql += " and cm.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";

//			String openStaff = query.getOpenStaff();
			
//			if (StringUtils.isNotEmpty(openStaff)) {
//				hql += " and qu.prProject.openStaff.name like :openStaff";
//			}
		} else {
//			hql += " and qu.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";
			Integer r = null;
			Iterator<SysRole> set = user.getRoles().iterator();
			while (set.hasNext()) {
				SysRole role=set.next();
				r = role.getRoleId();
			}
			if(r==6){//如果是具有審批權限的角色
				hql += " and (cm.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";
				hql += " or cm.prProject.openStaff='" + user.getUserCode() + "')";

			}else{
				hql += " and cm.prProject.openStaff='" + user.getUserCode() + "'";
			}
		}
//		if (user.isBoss()) {
//			String applicant = query.getApplicant();
//			if (StringUtils.isNotEmpty(applicant)) {
//				hql += " and cm.applicant.name like :applicant";
//			}
//		} else {
////			hql += " and cm.applicant.userCode='" + user.getUserCode() + "'";
//			//hql += " and (cm.applicant.userCode='" + user.getUserCode() + "' or cm.prProject.approvalOfPersonnel='"+ user.getUserCode() +"')";//添加人跟审批人都可以看到
//			hql += " and cm.prProject.approvalOfPersonnel='" + user.getUserCode() + "'";//审批人可以看到自己审批的请款
//		}

/*		if (query.getFlag() != null && query.getFlag()) {
			List<String> permissions = (List<String>) session.getAttribute(Constants.PERMISSIONS);
			if (!permissions.contains(Constants.ALL_PROJECTS_PERMISSION)) {
				Set<PrProject> projects = user.getProjects();
				StringBuilder builder = new StringBuilder();
				for (PrProject project : projects) {
					builder.append("'").append(project.getProjectCode()).append("',");
				}
				String projectCodes = null;
				if (builder.length() > 0) {
					projectCodes = builder.substring(0, builder.length() - 1);
				}
				if (projectCodes == null) {
					hql += " and 1!=1";
				} else {
					hql += " and cm.prProject.projectCode in (" + projectCodes + ")";
				}
			}
		}*/
		String applicant = query.getApplicant();
		if (StringUtils.isNotEmpty(applicant)) {
			hql += " and cm.applicant.name like :applicant";
		}
		String prProject = query.getPrProject();
		if (StringUtils.isNotEmpty(prProject)) {
			hql += " and cm.prProject.projectCode like :prProject";
		}
		String type = query.getType();
		if (StringUtils.isNotEmpty(type)) {
			hql += " and cm.type like :type";
		}
		
		Integer typeId = query.getTypeId();
		if (typeId != null) {
			if (typeId ==1) {
				hql += " and cm.typeId is not null";
			}else if(typeId ==2){
				hql += " and cm.typeId is null";
			}
		}
/*		String prProjectName = query.getPrProjectName();
		if (StringUtils.isNotEmpty(prProjectName)) {
			hql += " and cm.prProject.name like :prProjectName";
		}*/
		Integer status = query.getStatus();
		if (status != null) {
			hql += " and cm.status=:status";
		}
		String amount = query.getAmount();
		if ("1".equals(amount)) {
			hql += " and cm.amount>0 and cm.amount<101";
		} else if ("2".equals(amount)) {
			hql += " and cm.amount>100 and cm.amount<1001";
		} else if ("3".equals(amount)) {
			hql += " and cm.amount>1000 and cm.amount<10001";
		} else if ("4".equals(amount)) {
			hql += " and cm.amount>10000";
		}
		hql +=" order by createTime desc,applyDate desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(PfProjectFeeQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			String applicant = query.getApplicant();
			if (StringUtils.isNotEmpty(applicant)) {
				params.put("applicant", "%" + applicant + "%");
			}
		}
/*		String prProject = query.getPrProject();
		if (StringUtils.isNotEmpty(prProject)) {
			params.put("prProject", "%" + prProject + "%");
		}*/
/*		String prProjectName = query.getPrProjectName();
		if (StringUtils.isNotEmpty(prProjectName)) {
			params.put("prProjectName", "%" + prProjectName + "%");
		}*/
		String type = query.getType();
		if (StringUtils.isNotEmpty(type)) {
			params.put("type", "%" + type + "%");
		}
		Integer status = query.getStatus();
		if (status != null) {
			params.put("status", status);
		}
		return params;
	}

	public void save(String quotationIds,Integer quotationId, Integer supplierCode, String applydate, PFProjectFee projectFee, HttpSession session, Integer[] types) throws Exception {
		SysUser applicant = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		Quotation quotation = quotationDao.findById(quotationId);
		SuSupplier supplier = supplierDao.findById(supplierCode);
		projectFee.setApplyDate(DateUtil.parse(applydate, "yyyy-MM-dd"));
		projectFee.setCreateStaff(applicant);
		projectFee.setApplicant(applicant);
		projectFee.setCreateTime(new Date());
		projectFee.setStatus(ExamineStatus.APPROVING.getStatus());
		projectFee.setQuotation(quotation);
		projectFee.setSupplier(supplier);
		projectFee.setPrProject(quotation.getPrProject());
		Set<QuotationList> quotationLists = new HashSet<QuotationList>();
		Set<OtherPFProjectFee> otherPFProjectFees = new HashSet<OtherPFProjectFee>();
		setProjectFees(quotationIds,types, projectFee, quotation, quotationLists, otherPFProjectFees);
		pfProjectFeeDao.save(projectFee);
		saveQuotationListPFProjectFee(quotationIds,projectFee);
	}
	public void deleteByCode(Integer pfProjectFeeId) {
		PFProjectFee pFProjectFee = pfProjectFeeDao.findById(pfProjectFeeId);
		if(pFProjectFee!=null){
			if(pFProjectFee.getQuotationLists()!=null&&pFProjectFee.getQuotationLists().size()>0){
				pFProjectFee.getQuotationLists().clear();
			}
		}
		
//		if (quotationList != null && quotationList.size() > 0) {
//			for (QuotationList quo : quotationList) {
//				quotationListDao.deleteById(quo.getQuotationListId());
//			}
//		}
		pfProjectFeeDao.deleteById(pfProjectFeeId);
	}
	public void update(String quotationIds,Integer quotationId, Integer supplierCode, String applydate, PFProjectFee pfProjectFee, HttpSession session, Integer[] types) {
		PFProjectFee dbPFProjectFee = findById(pfProjectFee.getProjectFeeId());
		SuSupplier suppplier = supplierDao.findById(supplierCode);
		Quotation quotation = quotationDao.findById(quotationId);
		dbPFProjectFee.setSupplier(suppplier);
		dbPFProjectFee.setQuotation(quotation);
		dbPFProjectFee.setAmount(pfProjectFee.getAmount());
		dbPFProjectFee.setName(pfProjectFee.getName());
		dbPFProjectFee.setNotes(pfProjectFee.getNotes());
		dbPFProjectFee.setStatus(ExamineStatus.APPROVING.getStatus());
		dbPFProjectFee.setPrProject(quotation.getPrProject());
		Set<QuotationList> quotationLists = new HashSet<QuotationList>();
		Set<OtherPFProjectFee> otherPFProjectFees = new HashSet<OtherPFProjectFee>();
		otherPFProjectFeeDao.deleteByFeeId(pfProjectFee.getProjectFeeId());
		setProjectFees(quotationIds,types, dbPFProjectFee, quotation, quotationLists, otherPFProjectFees);
		super.update(dbPFProjectFee);
		saveQuotationListPFProjectFee(quotationIds,dbPFProjectFee);
	}

	private void setProjectFees(String quotationIds,Integer[] types, PFProjectFee dbPFProjectFee, Quotation quotation, Set<QuotationList> quotationLists, Set<OtherPFProjectFee> otherPFProjectFees) {
		if(types!=null&&types.length>0){
			for (Integer typeId : types) {
				if (typeId != null) {
					if (typeId < 100000000) {
						QuotationList quotationList = new QuotationList();
						quotationList.setQuotationListId(typeId);
						quotationLists.add(quotationList);
					} else {
						OtherPFProjectFee otherPFProjectFee = new OtherPFProjectFee();
						otherPFProjectFee.setOtherTypeId(typeId);
						otherPFProjectFee.setPfProjectFee(dbPFProjectFee);
						otherPFProjectFeeDao.save(otherPFProjectFee);
						otherPFProjectFees.add(otherPFProjectFee);
					}
				}
			}
		}

//		dbPFProjectFee.setQuotationLists(quotationLists);
		quotation.setOtherPFProjectFees(otherPFProjectFees);
	}

	private void saveQuotationListPFProjectFee(String quotationIds,PFProjectFee dbPFProjectFee){
		if(!StringUtils.isEmpty(quotationIds)){

//			if(quotationIds!=null){
//				quotationIds=quotationIds.substring(0, quotationIds.length()-1);
//			}
			if(dbPFProjectFee.getQuotationLists()!=null&&dbPFProjectFee.getQuotationLists().size()>0){
				//dbPFProjectFee.getQuotationLists().clear();
				deleteQuotactionListPFProjectFeeByFeeId(dbPFProjectFee.getProjectFeeId());
			}
			String[] qutIds = quotationIds.split(",");
			List<String> ids=new ArrayList<String>();
			UUID  uuid=UUID.randomUUID();
			for (String qid : qutIds) {
				if(!ids.contains(qid)){
					QuotationListPFProjectFee qf=new QuotationListPFProjectFee();
					qf.setQuotationListId(Integer.parseInt(qid));
					qf.setProjectFeeId(dbPFProjectFee.getProjectFeeId());
					qf.setUuid(uuid.toString());

					insertQuotactionListPFProjectFee(qf);
					ids.add(qid);
				}
			}
			
		}
	}
	public void changeStatus(Integer projectFeeId, Integer status) {
		pfProjectFeeDao.changeStatus(projectFeeId, status);
	}

	public List<PFProjectFee> findByProejctCode(String projectCode) {
		return pfProjectFeeDao.findByProjectCode(projectCode);
	}
	
	public List<String> findQuotationPfPorjects(Integer quotationId){
		return pfProjectFeeDao.findQuotationPfPorjects(quotationId);
	}
	public List<String> getQuotationListId(Integer quotationId){
		return quotationDao.getQuotationListId(quotationId);
	}
	public List<String> getQuotationListIdByPrpjectFeeId(Integer quotationId){
		return quotationDao.getQuotationListIdByPrpjectFeeId(quotationId);
	}
	
	public List<String> findQuotationItems(String type){
//		StringBuilder sb=new StringBuilder();
//		if(StringUtils.isNotEmpty(type)){
//			
//			String[] str=type.split(",");
//			for(String s:str){
//				sb.append(s).append("','");
//			}
//			sb.delete(sb.length()-3, sb.length());
//		}
		return pfProjectFeeDao.findQuotationItems(type);
	}
	
	public void insertQuotactionListPFProjectFee(QuotationListPFProjectFee pf) {
		String sql="insert into QUOTATION_LIST_PROJECTFEES(PROJECT_FEE_ID,QUOTATION_LIST_ID,uuid) values(" +
					pf.getProjectFeeId()+","+pf.getQuotationListId()+",'"+pf.getUuid()+"')";
		jdbcTemplate.update(sql);
	}
	
	public void deleteQuotactionListPFProjectFeeByFeeId(Integer FeeId) {
		String sql="delete from QUOTATION_LIST_PROJECTFEES where PROJECT_FEE_ID = "+FeeId+" ";
		
		jdbcTemplate.update(sql);
	}
	//其它请款的添加
/*	public void otherProjectFeeSave(OtherProjectFee otherProjectFee){
		String sql="insert into OTHER_PROJECTFEE(QUOTATION_ID,OTHERPROJECTFEE_TYPEID,AMOUNT) values(" +
				otherProjectFee.getQuotationId()+","+otherProjectFee.getOtherProjectFeeTypeId()+",'"+otherProjectFee.getAmount()+"')";
				jdbcTemplate.execute(sql);
	}*/
	public void otherProjectFeeSave(Integer quotationId, PFProjectFee projectFee, HttpSession session) throws Exception {
		SysUser applicant = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		Quotation quotation = quotationDao.findById(quotationId);
	//	SuSupplier supplier = supplierDao.findById(supplierCode);
		projectFee.setApplyDate(new Date());
		projectFee.setCreateStaff(applicant);
		projectFee.setApplicant(applicant);
		projectFee.setCreateTime(new Date());
		projectFee.setStatus(ExamineStatus.APPROVING.getStatus());
		projectFee.setQuotation(quotation);
		projectFee.setName(otherProjectFeeTypeList(projectFee.getTypeId()));
		//projectFee.setSupplier(supplier);
		projectFee.setPrProject(quotation.getPrProject());
		projectFee.setTypeName(otherProjectFeeTypeList(projectFee.getTypeId()));
		//projectFee.setTypeId(typeId);
//		Set<QuotationList> quotationLists = new HashSet<QuotationList>();
//		Set<OtherPFProjectFee> otherPFProjectFees = new HashSet<OtherPFProjectFee>();
		//setProjectFees(quotationIds,types, projectFee, quotation, quotationLists, otherPFProjectFees);
		pfProjectFeeDao.save(projectFee);
		//saveQuotationListPFProjectFee(quotationIds,projectFee);
	}
	public void otherProjectFeeUpdate(Integer quotationId, PFProjectFee pfProjectFee, HttpSession session) {
		PFProjectFee dbPFProjectFee = findById(pfProjectFee.getProjectFeeId());
		//SuSupplier suppplier = supplierDao.findById(supplierCode);
		Quotation quotation = quotationDao.findById(quotationId);
		//dbPFProjectFee.setSupplier(suppplier);
		dbPFProjectFee.setQuotation(quotation);
		dbPFProjectFee.setAmount(pfProjectFee.getAmount());
		dbPFProjectFee.setName(otherProjectFeeTypeList(dbPFProjectFee.getTypeId()));
		dbPFProjectFee.setNotes(pfProjectFee.getNotes());
		dbPFProjectFee.setStatus(ExamineStatus.APPROVING.getStatus());
		dbPFProjectFee.setPrProject(quotation.getPrProject());
		dbPFProjectFee.setTypeName(otherProjectFeeTypeList(dbPFProjectFee.getTypeId()));
		//Set<QuotationList> quotationLists = new HashSet<QuotationList>();
		//Set<OtherPFProjectFee> otherPFProjectFees = new HashSet<OtherPFProjectFee>();
		otherPFProjectFeeDao.deleteByFeeId(pfProjectFee.getProjectFeeId());
		//setProjectFees(quotationIds,types, dbPFProjectFee, quotation, quotationLists, otherPFProjectFees);
		super.update(dbPFProjectFee);
		//saveQuotationListPFProjectFee(quotationIds,dbPFProjectFee);
	}
	//其它请款的修改
//	public void otherProjectFeeUpdate(OtherProjectFee otherProjectFee){
//		String sql="update OTHER_PROJECTFEE set OTHERPROJECTFEE_TYPEID=" +
//				otherProjectFee.getOtherProjectFeeTypeId()+", AMOUNT="+otherProjectFee.getAmount()+" where id="+otherProjectFee.getId();
//				jdbcTemplate.update(sql);
//	}
	
	//其它请款的修改
	public List<OtherPFProjectFeeType> otherProjectFeeTypeList(){
//		List<OtherProjectFee> list=	 new ArrayList<OtherProjectFee>();
		String sql="select * from other_pf_project_fee_type";
		//List<OtherPFProjectFeeType> list=jdbcTemplate.queryForList(sql,OtherPFProjectFeeType.class);
		return this.jdbcTemplate.query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO 自动生成的方法存根
				OtherPFProjectFeeType dto = new OtherPFProjectFeeType();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				return dto;
			}});
	}
	//其它请款的修改
	public String otherProjectFeeTypeList(Integer id){
//		List<OtherProjectFee> list=	 new ArrayList<OtherProjectFee>();
		List<String>list=pfProjectFeeDao.findTypeNames(id);
	
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;	
	}
	
	//其它请款的修改
	public List<PFProjectFee> projectFeeTypeList(Integer quotationId){
//		List<OtherProjectFee> list=	 new ArrayList<OtherProjectFee>();
		String sql="select * from pf_project_fee pf where pf.QUOTATION_ID="+quotationId+" and pf.TYPE_ID is not null and pf.TYPE_NAME is not null and (pf.`STATUS`=0 or pf.`STATUS`=1)";
		return this.jdbcTemplate.query(sql, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO 自动生成的方法存根
				PFProjectFee dto = new PFProjectFee();
				dto.setTypeId(rs.getInt("type_id"));
				dto.setAmount(rs.getDouble("amount"));
				return dto;
			}});
	}
	public Double querySelectQuotationListAmount(String quotationListId) {
		String sql = " select  sum(SUB_TOTAL)amount from  quotation_list a " +
				" inner join quotation b  on a.quotation_id = b.quotation_id " +
				" inner join pr_project c on  b.project_code = c.project_code " +
				" where a.QUOTATION_LIST_ID in("+quotationListId+") " ;
		Map map = this.jdbcTemplate.queryForMap(sql);
		Double amount = (Double) map.get("amount")==null?0:(Double) map.get("amount");
		BigDecimal  b = new BigDecimal(amount);  
		double   amount1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return amount1;
	}
	public Double queryProjectFeeAmoount(String quotationListId) {
		
		String sql = " select  group_concat(distinct(cast(project_fee_id as char(10)))) project_fee_id  from quotation_list_projectfees where QUOTATION_LIST_ID in("+quotationListId+") ";
		String projectFeeId =(String) this.jdbcTemplate.queryForMap(sql).get("project_fee_id");
		String query = " select sum(amount)amount  from pf_project_fee where project_fee_id in ("+projectFeeId+") and (status=1  or status=0)";
		Map map = this.jdbcTemplate.queryForMap(query);
		Double projectFeeAmoount = (Double) map.get("amount")==null?0:(Double) map.get("amount");
		BigDecimal  b = new BigDecimal(projectFeeAmoount);  
		double   projectFeeAmoount1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return projectFeeAmoount1;
	}
	public double queryAlreadyPleaseAmount(Integer quotationId) {
		// TODO Auto-generated method stub
		String  sql = " select sum(a.amount)amount from pf_project_fee as a where a.quotation_id = "+quotationId+" and a.status in(0, 1) ";
		double amount =(double) this.jdbcTemplate.queryForMap(sql).get("amount");
		return amount;
	}
}
