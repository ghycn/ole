package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.CustomerType;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class CustomerTypeDao extends HibernateDao<CustomerType> {

	public List<CustomerType> findByRemove() {
		return hibernateTemplate.execute(new HibernateCallback<List<CustomerType>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<CustomerType> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from CustomerType where remove=1").list();
			}
		});
	}
}
