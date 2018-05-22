package com.etiansoft.ole.dao;

import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.OtherPFProjectFee;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class OtherPFProjectFeeDao extends HibernateDao<OtherPFProjectFee> {

	public void deleteByFeeId(final Integer projectFeeId) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from " + OtherPFProjectFee.class.getCanonicalName() + " where pfProjectFee.projectFeeId=?").setInteger(0, projectFeeId).executeUpdate();
				return null;
			}
		});
	}
}