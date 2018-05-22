package com.etiansoft.ole.dao;

import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.BadAccount;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class BadAccountDao extends HibernateDao<BadAccount> {

	public void changeStatus(final Integer badId, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update BadAccount set status=? where badId=?").setInteger(0, status).setInteger(1, badId).executeUpdate();
				return null;
			}
		});
	}
}
