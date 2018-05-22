package com.etiansoft.ole.quotation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.QuotationDao;
import com.etiansoft.ole.dao.QuotationListDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SuSupplierContact;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.vo.QuotationListVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class QuotationListService extends OleHibernateService<QuotationList> {
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired
	private QuotationListDao quotationListDao;
	@Autowired
	private QuotationDao quotationDao;

	public DataTablePage getData(Integer quotationId, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("quotationId", quotationId);
		Pagation pagation = quotationDao.findPage(currentPage, page.getiDisplayLength(), map);
		List<Quotation> list = (List<Quotation>) pagation.getRecords();
		Set<QuotationList> quotationList = list.get(0).getQuotationList();
		List<QuotationList> quoList = new ArrayList<QuotationList>();
		quoList.addAll(quotationList);
		pagation.setRecords(quoList);
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, QuotationListVo.class);
		return dataTablePage;
	}

	public void deleteByCode(Integer quotationListId) {
		List<QuotationList> quotationList = quotationListDao.findByParentId(quotationListId);
		if (quotationList != null && quotationList.size() > 0) {
			for (QuotationList quo : quotationList) {
				quotationListDao.deleteById(quo.getQuotationListId());
			}
		}
		quotationListDao.deleteById(quotationListId);
	}

	public Object findAllByCode(Integer quotationListId) {
		QuotationList quotationItem = quotationListDao.findById(quotationListId);
		return quotationItem;
	}

	public void updateAll(QuotationList quotationItem) {
		quotationListDao.update(quotationItem);
	}

	public void saveItem(QuotationList quotationItem) {
		super.save(quotationItem);
	}

	public List<QuotationListVo> createTree(Set<QuotationList> quotationList) {
		List<QuotationList> list = new ArrayList<QuotationList>();
		list.addAll(quotationList);
		return getRoots(list);
	}

	public List<QuotationListVo> createTree(List<QuotationList> list) {
		return getRoots(list);
	}

	private List<QuotationListVo> getRoots(List<QuotationList> list) {
//		List<QuotationListVo> quotationVoList=new ArrayList<QuotationListVo>();
		List<QuotationListVo> quotationListMap = makeupQuotationListMap(list);
//		List<QuotationListVo> roots = quotationListMap.get(null);
		if (quotationListMap == null) {
			return Collections.emptyList();
		}
//		if(list.size()>0){
//			for (QuotationListVo root : quotationListMap) {
//				if(root.getParent()==null){
//					List<QuotationListVo> childQuotationVoList=new ArrayList<QuotationListVo>();
//					for(QuotationListVo child:quotationListMap){
//						if(child.getQuotationListId()==root.getQuotationListId()||root.getCategory().equals(child.getCategory())){
//							childQuotationVoList.add(child);
////							root.getChildren().add(child);
//						}
//					}
////					.add(childQuotationVoList);
//					root.setChildren(childQuotationVoList);
//					quotationVoList.add(root);
//				}
//	//			buildTree(root, quotationListMap);
//			}
//		}
		return quotationListMap;
	}

	private void buildTree(QuotationListVo parentNode,  List<QuotationListVo> quotationListMap) {
		
//		Integer parentId = parentNode.getQuotationListId();
//		List<QuotationListVo> children = quotationListMap.get(parentId);
//		parentNode.setChildren(children);

//		if (children == null) {
//			return;
//		}
//		for (QuotationListVo child : children) {
//			buildTree(child, quotationListMap);
//		}
	}

	private List<QuotationListVo> makeupQuotationListMap(List<QuotationList> quotationList) {
//		Map<Integer, List<QuotationListVo>> quotataionListMap = new HashMap<Integer, List<QuotationListVo>>();
		List<QuotationListVo> quotationListNodes =new ArrayList<QuotationListVo>();
		for (QuotationList quo : quotationList) {
//			Integer parentId = quo.getParent();
//			List<QuotationListVo> quotationListNodes = quotataionListMap.get(parentId);
//			if (quotationListNodes == null) {
//				quotationListNodes = new ArrayList<QuotationListVo>();
//				quotataionListMap.put(parentId, quotationListNodes);
//			}

			QuotationListVo quotationListVo = new QuotationListVo();
			quotationListVo.setQuotationListId(quo.getQuotationListId());
			quotationListVo.setCategory(quo.getCategory());
			quotationListVo.setItem(quo.getItem());
			quotationListVo.setSize(quo.getSize());
			quotationListVo.setQuantity(quo.getQuantity());
			quotationListVo.setUnitPrice(quo.getUnitPrice());
			quotationListVo.setSubTotal(quo.getSubTotal());
			quotationListVo.setSpec(quo.getSpec());
			quotationListVo.setNote(quo.getNote());
			quotationListVo.setParent(quo.getParent());
			SuSupplier supplier = quo.getSupplier();
			if (supplier != null) {
				quotationListVo.setSupplierName(supplier.getName());
				quotationListVo.setSupplierCode(supplier.getSupplierCode());
				List<SuSupplierContact> contacts = supplier.getContacts();
				if (contacts != null && contacts.size() > 0) {
					quotationListVo.setContactName(contacts.get(0).getName());
					quotationListVo.setContactMobile(contacts.get(0).getMobile());
				} else {
					quotationListVo.setContactName("无");
					quotationListVo.setContactMobile("无");
				}
			}
			Set<PFProjectFee> projectFees = quo.getProjectFees();
			if (projectFees!=null && projectFees.size() > 0) {
				Iterator<PFProjectFee> iterator = projectFees.iterator();
				BigDecimal amountDecimal = new BigDecimal(0);
				while (iterator.hasNext()) {
					PFProjectFee projectFee = iterator.next();
					BigDecimal addDecimal = new BigDecimal(projectFee.getAmount());
					amountDecimal = amountDecimal.add(addDecimal);
				}
				quotationListVo.setAmount(amountDecimal.doubleValue());
			}
			quotationListNodes.add(quotationListVo);
		}
		return quotationListNodes;
	}

	public String  queryQuotationListByQuotationId(Integer quotationId, String str) {
		String sql=" select  * from quotation_list where QUOTATION_LIST_ID =(select  min(QUOTATION_LIST_ID) from  quotation_list   where QUOTATION_ID = "+quotationId+" and CATEGORY ='"+str+"') ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		String category=null;
		if(list!=null&&list.size()>0){
			category = (String)list.get(0).get("CATEGORY");
		}
//		if(map!=null){
//			category = (String)map.get("category");
//		}
		return category;
	}
}