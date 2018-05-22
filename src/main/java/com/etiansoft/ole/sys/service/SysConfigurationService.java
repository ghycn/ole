package com.etiansoft.ole.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiansoft.ole.dao.SysConfigurationDao;
import com.etiansoft.ole.po.SysConfiguration;
import com.etiansoft.ole.util.OleHibernateService;

@Service
@Transactional
public class SysConfigurationService extends OleHibernateService<SysConfiguration> {

	@Autowired
	private SysConfigurationDao configurationDao;

	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	public List<SysConfiguration> getConfig() {
		SysConfiguration config = new SysConfiguration();
		List<SysConfiguration> configs = configurationDao.findAll();
		return configs;
	}

	public void update(SysConfiguration config) {
		super.update(config);
	}

	public void setIsUsed(Integer id) {
		
		String sql1 = " update SYS_CONFIGURATION set is_used = 0 ";
		this.jdbcTemplate.update(sql1);
		
		String sql2 = " update SYS_CONFIGURATION set is_used = 1  where id = "+id+" ";
		this.jdbcTemplate.update(sql2);
		
	}

	public void add(String taxRate) {
		String str = taxRate+"%";
		String sql = " insert into  SYS_CONFIGURATION (is_used,tax_rate) values (0,'"+str+"') ";
		this.jdbcTemplate.update(sql);

	}
}