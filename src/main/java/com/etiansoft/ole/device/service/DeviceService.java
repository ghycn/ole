package com.etiansoft.ole.device.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.DeviceDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Device;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.vo.DeviceBorrowVo;
import com.etiansoft.ole.vo.DeviceVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class DeviceService extends OleHibernateService<Device> {

	@Autowired
	private DeviceDao deviceDao;

	public DataTablePage getData(Device device, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = deviceDao.findPage(currentPage, page.getiDisplayLength(), makeHql(device), makeQuery(device));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, DeviceVo.class);
		return dataTablePage;
	}

	private String makeHql(Device device) {
		String hql = "from Device where 1=1 and remove =1";
		if (StringUtils.isNotEmpty(device.getName())) {
			hql += " and name like :name";
		}
		hql+="  order by expirationDate desc, buyingTime desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(Device device) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if (StringUtils.isNotEmpty(device.getName())) {
			params.put("name", "%" + device.getName() + "%");
		}
		return params;
	}

	public void save(Device device, String buyTime, String expireDate) {
		device.setStaffHoldNum(0);
		device.setStockNum(device.getTotal());
		device.setBuyingTime(DateTool.parseDate(buyTime));
		device.setExpirationDate(DateTool.parseDate(expireDate));
		device.setRemove(true);
		super.save(device);
	}

	public void update(Device device) {
		Device dbDevice = findById(device.getDeviceCode());
		dbDevice.setName(device.getName());
		dbDevice.setTotal(device.getTotal());
		dbDevice.setStaffHoldNum(device.getStaffHoldNum());
		dbDevice.setStockNum(device.getStockNum());
		super.update(dbDevice);
	}

	public void deleteByCode(Integer deviceCode) {
		deviceDao.deleteById(deviceCode);
	}

	public Object findAllByCode(Integer deviceCode) {
		Device device = deviceDao.findById(deviceCode);
		return device;
	}
	public List<Device> getDeviceList(){
		return deviceDao.getDeviceList();
	}
	
	public DeviceBorrowVo findBorrow(Integer deviceCode) {
		DeviceBorrowVo deviceBorrow = deviceDao.findVoBorrow(deviceCode);
		return deviceBorrow;
	}

}
