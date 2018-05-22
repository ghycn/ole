package com.etiansoft.ole.wk.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.SysUserDao;
import com.etiansoft.ole.dao.WkLeaveDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.vo.WkLeaveVo;
import com.etiansoft.ole.wk.query.WkLeaveQuery;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class WkLeaveService extends OleHibernateService<WkLeave> {

	@Autowired
	private WkLeaveDao wkLeaveDao;
	@Autowired
	private SysUserDao userDao;

	public DataTablePage getData(WkLeaveQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = wkLeaveDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, WkLeaveVo.class);
		List<WkLeaveVo> datas = (List<WkLeaveVo>) dataTablePage.getAaData();
		for (WkLeaveVo wkLeave : datas) {
			String userCode = wkLeave.getApplicantCode();
			SysUser user = userDao.findById(userCode);
			Double leaveDays = wkLeaveDao.countDays(userCode);
			Integer vacationDays = user.getVacationDays()==null?0:user.getVacationDays();
			wkLeave.setSurplusLeaveDays(vacationDays - leaveDays);
		}
		return dataTablePage;
	}

	private String makeHql(WkLeaveQuery query) {
		String hql = "from WkLeave where 1=1";
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		 Set<SysRole> roleSet = user.getRoles();
		 Integer roleId=null;
		 for(SysRole role:roleSet){
			 roleId=role.getRoleId();
			 if(roleId==6){
				 break;
			 }
		 }
		if (user.isBoss()||roleId==6) {
			String applicant = query.getApplicant();
			if (StringUtils.isNotEmpty(applicant)) {
				hql += " and applicant.name like :applicant";
			}
		} else {
			hql += " and applicant.userCode='" + user.getUserCode() + "'";
		}

		String status = query.getStatus();
		try {
			Integer.parseInt(status);
			hql += " and status = :status";
		} catch (Exception e) {
		}
		hql +=" order by endDate desc,startDate desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(WkLeaveQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		 Set<SysRole> roleSet = user.getRoles();
		 Integer roleId=null;
		 for(SysRole role:roleSet){
			 roleId=role.getRoleId();
			 if(roleId==6){
				 break;
			 }
		 }
		if (user.isBoss()||roleId==6) {
			String applicant = query.getApplicant();
			if (StringUtils.isNotEmpty(applicant)) {
				params.put("applicant", "%" + applicant + "%");
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

	public void save(WkLeave wkLeave, String start, String end) {
		wkLeave.setStartDate(DateUtil.parse(start, "yyyy-MM-dd"));
		wkLeave.setEndDate(DateUtil.parse(end, "yyyy-MM-dd"));
		wkLeave.setStatus(ExamineStatus.APPROVING.getStatus());
		super.save(wkLeave);
	}

	public void update(String start, String end, WkLeave wkLeave) {
		wkLeave.setStartDate(DateUtil.parse(start, "yyyy-MM-dd"));
		wkLeave.setEndDate(DateUtil.parse(end, "yyyy-MM-dd"));
		wkLeave.setStatus(ExamineStatus.APPROVING.getStatus());
		super.update(wkLeave);
	}

	public void changeStatus(Integer leaveId, int status) {
		wkLeaveDao.changeStatus(leaveId, status);
	}

	public List<Object[]> report(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.getCurrentYear();
		}
		return wkLeaveDao.report(year, userCode);
	}

	public Double countDays(String userCode) {
		return wkLeaveDao.countDays(userCode);
	}

	public List<WkLeave> queryVacateDay() {
		// TODO Auto-generated method stub
		return wkLeaveDao.queryVacateDay();
	}

	public void clearWkLeaveRecord() {
		// TODO Auto-generated method stub
		wkLeaveDao.clearWkLeaveRecord();
	}
}