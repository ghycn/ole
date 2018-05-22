package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.CustomerContact;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class CustomerContactDao extends HibernateDao<CustomerContact> {
	public void deleteBycustomerCode(final String customerCode) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from CustomerContact where customerCode=?").setString(0, customerCode).executeUpdate();
				return null;
			}
		});
	}

	public List<CustomerContact> findByCustomerCode(final String customerCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<CustomerContact>>() {
			@Override
			public List<CustomerContact> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from CustomerContact where customerCode=?").setString(0, customerCode);
				return query.list();
			}
		});
	}

	public CustomerContact findContactByCustomerCode(String customerCode) {
		CustomerContact customerContact = null;
		List<CustomerContact> list = hibernateTemplate.find("from CustomerContact where customerContactId="+customerCode+" ");
		if(list!=null && list.size()>0){
			customerContact=list.get(0);
		}
		return customerContact;
	}

}
