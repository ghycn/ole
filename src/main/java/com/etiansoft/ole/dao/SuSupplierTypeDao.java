package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SuSupplierType;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class SuSupplierTypeDao extends HibernateDao<SuSupplierType> {

	public List<SuSupplierType> findByRemove() {
		return hibernateTemplate.execute(new HibernateCallback<List<SuSupplierType>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<SuSupplierType> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SuSupplierType where remove=1").list();
			}
		});
	}
}
