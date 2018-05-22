package com.etiansoft.ole.device.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.DeviceBorrowDao;
import com.etiansoft.ole.dao.DeviceDao;
import com.etiansoft.ole.dao.SysUserDao;
import com.etiansoft.ole.device.query.DeviceBorrowRecordQuery;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Device;
import com.etiansoft.ole.po.DeviceBorrow;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.vo.DeviceBorrowVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DeviceBorrowService extends OleHibernateService<DeviceBorrow> {

	@Autowired
	private DeviceBorrowDao deviceBorrowDao;
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private SysUserDao sysUserDao;

	public DataTablePage getData(DeviceBorrowRecordQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = deviceBorrowDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		List<DeviceBorrow> list = (List<DeviceBorrow>) pagation.getRecords();
		pagation.setRecords(list);
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, DeviceBorrowVo.class);
		return dataTablePage;
	}

	public void applyBorrowSubmit(DeviceBorrow deviceBorrow, String userCode, Integer deviceCode, String borrowTime, String returnTime, HttpSession session) {
		Device device = deviceDao.findById(deviceCode);
		SysUser sysUser = sysUserDao.findById(userCode);
		deviceBorrow.setApplicant(sysUser);
		deviceBorrow.setDevice(device);
		deviceBorrow.setApplyTime(new Date());
		deviceBorrow.setBorrowTime(DateUtil.parse(borrowTime, "yyyy-MM-dd"));
		deviceBorrow.setBorrowTime(DateUtil.parse(returnTime, "yyyy-MM-dd"));
		device.setStaffHoldNum(device.getStaffHoldNum() + deviceBorrow.getLendNum());
		device.setStockNum(device.getStockNum() - deviceBorrow.getLendNum());
		deviceDao.save(device);
		deviceBorrow.setStatus(true);
		deviceBorrowDao.save(deviceBorrow);
	}

	public void deviceBorrowSave(DeviceBorrow deviceBorrow, String userCode, Integer deviceCode) {
		Device device = deviceDao.findById(deviceCode);
		SysUser sysUser = sysUserDao.findById(userCode);
		deviceBorrow.setApplicant(sysUser);
		deviceBorrow.setDevice(device);
		deviceBorrow.setApplyTime(new Date());
//		deviceBorrow.setBorrowTime(DateUtil.parse(deviceBorrow.getBorrowTime(), "yyyy-MM-dd"));
//		deviceBorrow.setBorrowTime(DateUtil.parse(returnTime, "yyyy-MM-dd"));
		device.setStaffHoldNum(device.getStaffHoldNum() + deviceBorrow.getLendNum());
		device.setStockNum(device.getStockNum() - deviceBorrow.getLendNum());
		deviceDao.save(device);
		deviceBorrow.setStatus(true);
		deviceBorrowDao.save(deviceBorrow);
	}
	
	public void returnBack(Integer borrowCode) {
		DeviceBorrow borrow = deviceBorrowDao.findById(borrowCode);
		borrow.setStatus(false);
		Device device = borrow.getDevice();
		device.setStaffHoldNum(device.getStaffHoldNum() - borrow.getLendNum());
		device.setStockNum(device.getStockNum() + borrow.getLendNum());
		borrow.setReturnTime(new Date());
		deviceBorrowDao.update(borrow);
		deviceDao.update(device);
	}

	public void delete(Integer borrowCode) {
//		DeviceBorrow borrow = deviceBorrowDao.findById(borrowCode);
/*		Device device = borrow.getDevice();
		device.setStaffHoldNum(device.getStaffHoldNum() - borrow.getLendNum());
		device.setStockNum(device.getStockNum() + borrow.getLendNum());
		deviceDao.update(device);*/
		deviceBorrowDao.deleteById(borrowCode);
	}
	private String makeHql(DeviceBorrowRecordQuery query) {
		String hql = "from DeviceBorrow cm where 1=1";
		String lender = query.getLender();
		if (StringUtils.isNotEmpty(lender)) {
			hql += " and cm.applicant.name like :lender";
		}
		String code = query.getCode();
		if (StringUtils.isNotEmpty(code)) {
			hql += " and cm.device.deviceCode=" + code;
		}
		if(StringUtils.isNotEmpty(query.getStatus())){
			Boolean status1 = Boolean.parseBoolean(query.getStatus());
				if (status1) {
					hql += " and cm.status=0" + " and cm.device.remove = 1";
				} else {
					hql += " and cm.status=1" + " and cm.device.remove = 1";
				}
		}else{
			hql += " and (cm.status=0 or cm.status=1)" + " and cm.device.remove = 1";
		}
		hql +="  order by borrowCode desc";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(DeviceBorrowRecordQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		String lender = query.getLender();
		if (StringUtils.isNotEmpty(lender)) {
			params.put("lender", "%" + lender + "%");
		}

		return params;
	}
}
