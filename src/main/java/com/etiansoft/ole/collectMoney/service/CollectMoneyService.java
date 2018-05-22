package com.etiansoft.ole.collectMoney.service;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.collectMoney.query.CollectMoneyQuery;
import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.dao.ActivityDao;
import com.etiansoft.ole.dao.CollectMoneyDao;
import com.etiansoft.ole.dao.PrProjectDao;
import com.etiansoft.ole.dao.QuotationDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.CollectMoney;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.vo.CollectMoneyVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class CollectMoneyService extends OleHibernateService<CollectMoney> {

	@Autowired
	private CollectMoneyDao collectMoneyDao;
	@Autowired
	private PrProjectDao projectDao;
	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private ActivityDao activityDao;

	public DataTablePage getData(CollectMoneyQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = collectMoneyDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		return ConvertUtil.generatorTablePage(pagation, page, CollectMoneyVo.class);
	}

	private String makeHql(CollectMoneyQuery query) {
		String hql = "from CollectMoney cm where 1=1";
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		if (!user.isBoss()) {
			hql += " and cm.applicant.userCode='" + user.getUserCode() + "'";
		}
		String activityCode = query.getActivityCode();
		if (StringUtils.isNotEmpty(activityCode)) {
			hql += " and cm.quotaion.prProject.name like :activityCode";
		}
		hql+=" order by date desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(CollectMoneyQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String activityCode = query.getActivityCode();
		if (StringUtils.isNotEmpty(activityCode)) {
			params.put("activityCode", "%" + activityCode + "%");
		}
		return params;
	}

	public void save(CollectMoney collectMoney, String date, Integer quotationId) {
		SysUser user = (SysUser) ServletContextProvider.getSession().getAttribute(Constants.LOGIN_USER);
		Quotation quotation = quotationDao.findById(quotationId);
		collectMoney.setQuotaion(quotation);
		collectMoney.setDate(DateUtil.parse(date, "yyyy-MM-dd"));
		collectMoney.setApplicant(user);
		collectMoneyDao.save(collectMoney);
	}

	public void update(CollectMoney collectMoney, String date, Integer quotationId) {
		CollectMoney dbCollectMoney = findById(collectMoney.getCollectId());
		Quotation quotation = quotationDao.findById(quotationId);
		dbCollectMoney.setQuotaion(quotation);
		dbCollectMoney.setInvoice(collectMoney.getInvoice());
		dbCollectMoney.setAmount(collectMoney.getAmount());
		dbCollectMoney.setDate(collectMoney.getDate());
		dbCollectMoney.setToAccount(collectMoney.getToAccount());
		dbCollectMoney.setNotes(collectMoney.getNotes());
		dbCollectMoney.setDate(DateUtil.parse(date, "yyyy-MM-dd"));
		super.update(dbCollectMoney);
	}
}