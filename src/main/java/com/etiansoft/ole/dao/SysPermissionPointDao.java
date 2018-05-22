/**
 * @项目名称: ole
 * @文件名称: SysPermissionPointDao.java
 * @author tianlihu
 * @Date: 2015-5-9
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SysPermissionPoint;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class SysPermissionPointDao extends HibernateDao<SysPermissionPoint> {

	public List<String> findUserPermissionPoints(final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select distinct pp.value                                                  " +
							 "from sys_permission_point pp                                              " +
							 "left join sys_role_permission rp on rp.PERMISSION_ID = pp.PERMISSION_ID   " +
							 "left join sys_user_role ur on ur.role_id = rp.role_id                     " +
							 "where                                                                     " +
							 "ur.user_code = :userCode                                                  ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("userCode", userCode);
				return query.list();
			}
		});
	}

	public List<String> findRolePermissions(final Integer roleId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select distinct rp.permission_id                                 " +
							 "from sys_role_permission rp                                      " +
							 "where                                                            " +
							 "rp.role_id = :roleId                                             ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("roleId", roleId);
				return query.list();
			}
		});
	}

	public List<String> findMenuName(final Integer roleId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				 String sql=" select value from sys_permission_point" +
					 		" s where  s.PERMISSION_ID in " +
					 		" (select a.PERMISSION_ID from sys_role_permission a inner join sys_permission b ON a.PERMISSION_ID = b.PERMISSION_ID where  a.ROLE_ID=:roleId) " +
					 		" and s.value not like '/%' and length(s.PERMISSION_ID)=5 ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("roleId", roleId);
				return query.list();
			}
		});
	}

	public List<String> findMenuNames(final Integer roleId, final Integer menuId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				 String sql=" select value from sys_permission_point" +
					 		" s where  s.PERMISSION_ID in " +
					 		" (select a.PERMISSION_ID from sys_role_permission a inner join sys_permission b ON a.PERMISSION_ID = b.PERMISSION_ID where  a.ROLE_ID=:roleId) " +
					 		" and s.value not like '/%' and length(s.PERMISSION_ID)=5  and substring(s.PERMISSION_ID,2,2)=:menuId  ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("roleId", roleId);
				query.setParameter("menuId", menuId);
				return query.list();
			}
		});
	}
}
