package com.etiansoft.ole.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.CustomerTypeDao;
import com.etiansoft.ole.po.CustomerType;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.CustomerTypeVo;

@Service
@Transactional
public class CustomerTypeService extends OleHibernateService<CustomerType> {

	@Autowired
	private CustomerTypeDao customerTypeDao;

	public void saveMuli(String names) {
		String[] name = names.split(",");
		for (String str : name) {
			CustomerType customerType = new CustomerType();
			customerType.setRemove(true);
			customerType.setName(str);
			customerTypeDao.save(customerType);
		}
	}

	public List<CustomerTypeVo> findByVoRemove() {
		List<CustomerType> types = customerTypeDao.findByRemove();
		return VoTool.convert(types, CustomerTypeVo.class);
	}

	public void update(Integer typeId) {
		if (typeId != null) {
			CustomerType types = customerTypeDao.findById(typeId);
			types.setRemove(false);
			super.update(types);
		}
	}

	public void saveCusType(String name) {
		CustomerType customerType = new CustomerType();
		customerType.setName(name);
		customerType.setRemove(true);
		super.save(customerType);
	}
}