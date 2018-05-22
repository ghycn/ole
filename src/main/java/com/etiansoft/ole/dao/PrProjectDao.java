package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.PrProject;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class PrProjectDao extends HibernateDao<PrProject> {

	public void changeStatus(final String projectCode, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update PrProject set status=? where projectCode=?").setInteger(0, status).setString(1, projectCode).executeUpdate();
				return null;
			}
		});
	}

	public List<PrProject> findByStatus(final Integer status) {
		return hibernateTemplate.execute(new HibernateCallback<List<PrProject>>() {
			@Override
			public List<PrProject> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from PrProject where status=?").setInteger(0, status).list();
			}
		});
	}

	public List<Object[]> report(final String year, final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql;
				if (StringUtils.isNotEmpty(userCode) && !StringUtils.equals("undefined", userCode)) {
					hql = "select month(openTime),COUNT(*) from PrProject t where status=1 AND openStaff.userCode=? AND DATE_FORMAT(openTime,'%Y')=? GROUP BY month(openTime)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				hql = "select month(openTime),COUNT(*) from PrProject t where status=1 AND DATE_FORMAT(openTime,'%Y')=? GROUP BY month(openTime)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public void changeInvoiceAmount(final String projectCode) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update PrProject set invoiceAmount=(select sum(taxTotal) from Quotation where prProject.projectCode=?) where projectCode=?").setString(0, projectCode).setString(1, projectCode).executeUpdate();
				return null;
			}
		});
	}
}
