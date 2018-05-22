package com.etiansoft.ole.vo;

import com.etiansoft.ole.annotation.Cell;
import com.etiansoft.ole.annotation.Sheet;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.tools.vo.Vo;

@Sheet(name = "厂商一覽")
public class SuSupplierVo implements Vo<SuSupplier> {

	/** 厂商编号 **/
	@Cell(name = "编号")
	private Integer supplierCode;
	/** 名称 **/
	@Cell(name = "公司名")
	private String name;
	/** 分类 **/
	@Cell(name = "分类")
	private String type;
	/** 银行账号 **/
	private String bankAccount;
	/** 银行户名 **/
	private String accountName;
	/** 开户行 **/
	@Cell(name = "开户行")
	private String bankName;
	/** 支行名称 **/
	@Cell(name = "支行名称")
	private String branchName;
	/** 所在地 **/
	@Cell(name = "所在地")
	private String address;
	/** 官网 **/
	@Cell(name = "官网")
	private String officialWebsite;
	/** 备注 **/
	private String note;
	/** 状态 **/
	private Integer status;

	public Integer getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(Integer supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void from(SuSupplier po) {
		setSupplierCode(po.getSupplierCode());
		setName(po.getName());
		setType(po.getType());
		setBankAccount(po.getBankAccount());
		setAccountName(po.getAccountName());
		setBankName(po.getBankName());
		setBranchName(po.getBankName());
		setAddress(po.getAddress());
		setOfficialWebsite(po.getOfficialWebsite());
		setNote(po.getNote());
		setStatus(po.getStatus());
	}

	@Override
	public void to(SuSupplier po) {

	}
}