package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SysUser;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class SysUserDao extends HibernateDao<SysUser> {

	@Override
	public List<SysUser> findAll() {
		return hibernateTemplate.find("from SysUser where userCode != 'admin'");
	}

	public void updatePassword(final SysUser user, final String password) {
		hibernateTemplate.execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update SysUser set password=? where userCode=?").setString(0, password).setString(1, user.getUserCode()).executeUpdate();
				return null;
			}
		});
	}

	public List<SysUser> findbyStatus(final Integer status) {
		return hibernateTemplate.execute(new HibernateCallback<List<SysUser>>() {
			@Override
			public List<SysUser> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SysUser where status=?").setInteger(0, status).list();
			}
		});
	}

	public List<SysUser> findbyState() {
		return hibernateTemplate.execute(new HibernateCallback<List<SysUser>>() {
			@Override
			public List<SysUser> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SysUser where state=1 and status=1").list();
			}
		});
	}

	public List<SysUser> findBydel() {
		return hibernateTemplate.execute(new HibernateCallback<List<SysUser>>() {

			@Override
			public List<SysUser> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from SysUser where state=1 and userCode!='admin'").list();
			}
		});
	}
}
