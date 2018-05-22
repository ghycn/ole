package com.etiansoft.ole.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.etiansoft.ole.po.SysMenu;
import com.etiansoft.tools.hibernate.HibernateDao;

@Repository
@SuppressWarnings("unchecked")
public class SysMenuDao extends HibernateDao<SysMenu> {

	public List<SysMenu> findAllOrder() {
		return hibernateTemplate.find("from SysMenu order by sort");
	}
}
