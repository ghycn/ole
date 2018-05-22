package com.etiansoft.ole.po;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/** 用户(SysUser) **/
public class SysUser {

	/** 用户编码 **/
	private String userCode;
	/** 名称 **/
	private String name;
	/** 密码 **/
	private String password;
	/** 性别 **/
	private Boolean sex;
	/** 生日 **/
	private Date birthday;
	/** 头像 **/
	private String headIcon;
	/** 身份证号 **/
	private String idNum;
	/** 座机 **/
	private String tel;
	/** 手机 **/
	private String cellPhone;
	/** 邮箱 **/
	private String mail;
	/** 邮编 **/
	private String zipCode;
	/** 地址 **/
	private String address;
	/** 类别 **/
	private String type;
	/** 状态 **/
	private Boolean status;
	/** 最后登入时间 **/
	private Date lastLoginTime;
	/** 创建时间 **/
	private Date createTime;
	/** 基础工资 **/
	private Double baseSalary;
	/** 工资计算 **/
	private String salary;
	/** 入职日期 **/
	private Date entryDate;
	/** 到职日期 **/
	private Date workDate;
	/** 工龄 **/
	private Integer workAge;
	/** 年假 **/
	private Integer vacationDays;
	/** 是否管理员(0否、1是) **/
	private Boolean admin;
	/** 逻辑状态 **/
	private Integer state;

	private Set<PrProject> projects;

	private Set<SysRole> roles;


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getStatus() {
		if (status == null) {
			return false;
		}
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<SysRole> getRoles() {
		if (roles == null) {
			return Collections.emptySet();
		}
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}

	public Integer getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(Integer vacationDays) {
		this.vacationDays = vacationDays;
	}

	public Boolean getAdmin() {
		if (admin == null) {
			return false;
		}
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public boolean isBoss() {
		Set<SysRole> roles = getRoles();
		for (SysRole role : roles) {
			if (role.getRoleId() == 1) {
				return true;
			}
		}
		return false;
	}

	public Set<PrProject> getProjects() {
		return projects;
	}

	public void setProjects(Set<PrProject> projects) {
		this.projects = projects;
	}
}