package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class QuotationDao extends HibernateDao<Quotation> {

	public void changeStatus(final Integer quotationId, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update Quotation set status=? where quotationId=?").setInteger(0, status).setInteger(1, quotationId).executeUpdate();
				return null;
			}
		});
	}

	public List<Quotation> findByProject(final String projectCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Quotation>>() {
			@Override
			public List<Quotation> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from Quotation where prProject.projectCode=?").setString(0, projectCode).list();
			}
		});
	}

	public List<Quotation> findbyStatus(final Integer status) {
		return hibernateTemplate.execute(new HibernateCallback<List<Quotation>>() {
			@Override
			public List<Quotation> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from Quotation where status=?").setInteger(0, status).list();
			}
		});
	}

	public void changeTaxTotal(final Integer quotationId) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "update quotation q SET q.TAXTOTAL=(SELECT SUM(ql.SUB_TOTAL) FROM quotation_list ql where ql.QUOTATION_ID=?)  WHERE q.QUOTATION_ID=?";
				session.createSQLQuery(sql).setInteger(0, quotationId).setInteger(1, quotationId).executeUpdate();
				return null;
			}
		});
	}
	
	public Double totalAmount(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<Double>() {
			@Override
			public Double doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select sum(pf.AMOUNT) from pf_project_fee pf where pf.PROJECT_FEE_ID in(select DISTINCT(qlt.PROJECT_FEE_ID) from quotation_list qt ,quotation_list_projectfees qlt WHERE qt.QUOTATION_LIST_ID=qlt.QUOTATION_LIST_ID and qt.QUOTATION_id=?) and (pf.status=0 or pf.status=1)";
				Double query = (Double) session.createSQLQuery(sql).setInteger(0, quotationId).uniqueResult();
				if (query == null) {
					return (double) 0;
				}
				return query.doubleValue();
			}
		});
	}
	public Double otherTotalAmount(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<Double>() {
			@Override
			public Double doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select sum(pf.AMOUNT) from pf_project_fee pf where pf.QUOTATION_ID="+quotationId+" and pf.TYPE_ID is not null and pf.TYPE_NAME is not null and (pf.status=0 or pf.status=1)";
				Double query = (Double) session.createSQLQuery(sql).uniqueResult();
				if (query == null) {
					return (double) 0;
				}
				return query.doubleValue();
			}
		});
	}
	
	public Double taxTotal(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<Double>() {
			@Override
			public Double doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select sum(pf.SUB_TOTAL) from quotation_list pf where qt.QUOTATION_id=?";
				Double query = (Double) session.createSQLQuery(sql).setInteger(0, quotationId).uniqueResult();
				if (query == null) {
					return (double) 0;
				}
				return query.doubleValue();
			}
		});
	}
	public List<String> getQuotationListId(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select DISTINCT p.QUOTATION_LIST_ID from quotation_list_projectfees p where p.uuid in( select qlp.uuid from quotation_list_projectfees qlp  where qlp.QUOTATION_LIST_ID=?)";
//				Double query = (Double) session.createSQLQuery(sql).setInteger(0, quotationId).uniqueResult();
	
				return session.createSQLQuery(sql).setInteger(0, quotationId).list();
			}
		});
	}
	public List<String> getQuotationListIdByPrpjectFeeId(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select DISTINCT p.QUOTATION_LIST_ID from quotation_list_projectfees p where p.uuid in( select qlp.uuid from quotation_list_projectfees qlp  where qlp.PROJECT_FEE_ID=?)";
//				Double query = (Double) session.createSQLQuery(sql).setInteger(0, quotationId).uniqueResult();
	
				return session.createSQLQuery(sql).setInteger(0, quotationId).list();
			}
		});
	}
	
	public List<QuotationList> getQuotationList(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<List<QuotationList>>() {
			@Override
			public List<QuotationList> doInHibernate(Session session) throws HibernateException, SQLException {
//				String sql = "select * from QuotationList q where q.quotationId=?";
//				Double query = (Double) session.createSQLQuery(sql).setInteger(0, quotationId).uniqueResult();
	
				return session.createQuery("from QuotationList q where q.quotationId=?").setInteger(0, quotationId).list();
			}
		});
	}
}