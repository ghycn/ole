package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.WkOvertime;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class WkOvertimeDao extends HibernateDao<WkOvertime> {

	public void changeStatus(final Integer overTimeId, final int status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update WkOvertime set status=? where overtimeId=?").setInteger(0, status).setInteger(1, overTimeId).executeUpdate();
				return null;
			}
		});
	}

	public List<Object[]> report(final String year, final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "";
				if (StringUtils.isNotEmpty(userCode) && !StringUtils.equals("undefined", userCode)) {
					hql = "select month(startDate),SUM(duration) from WkOvertime t where applicant.userCode=? AND status=1 AND DATE_FORMAT(startDate,'%Y')=? GROUP BY month(startDate)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				hql = "select month(startDate),SUM(duration) from WkOvertime t where status=1 AND DATE_FORMAT(startDate,'%Y')=? GROUP BY month(startDate)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public int countDays(final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Double query = (Double) session.createQuery("select sum(duration) from WkOvertime where applicant.userCode=? and status=1").setString(0, userCode).uniqueResult();
				if (query == null) {
					return 0;
				}
				return query.intValue();
			}
		});
	}

	public List<WkOvertime> findByUserCode(final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<WkOvertime>>() {
			@Override
			public List<WkOvertime> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from WkOvertime where applicant.userCode=? and status=1").setString(0, userCode).list();
			}
		});
	}
}
