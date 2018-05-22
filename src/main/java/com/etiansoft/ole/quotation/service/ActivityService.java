package com.etiansoft.ole.quotation.service;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.ActivityDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Activity;
import com.etiansoft.ole.quotation.query.ActivityQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.vo.ActivityVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class ActivityService extends OleHibernateService<Activity> {

	@Autowired
	private ActivityDao activityDao;

	public DataTablePage getData(ActivityQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = activityDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, ActivityVo.class);
		return dataTablePage;
	}

	private String makeHql(ActivityQuery query) {
		String hql = "from Activity where 1=1";
		String activityCode = query.getActivityCode();
		if (StringUtils.isNotEmpty(activityCode)) {
			hql += " and activityCode=:activityCode";
		}
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and name like :name";
		}
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(ActivityQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String activityCode = query.getActivityCode();
		if (StringUtils.isNotEmpty(activityCode)) {
			params.put("activityCode", activityCode);
		}
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		return params;
	}

	public void save(Activity activity) {
		if (StringUtils.isNotEmpty(activity.getActivityCode())) {
			Activity tivity = activityDao.findById(activity.getActivityCode());
			if (tivity == null) {
				super.save(activity);
			}
		}
	}

	public void update(Activity activity) {
		super.update(activity);
	}
}