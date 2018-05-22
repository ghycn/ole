package com.etiansoft.ole.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class WkLeaveDao extends HibernateDao<WkLeave> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void changeStatus(final Integer leaveId, final int status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update WkLeave set status=? where leaveId=?").setInteger(0, status).setInteger(1, leaveId).executeUpdate();
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
					hql = "select month(startDate),SUM(duration) from WkLeave t where applicant.userCode=? AND status=1 AND DATE_FORMAT(startDate,'%Y')=? GROUP BY month(startDate)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				hql = "select month(startDate),SUM(duration) from WkLeave t where status=1 AND DATE_FORMAT(startDate,'%Y')=? GROUP BY month(startDate)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public Double countDays(final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<Double>() {
			@Override
			public Double doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("select sum(duration) from WkLeave where applicant.userCode=? and status=1");
				query.setString(0, userCode);
				Double result = (Double) query.uniqueResult();
				if (result == null) {
					return 0.0;
				}
				return result.doubleValue();
			}
		});
	}

	public List<WkLeave> findbyUserCode(final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<WkLeave>>() {
			@Override
			public List<WkLeave> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from WkLeave where applicant.userCode=? and status=1").setString(0, userCode).list();
			}
		});
	}

	public List<WkLeave> queryVacateDay() {
	    String sql = " SELECT a.USER_CODE notes, sum(b.DURATION)duration FROM  sys_user a "+
	    		     " INNER JOIN  wk_leave b ON  a.USER_CODE=b.APPLICANT GROUP BY a.USER_CODE ";
	    List<WkLeave> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(WkLeave.class));
		return list;
	}

	public void clearWkLeaveRecord() {
		// TODO Auto-generated method stub
		String sql = "UPDATE WK_LEAVE SET DURATION = 0";
		this.jdbcTemplate.update(sql);
	}
}
