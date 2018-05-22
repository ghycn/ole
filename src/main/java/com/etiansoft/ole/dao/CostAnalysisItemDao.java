package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.CostAnalysisItem;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class CostAnalysisItemDao extends HibernateDao<CostAnalysisItem> {

	public List<CostAnalysisItem> findItemByAnalysisId(final Integer costAnalysisId) {
		return hibernateTemplate.execute(new HibernateCallback<List<CostAnalysisItem>>() {
			@Override
			public List<CostAnalysisItem> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from CostAnalysisItem where costAnalysis.costAnalysisId=?";
				return session.createQuery(hql).setInteger(0, costAnalysisId).list();
			}
		});
	}

	public void merge(CostAnalysisItem item) {
		hibernateTemplate.merge(item);
	}
}