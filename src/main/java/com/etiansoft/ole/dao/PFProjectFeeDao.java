package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class PFProjectFeeDao extends HibernateDao<PFProjectFee> {

	public void changeStatus(final Integer projectFeeId, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update PFProjectFee set status=? where projectFeeId=?").setInteger(0, status).setInteger(1, projectFeeId).executeUpdate();
				return null;
			}
		});
	}

	public List<PFProjectFee> findByProjectCode(final String projectCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<PFProjectFee>>() {
			@Override
			public List<PFProjectFee> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from PFProjectFee where prProject.projectCode=?").setString(0, projectCode).list();
			}
		});
	}
	public List<PFProjectFee> findByQuotationId(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<List<PFProjectFee>>() {
			@Override
			public List<PFProjectFee> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from PFProjectFee where quotation.quotationId=?").setInteger(0, quotationId).list();
			}
		});
	}
	public List<PFProjectFee> findByQuotationId(final Integer quotationId, final String[] types) {
		return hibernateTemplate.execute(new HibernateCallback<List<PFProjectFee>>() {
			@Override
			public List<PFProjectFee> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from PFProjectFee where quotation.quotationId=? and type not in (:types) and status=1").setInteger(0, quotationId).setParameterList("types", types).list();
			}
		});
	}

	public Double findMiscelByQuotation(final Integer quotationId, final String[] types) {
		return hibernateTemplate.execute(new HibernateCallback<Double>() {
			@Override
			public Double doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select sum(amount) from PFProjectFee where quotation.quotationId=? and type in (:types) and status=1";
				Double query = (Double) session.createQuery(hql).setInteger(0, quotationId).setParameterList("types", types).uniqueResult();
				if (query == null) {
					return (double) 0;
				}
				return query.doubleValue();
			}
		});
	}
	
	public List<String> findQuotationPfPorjects(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {
	
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select distinct qp.PROJECT_FEE_ID                                 " +
							 "from quotation_list_projectfees qp                                      " +
							 "where                                                            " +
							 "qp.QUOTATION_LIST_ID = :quotationId                                             ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("quotationId", quotationId);
				return query.list();
			}
		});
	}
	
	public List<String> findQuotationItems(final String type) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {
	
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select q.ITEM from quotation_list q where q.QUOTATION_LIST_ID in(select qt.QUOTATION_LIST_ID from quotation_list_projectfees qt where qt.PROJECT_FEE_ID=:quotationId)";

				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("quotationId", type);
				return query.list();
			}
		});
	}

	public List<String> findTypeNames(final Integer id) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {
	
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select name from other_pf_project_fee_type where id="+id;

				SQLQuery query = session.createSQLQuery(sql);
				return query.list();
			}
		});
	}
}