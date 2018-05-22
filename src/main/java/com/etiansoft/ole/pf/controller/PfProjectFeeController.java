package com.etiansoft.ole.pf.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.tools.ant.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.ExamineStatus;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.pf.query.PfProjectFeeQuery;
import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SuSupplier;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.service.ActivityService;
import com.etiansoft.ole.quotation.service.QuotationListService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.supplier.service.SupplierService;
import com.etiansoft.ole.util.TreeNode;
import com.etiansoft.ole.util.ZTree;
import com.etiansoft.ole.vo.PFProjectFeeVo;
import com.etiansoft.ole.vo.QuotationListVo;
import com.etiansoft.ole.vo.QuotationVo;
import com.etiansoft.ole.vo.SuSupplierTypeVo;

@Controller
public class PfProjectFeeController {

	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private QuotationListService quotationListService;
	@Autowired
	private ActivityService activityService;

	@RequestMapping("/projectFee/applyList")
	public String list() {
		return "projectFee/applyList";
	}

	@RequestMapping("/projectFee/applyFeeList")
	public String listApplyFee(ModelMap model,Integer quotationId,Integer costStatus, DataTablePage page,String pAmount) {
		model.put("quotationId", quotationId);
		List<PFProjectFeeVo> pfFeeList=pfProjectFeeService.findByQuotationId(quotationId);
		fildTypes(pfFeeList);
		model.put("pfFeeList", pfFeeList);
		model.put("pAmount", pAmount);
		model.put("quotationId", quotationId);
		model.put("costStatus", costStatus);
		return "projectFee/applyFeeList";
	}
	@ResponseBody
	@RequestMapping("/projectFee/applyFeeListData")
	public DataTablePage applyFeeData(Integer quotationId, DataTablePage page) {
		return pfProjectFeeService.getAppFeeData(quotationId, page);
	}
	@ResponseBody
	@RequestMapping("/projectFee/applyListData")
	public DataTablePage data(PfProjectFeeQuery query, DataTablePage page) {
		return pfProjectFeeService.getData(query, page);
	}

	@RequestMapping("/projectFee/apply/add")
	public String applyAdd(PFProjectFee projectFee, ModelMap model, HttpSession session,Integer quotationId,String pAmount) {
		putModel(projectFee, model, session);
		model.put("quotationId", quotationId);
		model.put("pAmount", pAmount);
		String quotationIds=pfProjectFeeService.getQuotationListId(quotationId).toString();
		//model.put("quotationIds",quotationIds.substring(1, quotationIds.length() - 1).replaceAll(" ", ""));
		List<String> qutListId = quotationService.getQuotationListId(quotationId);
		List<QuotationListVo> list=quotationService.findByQuotationId(quotationId);
		model.put("treeNodes", buildTree(list,qutListId));
		return "projectFee/apply";
	}
//	@ResponseBody
//	@RequestMapping(value = "/projectFee/apply/treeNodes", method = RequestMethod.POST)
//	public List<TreeNode> treeNodes(Integer quotationId) {
//		List<String> qutListId = quotationService.getQuotationListId(quotationId);
//		List<QuotationList> list=quotationService.getQuotationList(quotationId);
//		return buildTrees(list,qutListId);
//	}
	
	@ResponseBody
	@RequestMapping(value = "/projectFee/apply/treeNodes", method = RequestMethod.POST)
	public List<ZTree> treeNodes(Integer quotationId,Integer id,String ids,boolean checked) {
//		List<String> qutListId = quotationService.getQuotationListId(quotationId);
		List<String> qutListId =new ArrayList<String>();
		if(id!=null){
			qutListId = quotationService.getQuotationListId(id);
		}
		List<QuotationList> list=quotationService.getQuotationList(quotationId);
		return buildZTree(list,qutListId,id,ids,checked);
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/projectFee/apply/treeNodesChecked", method = RequestMethod.POST)
//	public List<ZTree> treeNodesChecked(Integer quotationId,Integer id) {
//		List<String> qutListId =new ArrayList<String>();
//		if(id!=null){
//			qutListId = quotationService.getQuotationListId(quotationId);
//		}
//		List<QuotationList> list=quotationService.getQuotationList(quotationId);
//		return buildZTree(list,qutListId,id);
//	}
	
	@RequestMapping("/projectFee/apply/edit")
	public String applyEdit(PFProjectFee projectFee, ModelMap model, HttpSession session,Integer projectFeeId) {
		if (projectFeeId != null) {
			projectFee = pfProjectFeeService.findById(projectFeeId);
//			String quotationIds=pfProjectFeeService.getQuotationListId(projectFee.getQuotation().getQuotationId()).toString();
			String quotationIds=pfProjectFeeService.getQuotationListIdByPrpjectFeeId(projectFeeId).toString();
			model.put("quotationId", projectFee.getQuotation().getQuotationId());
			model.put("quotationIds",quotationIds.substring(1, quotationIds.length() - 1).replaceAll(" ", ""));
			model.put("remainingAmount",quotationListAmount(quotationIds.substring(1, quotationIds.length() - 1).replaceAll(" ", "")));
		}
		putModel(projectFee, model, session);
		return "projectFee/apply";
	}

	@RequestMapping("/projectFee/otherApply/edit")
	public String otherApplyEdit( ModelMap model, HttpSession session,Integer projectFeeId) {
		PFProjectFee projectFee = pfProjectFeeService.findById(projectFeeId);
		String  name = projectFee.getName();
		Double amount = projectFee.getAmount();
		PFProjectFee pfProjectFee = pfProjectFeeService.findById(projectFeeId);
		Integer quotationId = pfProjectFee.getQuotation().getQuotationId();//报价单id
		Double taxTotal = quotationService.findById(quotationId).getTaxTotal();
		Integer taxRate = pfProjectFee.getQuotation().getPrProject().getTaxRate();
		double a = taxTotal*((new Double(taxRate)*0.01)+1);//报价单总额
		double b = pfProjectFeeService.queryAlreadyPleaseAmount(quotationId);//查询该报价单已请款金额
		BigDecimal  c = new BigDecimal(a-b);  
		double   pAmount = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		model.put("name",name);
		model.put("amount",amount);
		model.put("types", pfProjectFeeService.otherProjectFeeTypeList());
		model.put("projectFeeId",projectFeeId);
		model.put("pAmount",pAmount);
		model.put("quotationId", quotationId);
		return "projectFee/otherApply";
	}
	@SuppressWarnings("unchecked")
	private void putModel(PFProjectFee projectFee, ModelMap model, HttpSession session) {
		List<SuSupplierTypeVo> supplierTypeList = supplierService.querySupplierTypeList();
		//List<QuotationVo> quotations = quotationService.findbyStatus(ExamineStatus.APPROVED.getStatus());
		List<SuSupplier> suppliers = supplierService.findbyStatus(ExamineStatus.APPROVED.getStatus());
		//List<QuotationListVo> trees = quotationListService.createTree(quotationListService.findAll());
		model.put("projectFee", projectFee);
		//model.put("quotations", quotations);
		model.put("suppliers", suppliers);
		model.put("supplierTypeList", supplierTypeList);

		Map<String, String> map = (Map<String, String>) session.getAttribute("typeMap");
		String[] types = new String[map.size()];
		Object[] s = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {
			types[i] = map.get(s[i]);
		}
		model.put("types", map);
		//model.put("trees", trees);
	}

	@ResponseBody
	@RequestMapping("/projectFee/apply/getTypesData")
	public List<QuotationListVo> getTypesData(Integer quotationId) {
		if (quotationId != null) {
			Quotation quotation = quotationService.findById(quotationId);
			return quotationListService.createTree(quotation.getQuotationList());
		}
		return null;
	}

	@RequestMapping("/projectFee/apply/addOrUpdate")
	public String applyAddOrUpdate(String quotationIds, Integer[] types, Integer quotationId, Integer supplierCode, String applydate, PFProjectFee projectFee, HttpSession session) throws Exception {
		if (projectFee.getProjectFeeId() != null) {
			pfProjectFeeService.update(quotationIds,quotationId, supplierCode, applydate, projectFee, session, types);
		} else {
			pfProjectFeeService.save(quotationIds,quotationId, supplierCode, applydate, projectFee, session, types);
		}
		return "redirect:/projectFee/applyList";
	}
	
	@ResponseBody
	@RequestMapping("/projectFee/apply/doDelete")
	public void doDelete(Integer pfProjectFeeId, Integer quotationId) {
		Quotation quotation = quotationService.findById(quotationId);
		pfProjectFeeService.deleteByCode(pfProjectFeeId);
		quotationService.changeTaxTotal(quotationId);
		quotationService.changeInvoiceAmount(quotation);
	}

	public static void main(String[] args) {
		String s = "1";
		System.out.println(s.getClass().getName());
	}

	@RequestMapping("/projectFee/apply/info")
	public String info(Integer projectFeeId, ModelMap model) {
		PFProjectFeeVo projectFee = pfProjectFeeService.findVoById(projectFeeId, PFProjectFeeVo.class);
		model.put("projectFee", projectFee);
		return "projectFee/info";
	}
	//其它请款申请添加
	@RequestMapping("/projectFee/otherApply/add")
	public String otherApplyAdd(ModelMap model, HttpSession session,Integer quotationId,String pAmount) {
//		putModel(projectFee, model, session);
		model.put("quotationId", quotationId);
		model.put("pAmount", pAmount);
//		String quotationIds=pfProjectFeeService.getQuotationListId(quotationId).toString();
//		model.put("quotationIds",quotationIds.substring(1, quotationIds.length() - 1).replaceAll(" ", ""));
		model.put("types", pfProjectFeeService.otherProjectFeeTypeList());
//		List<QuotationListVo> list=quotationService.findByQuotationId(quotationId);
//		model.put("treeNodes", buildTree(list,qutListId));
		return "projectFee/otherApply";
	}
	
	@RequestMapping("/projectFee/otherApply/addOrUpdate")
	public String otherApplyAddOrUpdate(Integer quotationId, PFProjectFee projectFee, HttpSession session) throws Exception {
		//projectFee.setQuotationId(quotationId);
		if (projectFee.getProjectFeeId() != null) {
			pfProjectFeeService.otherProjectFeeUpdate(quotationId,projectFee,session);
		} else {
			pfProjectFeeService.otherProjectFeeSave(quotationId,projectFee,session);
		}
		return "redirect:/projectFee/applyList";
	}
	private List<TreeNode> buildTree(List<QuotationListVo> quoVoList,List<String> qutListId){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(quoVoList.size()>0){
			for(QuotationListVo qu:quoVoList){
				if(qu.getParent()==null){
					TreeNode treeNode = new TreeNode();
					treeNode.setTitle(qu.getCategory());
//					treeNode.setKey(qu.getQuotationListId().toString());
					List<TreeNode> childNodes=new ArrayList<TreeNode>();
					for(QuotationListVo child:quoVoList){
						if(child.getQuotationListId()==qu.getQuotationListId()||qu.getCategory().equals(child.getCategory())){
							TreeNode childTreeNode = new TreeNode();
							childTreeNode.setTitle(child.getItem());
							childTreeNode.setKey(child.getQuotationListId().toString());
							if(qutListId.contains(child.getQuotationListId())){
								childTreeNode.setSelect(true);
							}
							childNodes.add(childTreeNode);
						}
					}
//					if(childNodes!=null&&childNodes.size()>0){
						treeNode.setChildren(childNodes);
//					}
//					if(qutListId.contains(qu.getQuotationListId())||(treeNode.getChildren()!=null&&treeNode.getChildren().size()>0)){
//						treeNode.setSelect(true);
//					}
					nodes.add(treeNode);
				}
			}
		}
//		Collections.reverse(nodes);
		return nodes;
	}
	private List<TreeNode> buildTrees(List<QuotationList> quoVoList,List<String> qutListId){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(quoVoList.size()>0){
			for(QuotationList qu:quoVoList){
				if(qu.getParent()==null){
					TreeNode treeNode = new TreeNode();
					treeNode.setTitle(qu.getCategory());
					List<TreeNode> childNodes=new ArrayList<TreeNode>();
					for(QuotationList child:quoVoList){
						if(child.getQuotationListId()==qu.getQuotationListId()||qu.getCategory().equals(child.getCategory())){
							TreeNode childTreeNode = new TreeNode();
							childTreeNode.setTitle(child.getItem());
							childTreeNode.setKey(child.getQuotationListId().toString());
							if(qutListId.contains(child.getQuotationListId())){
								childTreeNode.setSelect(true);
							}
							childNodes.add(childTreeNode);
						}
					}
						treeNode.setChildren(childNodes);
					nodes.add(treeNode);
				}
			}
		}
		return nodes;
	}
	
	private void fildTypes(List<PFProjectFeeVo> pfFeeList){
		if(pfFeeList!=null&&pfFeeList.size()>0){
			for(PFProjectFeeVo pf:pfFeeList){
				List<String> list=pfProjectFeeService.findQuotationItems(pf.getProjectFeeId().toString());
				if(list!=null&&list.size()>0){
					StringBuilder sb=new StringBuilder();
					for(String st:list){
						sb.append(st).append(",");
					}
					sb.deleteCharAt(sb.length()-1);
					pf.setType(sb.toString());
				}

			}
		}
	}
	
	
	private List<ZTree> buildZTree(List<QuotationList> quoVoList,List<String> qutListId,Integer id,String ids,boolean checked){
		List<ZTree> nodes=new ArrayList<ZTree>();
		if(quoVoList.size()>0){
			for(QuotationList qu:quoVoList){
				if(qu.getParent()==null){
					ZTree treeNode = new ZTree();
					treeNode.setName(qu.getCategory());
					treeNode.setOpen(true);
					if(qutListId!=null&&qutListId.size()>0){
						if(id==qu.getQuotationListId()){
							treeNode.setDoCheck(false);
						}
					}else{
						treeNode.setDoCheck(false);
					}
					List<ZTree> childNodes=new ArrayList<ZTree>();
					for(QuotationList child:quoVoList){
						if(child.getQuotationListId()==qu.getQuotationListId()||qu.getCategory().equals(child.getCategory())){
							ZTree childTreeNode = new ZTree();
							childTreeNode.setName(child.getItem());
							childTreeNode.setId(child.getQuotationListId());
							childTreeNode.setPid(qu.getQuotationListId());
							childTreeNode.setOpen(true);
							if(qutListId.size()>0){
								if(qutListId.contains(child.getQuotationListId())){
									if(checked){
										childTreeNode.setChecked(true);
									}
								}else{
									if(checked){
										childTreeNode.setDoCheck(false);
									}
								}
							}else{
								if(ids!=null&&ids!=""){
									if(ids.contains(child.getQuotationListId().toString())){
										if(checked){
											childTreeNode.setChecked(true);
										}
									}
									if(id!=null){
										if(id==child.getQuotationListId()){
											if(checked){
												childTreeNode.setChecked(true);
											}
										}else{
											if(ids.contains(child.getQuotationListId().toString())){
												childTreeNode.setChecked(true);
											}
										}
									}
								}else{
									if(id==child.getQuotationListId()){
										if(checked){
											childTreeNode.setChecked(true);
										}
									}
								}
								childTreeNode.setDoCheck(true);
							}
							childNodes.add(childTreeNode);
						}
					}
						treeNode.setChildren(childNodes);
						nodes.add(treeNode);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 查询总的报价项金额
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/projectFee/queryQuotationListAmount")
	public Double queryQuotationListAmount(String str,String projectFeeId){
		if(str!=null && !"".equals(str)){
			String quotationListId = str.substring(0, str.length()-1);
			Double  quotationListAmount= pfProjectFeeService.querySelectQuotationListAmount(quotationListId);//查询选择报价项的金额
			Double ProjectFeeAmoount = pfProjectFeeService.queryProjectFeeAmoount(quotationListId);//查询已请款总额
			BigDecimal  b = new BigDecimal(quotationListAmount-ProjectFeeAmoount);  
			double   amount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
//			if(org.apache.commons.lang.StringUtils.isNotEmpty(projectFeeId)&&!"undefined".equals(projectFeeId)){
//				return quotationListAmount;
//			}
			return amount;
		}else{
			return new Double(0.00);
		}
	}
	
	private Double quotationListAmount(String quotationListId){
		Double  quotationListAmount= pfProjectFeeService.querySelectQuotationListAmount(quotationListId);//查询选择报价项的金额
		Double ProjectFeeAmoount = pfProjectFeeService.queryProjectFeeAmoount(quotationListId);//查询已请款总额
//		BigDecimal  b = new BigDecimal(quotationListAmount);  
		BigDecimal  b = new BigDecimal(quotationListAmount-ProjectFeeAmoount);  
		double   amount = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return amount;
	}
}


