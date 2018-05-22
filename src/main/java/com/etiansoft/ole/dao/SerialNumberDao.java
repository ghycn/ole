package com.etiansoft.ole.dao;

import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SerialNumber;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class SerialNumberDao extends HibernateDao<SerialNumber> {
	public int findSerialNumber() {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer query = (Integer) session.createQuery("select max(id) from SerialNumber").uniqueResult();
				return query.intValue();
			}
		});
	}

	public void updateId(final int serialNumber) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update SerialNumber set id=?").setInteger(0, serialNumber + 1).executeUpdate();
				return null;
			}
		});
	}
}
