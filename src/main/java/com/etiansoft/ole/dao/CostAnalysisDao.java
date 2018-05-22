package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class CostAnalysisDao extends HibernateDao<CostAnalysis> {

	public CostAnalysis findbyProjectCode(final String projectCode) {
		return hibernateTemplate.execute(new HibernateCallback<CostAnalysis>() {
			@Override
			public CostAnalysis doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from CostAnalysis where project.projectCode=?";
				return (CostAnalysis) session.createQuery(hql).setString(0, projectCode).uniqueResult();
			}
		});
	}

	public void merge(CostAnalysis costAnalysis) {
		hibernateTemplate.merge(costAnalysis);
	}

	public void changeStatus(final Integer costAnalysisId, final Integer status) {
		hibernateTemplate.execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update CostAnalysis set status=? where costAnalysisId=?").setInteger(0, status).setInteger(1, costAnalysisId).executeUpdate();
				return null;
			}
		});
	}

	public List<Object[]> totalCostReport(final String year, final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				if (StringUtils.isNotEmpty(userCode) && !StringUtils.equals("undefined", userCode)) {
					String hql = "select month(p.executionTime),sum(cost.totalCost) " + "from CostAnalysis cost,PrProject p " + "where " + "p.projectCode = cost.project.projectCode " + "and cost.status=4 and p.status!=3 " + "AND p.openStaff.userCode=? " + "AND DATE_FORMAT(p.executionTime,'%Y')=? " + "GROUP BY month(p.executionTime)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				String hql = "select month(p.executionTime),sum(cost.totalCost) from CostAnalysis cost,PrProject p where p.projectCode = cost.project.projectCode and cost.status=4 and p.status!=3   AND DATE_FORMAT(p.executionTime,'%Y')=? GROUP BY month(p.executionTime)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public List<Object[]> dailyaverageReport(final String year, final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				if (StringUtils.isNotEmpty(userCode) && !StringUtils.equals("undefined", userCode)) {
					String hql = "select month(p.executionTime),(sum(cost.totalCost)/30) from CostAnalysis cost,PrProject p where p.projectCode = cost.project.projectCode and cost.status=4 and p.status!=3 AND p.openStaff.userCode=? AND DATE_FORMAT(p.executionTime,'%Y')=? GROUP BY month(p.executionTime)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				String hql = "select month(p.executionTime),(sum(cost.totalCost)/30) from CostAnalysis cost,PrProject p where p.projectCode = cost.project.projectCode and cost.status=4 and p.status!=3 AND DATE_FORMAT(p.executionTime,'%Y')=? GROUP BY month(p.executionTime)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public List<Object[]> grossProfitReport(final String year, final String userCode) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				if (StringUtils.isNotEmpty(userCode) && !StringUtils.equals("undefined", userCode)) {
					String hql = "select month(p.executionTime),CONCAT(ROUND(sum(cost.grossProfit)/sum(cost.taxQuotation)*100,2)) from CostAnalysis cost,PrProject p where p.projectCode = cost.project.projectCode and cost.status=4  and p.status!=3  AND p.openStaff.userCode=? AND DATE_FORMAT(p.executionTime,'%Y')=? GROUP BY month(p.executionTime)";
					return session.createQuery(hql).setString(0, userCode).setString(1, year).list();
				}
				String hql = "select month(p.executionTime),CONCAT(ROUND(sum(cost.grossProfit)/sum(cost.taxQuotation)*100,2)) from CostAnalysis cost,PrProject p where p.projectCode = cost.project.projectCode and cost.status=4   and p.status!=3  AND DATE_FORMAT(p.executionTime,'%Y')=? GROUP BY month(p.executionTime)";
				return session.createQuery(hql).setString(0, year).list();
			}
		});
	}

	public CostAnalysis closed(final String projectCode) {
		return hibernateTemplate.execute(new HibernateCallback<CostAnalysis>() {
			@Override
			public CostAnalysis doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from CostAnalysis where project.projectCode=?";
				return (CostAnalysis) session.createQuery(hql).setString(0, projectCode).uniqueResult();
			}
		});
	}

	public CostAnalysis findByQuotationId(final Integer quotationId) {
		return hibernateTemplate.execute(new HibernateCallback<CostAnalysis>() {
			@Override
			public CostAnalysis doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from CostAnalysis where quotation.quotationId=?";
				return (CostAnalysis) session.createQuery(hql).setInteger(0, quotationId).uniqueResult();
			}
		});
	}
}