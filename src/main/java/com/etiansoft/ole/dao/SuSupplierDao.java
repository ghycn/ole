package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class SuSupplierDao extends HibernateDao<SuSupplier> {

	public void changeStatus(final Integer supplierCode, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update SuSupplier set status=? where supplierCode=?").setInteger(0, status).setInteger(1, supplierCode).executeUpdate();
				return null;
			}
		});
	}

	public List<SuSupplier> findByStatus(final Integer status) {
		return hibernateTemplate.execute(new HibernateCallback<List<SuSupplier>>() {
			@Override
			public List<SuSupplier> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SuSupplier where status=?").setInteger(0, status).list();
			}
		});
	}

	public List<SuSupplier> findByRemove() {
		return hibernateTemplate.execute(new HibernateCallback<List<SuSupplier>>() {

			@Override
			public List<SuSupplier> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SuSupplier where remove=1").list();
			}
		});
	}
}
