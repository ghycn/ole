package com.etiansoft.ole.pr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SerialNumberDao;
import com.etiansoft.ole.po.SerialNumber;
import com.etiansoft.tools.hibernate.HibernateService;

@Service
@Transactional
public class SerialNumberService extends HibernateService<SerialNumber> {

	@Autowired
	private SerialNumberDao serialNumberDao;

	public int findSerialNumber() {
		return serialNumberDao.findSerialNumber();
	}

	public void updateId(int serialNumber) {
		serialNumberDao.updateId(serialNumber);
	}
}