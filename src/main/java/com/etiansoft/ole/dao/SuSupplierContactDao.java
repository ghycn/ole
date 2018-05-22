package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SuSupplierContact;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class SuSupplierContactDao extends HibernateDao<SuSupplierContact> {
	public void deleteBysupplierCode(final Integer supplierCode) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from SuSupplierContact where supplierCode=?").setInteger(0, supplierCode).executeUpdate();
				return null;
			}
		});
	}

	public void deleteBysupplierContactId(final Integer supplierContactId) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from SuSupplierContact where supplierContactId=?").setInteger(0, supplierContactId).executeUpdate();
				return null;
			}
		});
	}

	public List<SuSupplierContact> findBySupplierCode(final Integer supplierCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<SuSupplierContact>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<SuSupplierContact> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from SuSupplierContact where supplierCode=?").setInteger(0, supplierCode);
				return query.list();
			}
		});
	}

	public Integer getMoney(final Integer supplierCode, final String yearValue) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = " select sum(amount) from  su_supplier a " +
							 " inner join  pf_project_fee b  on  a.supplier_code = b.supplier_code  " +
							 " where  b.status=1 and  a.supplier_code=:supplierCode  and  left(create_time,4)=:yearValue  ";
				Query query = session.createQuery(sql);
				query.setParameter("supplierCode", supplierCode);
				query.setParameter("yearValue", yearValue);
				List list = query.list();
				Integer money = null;
				if(list!=null){
					money = (Integer) list.get(0);
				}
				return money==null?0:money;
			}
		});
	}
}