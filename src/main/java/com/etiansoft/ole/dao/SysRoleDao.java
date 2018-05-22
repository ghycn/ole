package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SysRole;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class SysRoleDao extends HibernateDao<SysRole> {

	public List<SysRole> findByRemove() {
		return hibernateTemplate.execute(new HibernateCallback<List<SysRole>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<SysRole> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SysRole").list();
			}
		});
	}
}
