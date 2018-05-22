package com.etiansoft.ole.user.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.dao.SerialNumberUserDao;
import com.etiansoft.ole.dao.SysUserDao;
import com.etiansoft.ole.dao.WkLeaveDao;
import com.etiansoft.ole.dao.WkOvertimeDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.po.WkLeave;
import com.etiansoft.ole.po.WkOvertime;
import com.etiansoft.ole.user.query.UserQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.SysUserVo;
import com.etiansoft.tools.common.MD5;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserService extends OleHibernateService<SysUser> {

	@Autowired
	private SysUserDao userDao;
	@Autowired
	private WkLeaveDao wkLeaveDao;
	@Autowired
	private WkOvertimeDao wkOvertimeDao;
	@Autowired
	private SerialNumberUserDao serialNumberUserDao;

	public DataTablePage getData(UserQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = userDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		List<SysUser> users = (List<SysUser>) pagation.getRecords();
		for (SysUser user : users) {
			System.out.println(user.getEntryDate());
			salary(user);
		}
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, SysUserVo.class);
		return dataTablePage;
	}

	private String makeHql(UserQuery query) {
		String hql = "from SysUser cm where userCode != 'admin' and state=1";
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and cm.name like :name";
		}
		String cellPhone = query.getCellPhone();
		if (StringUtils.isNotEmpty(cellPhone)) {
			hql += " and cm.cellPhone like :cellPhone";
		}
		hql +=" order by userCode ";
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(UserQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		String cellPhone = query.getCellPhone();
		if (StringUtils.isNotEmpty(cellPhone)) {
			params.put("cellPhone", "%" + cellPhone + "%");
		}

		return params;
	}

	public void save(SysUser sysUser, String birthday,String hiredate) {
		sysUser.setLastLoginTime(new Date());
		sysUser.setCreateTime(new Date());
		sysUser.setEntryDate(DateUtil.parse(hiredate, "yyyy-MM-dd"));
		sysUser.setStatus(true);
		sysUser.setAdmin(false);
		sysUser.setState(1);
		sysUser.setBirthday(DateUtil.parse(birthday, "yyyy-MM-dd"));
		sysUser.setPassword(MD5.md5(sysUser.getPassword()));
		super.save(sysUser);
	}

	public void update(SysUser sysUser, String birthday,String hiredate) {
		SysUser user = userDao.findById(sysUser.getUserCode());
		user.setName(sysUser.getName());
		user.setBirthday(DateUtil.parse(birthday, "yyyy-MM-dd"));
		user.setSex(sysUser.getSex());
		user.setIdNum(sysUser.getIdNum());
		user.setTel(sysUser.getTel());
		user.setCellPhone(sysUser.getCellPhone());
		user.setMail(sysUser.getMail());
		user.setZipCode(sysUser.getZipCode());
		user.setAddress(sysUser.getAddress());
		user.setType(sysUser.getType());
		user.setStatus(sysUser.getStatus());
		user.setLastLoginTime(new Date());
		user.setEntryDate(DateUtil.parse(hiredate, "yyyy-MM-dd"));
		user.setWorkDate(sysUser.getWorkDate());
		user.setWorkAge(sysUser.getWorkAge());
		if (sysUser.getRoles() != null && sysUser.getRoles().size() > 0) {
			user.setRoles(sysUser.getRoles());
		}

		HttpSession session = ServletContextProvider.getSession();
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (loginUser.isBoss()) {
			user.setBaseSalary(sysUser.getBaseSalary());
		}
		userDao.update(user);
		if (StringUtils.equals(loginUser.getUserCode(), user.getUserCode())) {
			user.getRoles().toString();
			session.setAttribute(Constants.LOGIN_USER, user);
		}
	}

	public void update(SysUser user) {
		super.update(user);
	}

	public void update(String userCode, Integer state) {
		SysUser user = userDao.findById(userCode);
		user.setState(state);
		super.update(user);

		HttpSession session = ServletContextProvider.getSession();
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (StringUtils.equals(loginUser.getUserCode(), user.getUserCode())) {
			user.getRoles().toString();
			session.setAttribute(Constants.LOGIN_USER, user);
		}
	}

	public void changeStatus(String userCode, boolean status) {
		SysUser dbUser = userDao.findById(userCode);
		dbUser.setStatus(status);
		super.update(dbUser);

		HttpSession session = ServletContextProvider.getSession();
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (StringUtils.equals(loginUser.getUserCode(), dbUser.getUserCode())) {
			dbUser.getRoles().toString();
			session.setAttribute(Constants.LOGIN_USER, dbUser);
		}
	}

	private void salary(SysUser user) {
		Double salaryD = user.getBaseSalary();
		if (user.getBaseSalary() == null) {
			salaryD = 0d;
		}
		Double workDaysD = 21.5;
		BigDecimal workDays = new BigDecimal(Double.toString(workDaysD));// 一个月多少天
		BigDecimal salary = new BigDecimal(Double.valueOf(salaryD));// 基本工资
		double daySalary = salary.divide(workDays, 2).doubleValue();
		BigDecimal baseSalary = new BigDecimal(Double.valueOf(daySalary));// 平均每天多少钱

		List<WkLeave> wkLeaves = wkLeaveDao.findbyUserCode(user.getUserCode());
		Double wkDaysD = 0d;
		for (WkLeave leave : wkLeaves) {
			if (leave.getDuration() != null) {
				wkDaysD += leave.getDuration();
			}
		}
		Double realS = 0d;
		if (wkDaysD > 0d) {
			BigDecimal days = new BigDecimal(Double.toString(wkDaysD));// 请假多少天
			double realDays = workDays.subtract(days).doubleValue();
			BigDecimal realDay = new BigDecimal(Double.valueOf(realDays));// 请完假后还剩多少天
			realS = baseSalary.multiply(realDay).doubleValue();// 平均每天的钱乘以请完假剩下的天数=剩下的工资
		}

		List<WkOvertime> overs = wkOvertimeDao.findByUserCode(user.getUserCode());
		Double overDaysD = 0d;
		for (WkOvertime overtime : overs) {
			if (overtime.getDuration() != null) {
				overDaysD += overtime.getDuration();
			}
		}
		Double overSalary = 0d;
		if (overDaysD > 0d) {
			BigDecimal overDays = new BigDecimal(Double.valueOf(overDaysD));// 加班多少天
			overSalary = overDays.multiply(baseSalary).doubleValue();// 平均每天的钱乘以加班的天数=加班的工资
		}
		Double last = realS + overSalary;
		if (last == 0d) {
			last = salaryD;
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		user.setSalary(df.format(last));
	}

	public List<SysUserVo> findBydel() {
		List<SysUser> sysUsers = userDao.findBydel();
		return VoTool.convert(sysUsers, SysUserVo.class);
	}

	public void updateDays(SysUser sysUser) {
		SysUser user = userDao.findById(sysUser.getUserCode());
		user.setName(sysUser.getName());
		user.setVacationDays(sysUser.getVacationDays());
		if (sysUser.getRoles() != null && sysUser.getRoles().size() > 0) {
			user.setRoles(sysUser.getRoles());
		}

		HttpSession session = ServletContextProvider.getSession();
		SysUser loginUser = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (loginUser.isBoss()) {
			user.setBaseSalary(sysUser.getBaseSalary());
		}
		userDao.update(user);
		if (StringUtils.equals(loginUser.getUserCode(), user.getUserCode())) {
			user.getRoles().toString();
			session.setAttribute(Constants.LOGIN_USER, user);
		}
	}

	public void setPorject(String setProject, String[] projectCodes, ModelMap model) {
	}

	public void saveSetProject(PrProject project, SysUser sysUser) {
		// TODO Auto-generated method stub
		project.setProjectCode(project.getProjectCode());
		sysUser.setProjects(sysUser.getProjects());
		userDao.save(sysUser);
	}
}
