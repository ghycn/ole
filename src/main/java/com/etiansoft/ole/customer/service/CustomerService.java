package com.etiansoft.ole.customer.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import com.etiansoft.ole.customer.query.CustomerQuery;
import com.etiansoft.ole.dao.CustomerContactDao;
import com.etiansoft.ole.dao.CustomerDao;
import com.etiansoft.ole.dao.CustomerTypeDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.CustomerContact;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.CustomerTypeVo;
import com.etiansoft.ole.vo.CustomerVo;
import com.etiansoft.ole.vo.YearInfoVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class CustomerService extends OleHibernateService<Customer> {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerContactDao customerContactDao;
	@Autowired
	private CustomerTypeDao customerTypeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void saveCustomer(Customer customer) {
		String code = customerDao.getMaxCode();
		String customerCode = "";
		if (code == null) {
			customerCode = "80001";
		} else {
			customerCode = String.valueOf(Integer.parseInt(code) + 1);
		}
		customer.setCustomerCode(customerCode);
		customer.setRemove(true);
		customer.setStatus(ExamineStatus.APPROVING.getStatus());
		customerDao.save(customer);
		if (customer.getContacts() != null) {
			for (CustomerContact contact : customer.getContacts()) {
				contact.setCustomerCode(customer);
				contact.setCreateTime(new Date());
				customerContactDao.save(contact);
			}
		}
	}

	public void deleteByCode(String customerCode) {
		customerDao.deleteById(customerCode);
		customerContactDao.deleteBycustomerCode(customerCode);
	}

	@Transactional(readOnly = true)
	public DataTablePage getData(CustomerQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = customerDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, CustomerVo.class);
		return dataTablePage;
	}

	private String makeHql(CustomerQuery query) {
		String hql = "from Customer cm where 1=1 and remove=1";
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and cm.name like :name";
		}
		String customerCode = query.getCustomerCode();
		if (StringUtils.isNotEmpty(customerCode)) {
			hql += " and cm.customerCode like :customerCode";
		}
		Integer status = query.getStatus();
		if (status != null) {
			hql += " and cm.status=:status";
		}

		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(CustomerQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		String customerCode = query.getCustomerCode();
		if (StringUtils.isNotEmpty(customerCode)) {
			params.put("customerCode", "%" + customerCode + "%");
		}
		Integer status = query.getStatus();
		if (status != null) {
			params.put("status", status);
		}
		return params;
	}

	@Transactional(readOnly = true)
	public List<CustomerContact> findContactByCustomerCode(String customerCode) {
		List<CustomerContact> contacts = customerContactDao.findByCustomerCode(customerCode);
		return contacts;
	}

	@Transactional(readOnly = true)
	public Customer findAllByCode(String customerCode) {
		Customer customer = customerDao.findById(customerCode);
		customer.setContacts(customerContactDao.findByCustomerCode(customerCode));
		return customer;
	}

	public void updateAll(Customer customer) {
		customer.setStatus(ExamineStatus.APPROVING.getStatus());
		customer.setRemove(true);
		customerDao.update(customer);
		if (customer.getContacts() != null) {
			for (CustomerContact contact : customer.getContacts()) {
				contact.setCustomerCode(customer);
				contact.setCreateTime(new Date());
				if (contact.getCustomerContactId() != null) {
					customerContactDao.update(contact);
				} else {
					customerContactDao.save(contact);
				}
			}
		}
	}

	public void updateDelete(String customerCode) {
		Customer customer = customerDao.findById(customerCode);
		customer.setStatus(ExamineStatus.REJECTED.getStatus());
		customer.setRemove(false);
		super.update(customer);
	}

	public void deleteContanct(CustomerContact contact) {
		customerContactDao.delete(contact);
	}

	public void changeStatus(String customerCode, Integer status) {
		customerDao.changeStatus(customerCode, status);
	}

	public List<Customer> findbyStatus(Integer status) {
		return customerDao.findbyStatus(status);
	}

	public List<CustomerVo> findBydel() {
		List<Customer> customers = customerDao.findBydel();
		return VoTool.convert(customers, CustomerVo.class);
	}

	public Double queryMoney(String customerCode, String yearValue) {
		String sql = " select sum(a.sub_total*c.tax_rate*0.01+a.sub_total)sub_total from quotation_list a" +
				 " inner join quotation b on  a.quotation_id = b.quotation_id  " +
				 " inner join pr_project c on b.project_code = c.project_code  " +
				 " where b.status=1 and  b.customer = '"+customerCode+"'  ";
		if(StringUtils.isNotEmpty(yearValue)){
			sql+= " and left(casetime,4)='"+yearValue+"'  ";
		}
		List list = jdbcTemplate.queryForList(sql);
		Double money = null;
		if(list!=null){
			Map map =  (Map) list.get(0);
			money = (Double) map.get("sub_total");
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

	public String queryTaxRate(Integer quotationId) {
		String sql = " select  tax_rate from  pr_project  a " +
					 " inner join   quotation b on a.PROJECT_CODE = b.PROJECT_CODE " +
					 " where b.QUOTATION_ID ="+quotationId+"  ";
		Map map = this.jdbcTemplate.queryForMap(sql);
		return map.get("tax_rate").toString();
	}
	//*查询客户信息
	@SuppressWarnings("unchecked")
	public List<CustomerVo> queryCustomerList() {
		// TODO Auto-generated method stub
		String sql = " select name, customer_code from customer where remove = 1 ";
		return this.jdbcTemplate.query(sql,new RowMapper(){

			public Object mapRow(ResultSet rs, int paramInt)
					throws SQLException {
				CustomerVo  customer = new CustomerVo();
				customer.setCustomerCode(rs.getString("customer_code"));
				customer.setName(rs.getString("name"));
				return customer;
			}
		});
	}
	//查询客户类型
	@SuppressWarnings("unchecked")
	public List<CustomerTypeVo> queryCustomersType() {
		String sql = " select name,type_id from customer_type  where remove = 1 ";
		return this.jdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerTypeVo customerType = new CustomerTypeVo();
				customerType.setName(rs.getString("name"));
				customerType.setTypeId(rs.getInt("type_id"));
				return customerType;
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<CustomerVo> queryCustomerListByType(String type) {
		String sql = " select a.customer_code,name from customer as a where a.type = '"+type+"' ";
		return this.jdbcTemplate.query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerVo customer = new CustomerVo();
				customer.setCustomerCode(rs.getString("customer_code"));
				customer.setName(rs.getString("name"));
				return customer;
			}
		});
	}

	public CustomerContact findCustomerContact(String customerCode) {
		String  sql  = " select * from  customer_contact where  customer_contact_id = 4";
		CustomerContact customerContact = customerContactDao.findContactByCustomerCode(customerCode);
		return customerContact;
	}

}