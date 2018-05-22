package com.etiansoft.ole.vo;

import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateTool;
import com.etiansoft.tools.vo.Vo;

public class CostAnalysisVo implements Vo<CostAnalysis> {

	/** 编号 */
	private Integer costAnalysisId;
	/** 报价ID **/
	private Integer quotationId;
	/** 案件编号 */
	private String projectCode;
	/** 案件名称 */
	private String projectName;
	/** 公司名称 */
	private String projectCustomerName;
	/** 开案人 */
	private String projectOpenStaffName;
	/** 开案时间 */
	private String projectOpenTime;
	/** 案件备注 */
	private String projectNotes;
	/** 状态 */
	private Integer status;
	/** 状态 */
	private String quotationName;
	/** 审批人 **/
	private String approvalOfPersonnel;
	/** 审批人 姓名**/
	private String approvalOfPersonnelName;
	public Integer getCostAnalysisId() {
		return costAnalysisId;
	}

	public void setCostAnalysisId(Integer costAnalysisId) {
		this.costAnalysisId = costAnalysisId;

	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
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

	public String getProjectCustomerName() {
		return projectCustomerName;
	}

	public void setProjectCustomerName(String projectCustomerName) {
		this.projectCustomerName = projectCustomerName;
	}

	public String getProjectOpenStaffName() {
		return projectOpenStaffName;
	}

	public void setProjectOpenStaffName(String projectOpenStaffName) {
		this.projectOpenStaffName = projectOpenStaffName;
	}

	public String getProjectOpenTime() {
		return projectOpenTime;
	}

	public void setProjectOpenTime(String projectOpenTime) {
		this.projectOpenTime = projectOpenTime;
	}

	public String getProjectNotes() {
		return projectNotes;
	}

	public void setProjectNotes(String projectNotes) {
		this.projectNotes = projectNotes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void from(CostAnalysis po) {
		setCostAnalysisId(po.getCostAnalysisId());
		PrProject project = po.getProject();
		if (project != null) {
			setProjectCode(project.getProjectCode());
			setProjectName(project.getName());
			setProjectNotes(project.getNotes());
			setProjectOpenTime(DateTool.formatDate(project.getOpenTime()));
		}
		setApprovalOfPersonnelName(po.getProject().getApprovalOfPersonnelName());
		setStatus(po.getStatus());
		Customer customer = po.getProject().getCustomer();
		po.getQuotation().getActivity().getName();
		setQuotationName(po.getQuotation().getActivity().getName());
		if (customer != null) {
			setProjectCustomerName(customer.getName());
		}
		SysUser sysUser = po.getProject().getOpenStaff();
		if (sysUser != null) {
			setProjectOpenStaffName(sysUser.getName());
		}
		Quotation quotation = po.getQuotation();
		if (quotation != null) {
			setQuotationId(quotation.getQuotationId());
		}
	}

	public String getQuotationName() {
		return quotationName;
	}

	public void setQuotationName(String quotationName) {
		this.quotationName = quotationName;
	}

	@Override
	public void to(CostAnalysis po) {

	}

	public String getApprovalOfPersonnel() {
		return approvalOfPersonnel;
	}

	public void setApprovalOfPersonnel(String approvalOfPersonnel) {
		this.approvalOfPersonnel = approvalOfPersonnel;
	}

	public String getApprovalOfPersonnelName() {
		return approvalOfPersonnelName;
	}

	public void setApprovalOfPersonnelName(String approvalOfPersonnelName) {
		this.approvalOfPersonnelName = approvalOfPersonnelName;
	}
}