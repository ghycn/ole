package com.etiansoft.ole.supplier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SuSupplierTypeDao;
import com.etiansoft.ole.po.SuSupplierType;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.SuSupplierTypeVo;

@Service
@Transactional
public class SuSupplierTypeService extends OleHibernateService<SuSupplierType> {

	@Autowired
	private SuSupplierTypeDao suSupplierTypeDao;

	public void saveMuli(String names) {
		String[] name = names.split(",");
		for (String str : name) {
			SuSupplierType customerType = new SuSupplierType();
			customerType.setRemove(true);
			customerType.setName(str);
			suSupplierTypeDao.save(customerType);
		}
	}

	public List<SuSupplierTypeVo> findByVoRemove() {
		List<SuSupplierType> types = suSupplierTypeDao.findByRemove();
		return VoTool.convert(types, SuSupplierTypeVo.class);
	}

	public void inputSupType(String name) {
		SuSupplierType supplierType = new SuSupplierType();
		supplierType.setName(name);
		supplierType.setRemove(true);
		super.save(supplierType);
	}

	public void update(Integer typeId) {
		if (typeId != null) {
			SuSupplierType types = suSupplierTypeDao.findById(typeId);
			types.setRemove(false);
			super.update(types);
		}
	}
}