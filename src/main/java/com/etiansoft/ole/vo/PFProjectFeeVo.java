package com.etiansoft.ole.vo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.etiansoft.ole.po.OtherPFProjectFee;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.ole.util.ServletContextProvider;
import com.etiansoft.tools.vo.Vo;

public class PFProjectFeeVo implements Vo<PFProjectFee> {

	/** 项目款ID **/
	private Integer projectFeeId;
	/** 项目编号 **/
	private String projectCode;
	/** 项目名称 **/
	private String projectName;
	/** 报价单名称 **/
	private String quotationName;
	/** 名称 **/
	private String name;
	/** 类型 **/
	private String type;
	/** 申请日期 **/
	private String applyDate;
	/** 请款人 编号 **/
	private String applicantCode;
	/** 请款人 名称 **/
	private String applicantName;
	/** 含税金额 **/
	private Double amount;
	/** 状态 (0申请,1通过,2驳回,3废弃,4关账) **/
	private Integer status;
	/** 备注 **/
	private String notes;
	/** 厂商名称 **/
	private String supplierName;
	/** 银行户名 **/
	private String accountName;
	/** 银行账号 **/
	private String bankAccount;
	/** 开户行 **/
	private String bankName;
	/** 支行名称 **/
	private String branchName;
	/** 其它类型编号 **/
	private Integer typeId;
	/** 其它类型名称 **/
	private String typeName;
	/** 税点 **/
	private Integer taxRate;
	private String quotationListId;//报价项id

	public String getQuotationListId() {
		return quotationListId;
	}

	public void setQuotationListId(String quotationListId) {
		this.quotationListId = quotationListId;
	}

	public Integer getProjectFeeId() {
		return projectFeeId;
	}

	public void setProjectFeeId(Integer projectFeeId) {
		this.projectFeeId = projectFeeId;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getQuotationName() {
		return quotationName;
	}

	public void setQuotationName(String quotationName) {
		this.quotationName = quotationName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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

	@Override
	@SuppressWarnings("unchecked")
	public void from(PFProjectFee po) {
		setProjectFeeId(po.getProjectFeeId());
		setAmount(po.getAmount());
		setName(po.getName());
		setNotes(po.getNotes());
		setApplyDate(DateTool.formatDate(po.getApplyDate()));
		setStatus(po.getStatus());
		Set<QuotationList> quotationLists = po.getQuotationLists();
		Iterator<QuotationList> iterator = quotationLists.iterator();
		StringBuilder types = new StringBuilder();
		StringBuilder quotationListIds = new StringBuilder();
		while (iterator.hasNext()) {
			QuotationList quotationList = iterator.next();
			types.append(quotationList.getItem()).append(",");
			quotationListIds.append(quotationList.getQuotationListId()).append(",");
		}
		Quotation quotation = po.getQuotation();
		HttpSession session = ServletContextProvider.getSession();
		Map<String, String> map = (Map<String, String>) session
				.getAttribute("typeMap");
		if (quotation != null) {
			Set<OtherPFProjectFee> otherPFProjectFees = quotation
					.getOtherPFProjectFees();
			Iterator<OtherPFProjectFee> iteratorO = otherPFProjectFees
					.iterator();
			while (iteratorO.hasNext()) {
				OtherPFProjectFee otherPfProjectFee = iteratorO.next();
				types.append(
						map.get(String.valueOf(otherPfProjectFee
								.getOtherTypeId()))).append(",");
			}
			setProjectName(quotation.getPrProject().getName() );
			setQuotationName(quotation.getActivity().getName());//quotation.getPrProject().getName() + '-' + quotation.getActivity().getName()
			setTaxRate(quotation.getPrProject().getTaxRate());
		}
		quotationListId = quotationListIds.toString();
		if (quotationListId.length() > 0) {
			quotationListId = quotationListId.substring(0, quotationListId.length() - 1);
		}
		setQuotationListId(quotationListId);
		String str = types.toString();
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		setType(str);
		if (po.getTypeId() != null) {
			setType(po.getTypeName());
		}
		PrProject project = po.getPrProject();
		if (project != null) {
			setProjectCode(project.getProjectCode());
			setProjectName(project.getName());
		}
		SysUser sysUser = po.getApplicant();
		if (sysUser != null) {
			setApplicantCode(sysUser.getUserCode());
			setApplicantName(sysUser.getName());
		}
		SuSupplier supplier = po.getSupplier();
		if (supplier != null) {
			setSupplierName(supplier.getName());
			setAccountName(supplier.getAccountName());
			setBankAccount(supplier.getBankAccount());
			setBankName(supplier.getBankName());
			setBranchName(supplier.getBranchName());
		}
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public void to(PFProjectFee po) {

	}

	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

}
