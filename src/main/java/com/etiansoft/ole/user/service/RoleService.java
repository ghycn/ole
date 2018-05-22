package com.etiansoft.ole.user.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SysRoleDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SysRole;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.user.query.RoleQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.SysRoleVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class RoleService extends OleHibernateService<SysRole> {

	@Autowired
	private SysRoleDao sysRoleDao;

	public DataTablePage getData(RoleQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = sysRoleDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		return ConvertUtil.generatorTablePage(pagation, page, SysRoleVo.class);
	}

	private String makeHql(RoleQuery query) {
		String hql = "from SysRole cm where 1=1";
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and cm.name like :name";
		}
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(RoleQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		return params;
	}

	public Object findAllByCode(Integer roleId) {
		SysRole sysRole = sysRoleDao.findById(roleId);
		return sysRole;
	}

	public void deleteByCode(Integer roleId) {
		SysRole role = sysRoleDao.findById(roleId);
		if (role != null) {
			Set<SysUser> users = role.getUsers();
			for (SysUser user : users) {
				Iterator<SysRole> roles = user.getRoles().iterator();
				while (roles.hasNext()) {
					SysRole r = roles.next();
					if (r.getRoleId().equals(roleId)) {
						roles.remove();
					}
				}
			}
		}
		sysRoleDao.deleteById(roleId);
	}

	public void save(SysRole sysRole) {
		super.save(sysRole);
	}

	public void update(SysRole role) {
		SysRole dbSysRole = sysRoleDao.findById(role.getRoleId());
		dbSysRole.setName(role.getName());
		dbSysRole.setNotes(role.getNotes());
		super.update(dbSysRole);
	}

	public List<SysRoleVo> findByVoRemove() {
		List<SysRole> types = sysRoleDao.findByRemove();
		return VoTool.convert(types, SysRoleVo.class);
	}
}
