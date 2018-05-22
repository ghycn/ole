package com.etiansoft.ole.supplier.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.dao.SuSupplierContactDao;
import com.etiansoft.ole.dao.SuSupplierDao;
import com.etiansoft.ole.dao.SuSupplierTypeDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SuSupplierContact;
import com.etiansoft.ole.supplier.query.SupplierQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.CustomerVo;
import com.etiansoft.ole.vo.SuSupplierTypeVo;
import com.etiansoft.ole.vo.SuSupplierVo;
import com.etiansoft.ole.vo.YearInfoVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class SupplierService extends OleHibernateService<SuSupplier> {

	@Autowired
	private SuSupplierDao supplierDao;
	@Autowired
	private SuSupplierContactDao supplierContactDao;
	@Autowired
	private SuSupplierTypeDao supplierTypeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void saveSupplier(SuSupplier supplier) {
		supplier.setStatus(ExamineStatus.APPROVING.getStatus());
		supplier.setRemove(true);
		supplierDao.save(supplier);
		if (supplier.getContacts() != null) {
			for (SuSupplierContact contact : supplier.getContacts()) {
				contact.setSupplierCode(supplier);
				contact.setCreateTime(new Date());
				supplierContactDao.save(contact);
			}
		}
	}

	public void deleteByCode(Integer supplierCode) {
		supplierDao.deleteById(supplierCode);
		supplierContactDao.deleteBysupplierCode(supplierCode);
	}

	@Transactional(readOnly = true)
	public DataTablePage getData(SupplierQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = supplierDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, SuSupplierVo.class);
		return dataTablePage;
	}

	private String makeHql(SupplierQuery query) {
		String hql = "from SuSupplier cm where 1=1 and remove=1";
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and cm.name like :name";
		}
		String supplierCode = query.getSupplierCode();
		try {
			Integer.parseInt(supplierCode);
			hql += " and cm.supplierCode = :supplierCode";
		} catch (Exception e) {
		}
		Integer status = query.getStatus();
		if (status != null) {
			hql += " and cm.status=:status";
		}
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(SupplierQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		String supplierCode = query.getSupplierCode();
		if (StringUtils.isNotEmpty(supplierCode)) {
			params.put("supplierCode", "%" + supplierCode + "%");
		}
		Integer status = query.getStatus();
		if (status != null) {
			params.put("status", status);
		}
		return params;
	}

	@Transactional(readOnly = true)
	public List<SuSupplierContact> findContactBySupplierCode(Integer supplierCode) {
		List<SuSupplierContact> contacts = supplierContactDao.findBySupplierCode(supplierCode);
		return contacts;
	}

	public SuSupplier findAllByCode(Integer supplierCode) {
		SuSupplier supplier = supplierDao.findById(supplierCode);
		supplier.setContacts(supplierContactDao.findBySupplierCode(supplierCode));
		return supplier;
	}

	public void updateAll(SuSupplier supplier) {
		supplier.setStatus(ExamineStatus.APPROVING.getStatus());
		supplier.setRemove(true);
		supplierDao.update(supplier);
		if (supplier.getContacts() != null) {
			for (SuSupplierContact contact : supplier.getContacts()) {
				contact.setSupplierCode(supplier);
				contact.setCreateTime(new Date());
				if (contact.getSupplierContactId() != null) {
					supplierContactDao.update(contact);
				} else {
					supplierContactDao.save(contact);
				}
			}
		}
	}

	public void updateDelete(Integer supplierCode) {
		SuSupplier supplier = supplierDao.findById(supplierCode);
		supplier.setStatus(ExamineStatus.REJECTED.getStatus());
		supplier.setRemove(false);
		super.update(supplier);
	}

	public void changeStatus(Integer supplierCode, Integer status) {
		supplierDao.changeStatus(supplierCode, status);
	}

	public List<SuSupplier> findbyStatus(Integer status) {
		return supplierDao.findByStatus(status);
	}

	public List<SuSupplierVo> findByRemove() {
		List<SuSupplier> suppliers = supplierDao.findByRemove();
		return VoTool.convert(suppliers, SuSupplierVo.class);
	}

	public Double queryMoney(Integer supplierCode, String yearValue) {
//		Integer moeny = supplierContactDao.getMoney(supplierCode,yearValue);
		String sql = " select sum(amount) amount from  su_supplier a " +
					 " inner join  pf_project_fee b  on  a.supplier_code = b.supplier_code  " +
					 " where  b.status=1 and  a.supplier_code="+supplierCode+"   ";
		if(StringUtils.isNotEmpty(yearValue)){
			sql+= " and  left(create_time,4)='"+yearValue+"' ";
		}
		List list = jdbcTemplate.queryForList(sql);
		Double money = null;
		if(list!=null){
			Map map =  (Map) list.get(0);
			money = (Double) map.get("amount");
		}
		return money==null?0:money;
	}
	@SuppressWarnings("unchecked")
	public List<YearInfoVo> queryYearInfo() {
		String sql =" select year_id,year_name,year_value from  year_info  "; 
		return this.jdbcTemplate.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				YearInfoVo  vo = new YearInfoVo();
				
				vo.setYearId(rs.getInt("year_id"));
				vo.setYearName(rs.getString("year_name"));
				vo.setYearValue(rs.getString("year_value"));
				return vo;
			}
		});
		
	}
	@SuppressWarnings("unchecked")
	public List<SuSupplierVo> querySupplierList() {
		String sql = " select supplier_code,name from su_supplier where remove = 1 ";
		return this.jdbcTemplate.query(sql,new RowMapper(){

			public Object mapRow(ResultSet rs, int paramInt)
					throws SQLException {
				SuSupplierVo  suSupplier = new SuSupplierVo();
				suSupplier.setSupplierCode(rs.getInt("supplier_code"));
				suSupplier.setName(rs.getString("name"));
				return suSupplier;
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<SuSupplierTypeVo> querySupplierTypeList() {
		String  sql = " select name, type_id from susupplier_type where remove = 1 ";
		return this.jdbcTemplate.query(sql,new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SuSupplierTypeVo suSupplierType = new SuSupplierTypeVo();
				suSupplierType.setName(rs.getString("name"));
				suSupplierType.setTypeId(rs.getInt("type_id"));
				return suSupplierType;
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<SuSupplierVo> querySuSupplierListByType(String type) {
		String sql = "";
		if("".equals(type)){
			 sql = " select name,supplier_code from su_supplier where remove = 1  ";

		}else{
			
			 sql = " select name,supplier_code from su_supplier where remove = 1 and type = '"+type+"' ";
		}
		return this.jdbcTemplate.query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SuSupplierVo suSupplier = new SuSupplierVo();
				suSupplier.setSupplierCode(rs.getInt("supplier_code"));
				suSupplier.setName(rs.getString("name"));
				return suSupplier;
			}
		});		
	}
}
