/**
 * @项目名称: ole
 * @文件名称: SysUserVo.java
 * @author tianlihu
 * @Date: 2015-5-26
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.etiansoft.ole.annotation.Cell;
import com.etiansoft.ole.annotation.Sheet;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

@Sheet(name = "用户信息")
public class SysUserVo implements Vo<SysUser>,Comparable<SysUserVo>  {

	/** 用户编码 **/
	@Cell(name = "用户编号")
	private String userCode;
	/** 名称 **/
	@Cell(name = "姓名")
	private String name;
	/** 性别 **/
	private Boolean sex;
	/** 手机 **/
	@Cell(name = "手机")
	private String cellPhone;
	/** 座机 **/
	private String tel;
	/** 地址 **/
	private String address;
	/** 类别 **/
	private String type;
	/** 状态 **/
	private Boolean status;
	/** 基础工资 **/
	@Cell(name = "基础工资")
	private Double baseSalary;
	/** 工资计算 **/
	private String salary;
	/** 到职日期 **/
	private Date workDate;
	/** 逻辑状态 **/
	private Integer state;
	/** 创建时间 **/
	private String createTime;
	/** 邮箱 **/
	@Cell(name = "邮箱")
	private String mail;
	/** 生日 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String birthday;
    /** 入职日期 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String entryDate;
	/** 年假 **/
	private Integer vacationDays;
	/**请假天数**/
	private double leaveDays;

	public double getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(double leaveDays) {
		this.leaveDays = leaveDays;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
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

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(Integer vacationDays) {
		this.vacationDays = vacationDays;
	}

	@Override
	public void from(SysUser po) {
		setUserCode(po.getUserCode());
		setName(po.getName());
		setSex(po.getSex());
		setCellPhone(po.getCellPhone());
		setTel(po.getTel());
		setType(po.getType());
		setStatus(po.getStatus());
		setBaseSalary(po.getBaseSalary());
		setSalary(po.getSalary());
		setAddress(po.getAddress());
		setMail(po.getMail());
		setEntryDate(DateTool.formatDate(po.getEntryDate()));
		setState(po.getState());
		setCreateTime(DateTool.formatDate(po.getCreateTime()));
		setBirthday(DateTool.formatDate(po.getBirthday()));
		setVacationDays(po.getVacationDays());
	}

	@Override
	public void to(SysUser po) {

	}

	@Override
	public int compareTo(SysUserVo o) {
		 return this.getUserCode().compareTo(o.getUserCode());
	}

}
