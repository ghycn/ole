package com.etiansoft.ole.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SerialNumberUserDao;
import com.etiansoft.ole.po.SerialNumberUser;
import com.etiansoft.ole.util.OleHibernateService;

@Service
@Transactional
public class SerialNumberUserService extends OleHibernateService<SerialNumberUser> {

	@Autowired
	private SerialNumberUserDao serialNumberUserDao;

	public int findSerialNumber() {
		return serialNumberUserDao.findSerialNumber();
	}

	public void updateId(int serialNumber) {
		serialNumberUserDao.updateId(serialNumber);
	}
}