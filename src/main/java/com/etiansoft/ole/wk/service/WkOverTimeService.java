package com.etiansoft.ole.wk.service;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.WkOvertimeDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.WkOvertime;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.vo.WkOvertimeVo;
import com.etiansoft.ole.wk.query.WkOverTimeQuery;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class WkOverTimeService extends OleHibernateService<WkOvertime> {

	@Autowired
	private WkOvertimeDao wkOvertimeDao;

	public DataTablePage getData(WkOverTimeQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = wkOvertimeDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, WkOvertimeVo.class);
		return dataTablePage;
	}

	private String makeHql(WkOverTimeQuery query) {
		String hql = "from WkOvertime where 1=1";
		String applicant = query.getApplicant();
		if (StringUtils.isNotEmpty(applicant)) {
			hql += " and applicant.userCode=:applicant";
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

	private LinkedHashMap<String, Object> makeQuery(WkOverTimeQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String applicant = query.getApplicant();
		if (StringUtils.isNotEmpty(applicant)) {
			params.put("applicant", applicant);
		}
		String status = query.getStatus();
		try {
			Integer iStatus = Integer.parseInt(status);
			params.put("status", iStatus);
		} catch (Exception e) {
		}
		return params;
	}

	public void save(String start, String end, WkOvertime wKOvertime) {
		wKOvertime.setStartDate(DateUtil.parse(start, "yyyy-MM-dd"));
		wKOvertime.setEndDate(DateUtil.parse(end, "yyyy-MM-dd"));
		wKOvertime.setStatus(ExamineStatus.APPROVING.getStatus());
		super.save(wKOvertime);
	}

	public void update(String start, String end, WkOvertime wKOvertime) {

		wKOvertime.setStartDate(DateUtil.parse(start, "yyyy-MM-dd"));
		wKOvertime.setEndDate(DateUtil.parse(end, "yyyy-MM-dd"));
		wKOvertime.setStatus(ExamineStatus.APPROVING.getStatus());
		super.update(wKOvertime);
	}

	public void changeStatus(Integer overTimeId, int status) {
		wkOvertimeDao.changeStatus(overTimeId, status);
	}

	public List<Object[]> report(String year, String userCode) {
		if (StringUtils.isEmpty(year)) {
			Calendar calendar = Calendar.getInstance();
			year = String.valueOf(calendar.get(Calendar.YEAR));
		}
		return wkOvertimeDao.report(year, userCode);
	}

	public int countDays(String userCode) {
		return wkOvertimeDao.countDays(userCode);
	}
}
