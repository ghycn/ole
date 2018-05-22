package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.Customer;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class CustomerDao extends HibernateDao<Customer> {

	public String getMaxCode() {
		return hibernateTemplate.execute(new HibernateCallback<String>() {
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				String code = (String) session.createQuery("SELECT MAX(customerCode) FROM Customer").uniqueResult();
				return code;
			}
		});
	}

	public void changeStatus(final String customerCode, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update Customer set status=? where customerCode=?").setInteger(0, status).setString(1, customerCode).executeUpdate();
				return null;
			}
		});
	}

	public List<Customer> findBydel() {
		return hibernateTemplate.execute(new HibernateCallback<List<Customer>>() {

			@Override
			public List<Customer> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from Customer where remove=1").list();
			}
		});
	}

	public List<Customer> findbyStatus(final Integer status) {
		return hibernateTemplate.execute(new HibernateCallback<List<Customer>>() {
			@Override
			public List<Customer> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from Customer where status=?").setInteger(0, status).list();
			}
		});
	}
}
