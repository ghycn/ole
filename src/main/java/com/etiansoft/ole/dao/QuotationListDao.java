package com.etiansoft.ole.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class QuotationListDao extends HibernateDao<QuotationList> {

	public List<QuotationList> findByParentId(Integer quotationListId) {
		return hibernateTemplate.find("from " + QuotationList.class.getCanonicalName() + " where parent=?", quotationListId);
	}
}
