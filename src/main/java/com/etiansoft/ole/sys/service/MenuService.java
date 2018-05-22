package com.etiansoft.ole.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SysMenuDao;
import com.etiansoft.ole.po.SysMenu;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.OleHibernateService;

@Service
@Transactional
public class MenuService extends OleHibernateService<SysMenu> {

	@Autowired
	private SysMenuDao menuDao;

	@Transactional(readOnly = true)
	public List<SysMenu> findMenus(SysUser user) {
		if (user.getAdmin()) {
			List<SysMenu> adminMenus = new ArrayList<SysMenu>();
			adminMenus.add(menuDao.findById(14));
			return adminMenus;
		}
		return menuDao.findAllOrder();
	}
}