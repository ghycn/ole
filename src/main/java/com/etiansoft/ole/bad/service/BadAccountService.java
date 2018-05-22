package com.etiansoft.ole.bad.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.bad.query.BadAccountQuery;
import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.BadAccountDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.BadAccount;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.vo.BadAccountVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class BadAccountService extends OleHibernateService<BadAccount> {

	@Autowired
	private BadAccountDao badAccountDao;
	@Autowired
	private PrProjectDao prProjectDao;

	public DataTablePage getData(BadAccountQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = badAccountDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, BadAccountVo.class);
		return dataTablePage;
	}

	public void changeStatus(Integer badId, Integer status) {
		badAccountDao.changeStatus(badId, status);
	}

	@SuppressWarnings("unchecked")
	private String makeHql(BadAccountQuery query) {
		HttpSession session = ServletContextProvider.getSession();
		String hql = "from BadAccount cm where 1=1";
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			String people = query.getPeople();
			if (StringUtils.isNotEmpty(people)) {
				hql += " and cm.applicant.name like :applicant";
			}
		} else {
			hql += " and cm.applicant.userCode='" + user.getUserCode() + "'";
		}

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
					hql += " and cm.quotation.prProject.projectCode in (" + projectCodes + ")";
				}
			}
		}*/

		String prProject = query.getPrProject();
		if (StringUtils.isNotEmpty(prProject)) {
			hql += " and cm.projectCode.projectCode like :prProject";
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			hql += " and cm.quotation.prProject.name like :projectName";
		}
		String status = query.getStatus();
		try {
			Integer.parseInt(status);
			hql += " and cm.status = :status";
		} catch (Exception e) {
		}
		hql += "  order by createTime desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(BadAccountQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String prProject = query.getPrProject();
		if (StringUtils.isNotEmpty(prProject)) {
			params.put("prProject", "%" + prProject + "%");
		}
		String projectName = query.getProjectName();
		if (StringUtils.isNotEmpty(projectName)) {
			params.put("projectName", "%" + projectName + "%");
		}

		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (user.isBoss()) {
			String people = query.getPeople();
			if (StringUtils.isNotEmpty(people)) {
				params.put("applicant", "%" + people + "%");
			}
		}

		String status = query.getStatus();
		try {
			Integer iStatus = Integer.parseInt(status);
			params.put("status", iStatus);
		} catch (Exception e) {
		}
		return params;
	}

	public void save(String date, BadAccount badAccount) {
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		badAccount.setApplicant(user);
		badAccount.setCreateTime(DateTool.parseDate(date));
		super.save(badAccount);
	}

	public void update(BadAccount badAccount) {
		BadAccount dbBadAccount = badAccountDao.findById(badAccount.getBadId());
		dbBadAccount.setQuotation(badAccount.getQuotation());
		dbBadAccount.setReason(badAccount.getReason());
		dbBadAccount.setTotal(badAccount.getTotal());
		dbBadAccount.setNote(badAccount.getNote());
		dbBadAccount.setStatus(ExamineStatus.APPROVING.getStatus());
		super.update(dbBadAccount);
	}
}
