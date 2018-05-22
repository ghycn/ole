package com.etiansoft.ole.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.Device;
import com.etiansoft.ole.vo.DeviceBorrowVo;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
public class DeviceDao extends HibernateDao<Device> {

	public DeviceBorrowVo findVoBorrow(final Integer deviceCode) {
		return hibernateTemplate.execute(new HibernateCallback<DeviceBorrowVo>() {
			@Override
			public DeviceBorrowVo doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from DeviceBorrowVo where device.deviceCode=?";
				return (DeviceBorrowVo) session.createQuery(hql).setInteger(0, deviceCode).uniqueResult();
			}
		});
	}
	public List<Device> getDeviceList() {
		return hibernateTemplate.execute(new HibernateCallback<List<Device>>() {
			@Override
			public List<Device> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from Device d where d.remove=1").list();
			}
		});
	}
}
