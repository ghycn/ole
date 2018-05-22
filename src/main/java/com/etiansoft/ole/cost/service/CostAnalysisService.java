package com.etiansoft.ole.cost.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.CostAnalysisDao;
import com.etiansoft.ole.dao.CostAnalysisItemDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.dao.QuotationDao;
import com.etiansoft.ole.dao.QuotationListDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.CostAnalysisItem;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.vo.CostAnalysisVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class CostAnalysisService extends OleHibernateService<PrProject> {

	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private QuotationListDao quotationListDao;
	@Autowired
	private PrProjectDao prProjectDao;
	@Autowired
	private CostAnalysisDao costAnalysisDao;
	@Autowired
	private CostAnalysisItemDao costAnalysisItemDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CostAnalysis findByQuotationId(Integer quotationId) {
		return costAnalysisDao.findByQuotationId(quotationId);
	}

	public List<CostAnalysisItem> findItemByAnalysisId(Integer costAnalysisId) {
		return costAnalysisItemDao.findItemByAnalysisId(costAnalysisId);
	}

	public void saveCostAnalysis(CostAnalysis costAnalysis, String projectCode, Integer quotationId) {
		PrProject project = prProjectDao.findById(projectCode);
		Quotation quotation = quotationDao.findById(quotationId);
		costAnalysis.setProject(project);
		costAnalysis.setQuotation(quotation);
		costAnalysis.setStatus(ExamineStatus.APPROVING.getStatus());
		if (costAnalysis.getCostAnalysisId() == null) {
			costAnalysisDao.save(costAnalysis);
		} else {
			costAnalysisDao.merge(costAnalysis);
		}
	}

	public void saveCostAnalysisItem(CostAnalysis costAnalysis) {
		List<CostAnalysisItem> items = costAnalysis.getCostAnalysisItems();
		for (CostAnalysisItem costAnalysisItem : items) {
			CostAnalysisItem item = new CostAnalysisItem();
			item.setCostAnalysis(costAnalysis);
			QuotationList quotationList = quotationListDao.findById(costAnalysisItem.getQuotationList().getQuotationListId());
			item.setQuotationList(quotationList);
			item.setTaxQuotation(costAnalysisItem.getTaxQuotation());
			if (costAnalysisItem.getCostAnalysisItemId() != null) {
				item.setCostAnalysisItemId(costAnalysisItem.getCostAnalysisItemId());
				costAnalysisItemDao.merge(item);
			} else {
				costAnalysisItemDao.save(item);
			}
		}
	}

	public DataTablePage getData(CostAnalysis costAnalysis, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = costAnalysisDao.findPage(currentPage, page.getiDisplayLength(), makeHql(), makeQuery());
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, CostAnalysisVo.class);
		return dataTablePage;
	}

	@SuppressWarnings("unchecked")
	private String makeHql() {
		HttpSession session = ServletContextProvider.getSession();
		List<String> permissions = (List<String>) session.getAttribute(Constants.PERMISSIONS);
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		String hql = "from CostAnalysis cost where 1=1 and status = 0  and cost.project.approvalOfPersonnel = "+user.getUserCode()+" "; // and cost.project.approvalOfPersonnel = "+user.getUserCode()+"
//		if (!permissions.contains(Constants.ALL_PROJECTS_PERMISSION)) {  
//			Set<PrProject> projects = user.getProjects();
//			StringBuilder builder = new StringBuilder();
//			for (PrProject project : projects) {
//				builder.append("'").append(project.getProjectCode()).append("',");
//			}
//			String projectCodes = null;
//			if (builder.length() > 0) {
//				projectCodes = builder.substring(0, builder.length() - 1);
//			}
//			if (projectCodes == null) {
//				hql += " and 1!=1";
//			} else {
//				hql += " and cost.project.projectCode in (" + projectCodes + ")";
//			}
//		}
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery() {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		return params;
	}

	public void changeStatus(Integer costAnalysisId, Integer status) {
		costAnalysisDao.changeStatus(costAnalysisId, status);
		String sql = " select quotation_id from cost_analysis where cost_analysis_id = "+costAnalysisId+" ";
		Map map = this.jdbcTemplate.queryForMap(sql);
		Integer quotationId = (Integer) map.get("quotation_id");
		String editSql = " update quotation  set status = 1 where quotation_id = "+quotationId+" ";
		this.jdbcTemplate.update(editSql);

	}

	public List<Object[]> totalCostReport(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.getCurrentYear();
		}
		return costAnalysisDao.totalCostReport(year, userCode);
	}

	public List<Object[]> dailyaverageReport(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.getCurrentYear();
		}
		return costAnalysisDao.dailyaverageReport(year, userCode);
	}

	public List<Object[]> grossProfitReport(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.getCurrentYear();
		}
		return costAnalysisDao.grossProfitReport(year, userCode);
	}

	public int closed(String projectCode) {
		CostAnalysis costAnalysis = costAnalysisDao.closed(projectCode);
		if (costAnalysis != null) {
			Integer status = costAnalysis.getStatus();
			if (status != null) {
				if (status == 1) {
					costAnalysisDao.changeStatus(costAnalysis.getCostAnalysisId(), ExamineStatus.CLOSED.getStatus());
				}
				return status;
			}
		}
		return -1;
	}
}