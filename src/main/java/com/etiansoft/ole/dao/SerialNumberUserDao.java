package com.etiansoft.ole.dao;

import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SerialNumberUser;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class SerialNumberUserDao extends HibernateDao<SerialNumberUser> {
	public int findSerialNumber() {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer query = (Integer) session.createQuery("select max(id) from SerialNumberUser").uniqueResult();
				return query.intValue();
			}
		});
	}

	public void updateId(final int serialNumber) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update SerialNumberUser set id=?").setInteger(0, serialNumber + 1).executeUpdate();
				return null;
			}
		});
	}
}
