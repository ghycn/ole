package com.etiansoft.ole.cost.service;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.etiansoft.ole.pf.service.PfProjectFeeService;
import com.etiansoft.ole.po.CostAnalysis;
import com.etiansoft.ole.po.CostAnalysisItem;
import com.etiansoft.ole.po.OtherPFProjectFeeType;
import com.etiansoft.ole.po.PFProjectFee;
import com.etiansoft.ole.po.PrProject;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.pr.service.PrProjectService;
import com.etiansoft.ole.quotation.service.QuotationService;
import com.etiansoft.ole.util.DateUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CostAnalysisReportPdfService {

	@Autowired
	private PrProjectService prProjectService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private PfProjectFeeService pfProjectFeeService;
	@Autowired
	private CostAnalysisService costAnalysisService;

	private static Font headfont;
	private static Font keyfont;
	private static Font textfont;

	static {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
			keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
			textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int maxWidth = 520;

	public PdfPCell createCell(String value, Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	public PdfPCell createCell(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	public PdfPCell createCell(String value, Font font, int align, int colspan, int rowspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		}
		return cell;
	}

	public PdfPTable createTable(int colNumber) {
		PdfPTable table = new PdfPTable(colNumber);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	public void generatePDF() throws Exception {

	}

	public void reportPdf(String projectCode, Integer quotationId, OutputStream os) throws Exception {
		Document document = new Document();

//		File file = new File("E:\\text.pdf");
//		file.createNewFile();
		document.setPageSize(PageSize.A4);
		PdfWriter.getInstance(document, os);
		document.open();

		PrProject project = prProjectService.findById(projectCode);
		CostAnalysis analysis = costAnalysisService.findByQuotationId(quotationId);
		List<CostAnalysisItem> items = null;
		if (analysis != null) {
			items = costAnalysisService.findItemByAnalysisId(analysis.getCostAnalysisId());
		}
		List<OtherPFProjectFeeType>list=fildTypeIds(pfProjectFeeService.projectFeeTypeList(quotationId),pfProjectFeeService.otherProjectFeeTypeList());
		Double amoutTotal=quotationService.getAmountTotal(quotationId);
		Double otherAmoutTotal=quotationService.otherTotalAmount(quotationId);
		Quotation quotation = quotationService.findById(quotationId);
		PdfPTable table = createTable(6);
		table.addCell(createCell(project.getName() + "(" + project.getProjectCode() + ")", headfont, Element.ALIGN_CENTER, 6, 0, false));

		table.addCell(createCell("案件名称", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getName(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("对方发票抬头", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getCustomer().getName(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("案件编号", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getProjectCode(), textfont, Element.ALIGN_CENTER));

		table.addCell(createCell("案件执行时间", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(DateUtil.format(project.getExecutionTime(), "yyyy-MM-dd"), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("对方公司地址", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getCustomer().getCustomerAddress(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("", textfont, Element.ALIGN_CENTER));

		table.addCell(createCell("总金额", keyfont, Element.ALIGN_CENTER));
		BigDecimal   d   =   new   BigDecimal(project.getInvoiceAmount()*(1+project.getTaxRate()*0.01));  
		double   e   =   d.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		table.addCell(createCell(String.valueOf(e ), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("对接人姓名", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getOpenStaff().getName(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("总成本", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(String.valueOf(analysis.getTotalCost()), textfont, Element.ALIGN_CENTER));

		table.addCell(createCell("案件主负责人", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getOpenStaff().getName(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("对接人电话", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(project.getOpenStaff().getCellPhone(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("毛利", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(String.valueOf(analysis.getGrossProfit()), textfont, Element.ALIGN_CENTER));

		table.addCell(createCell("接案时间", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(DateUtil.format(project.getOpenTime(), "yyyy-MM-dd"), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("合同编号", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell(analysis.getContractNo(), textfont, Element.ALIGN_CENTER));
		table.addCell(createCell("利润比", keyfont, Element.ALIGN_CENTER));
		//利润比
		//analysis.getProfitRate()==null?0
		table.addCell(createCell(analysis.getProfitRate(), textfont, Element.ALIGN_CENTER));

		PdfPTable table2 = createTable(6);
//		table2.addCell(createCell("报价单", keyfont, Element.ALIGN_CENTER, 3, 0, true));
//		table2.addCell(createCell("成本", keyfont, Element.ALIGN_CENTER, 4, 0, true));
//		table2.addCell(createCell("编号", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("项目名", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("报价", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("制作厂商", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("对接人", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("电话", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("含税报价", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("编号", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("項目名", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("报价", textfont, Element.ALIGN_CENTER));
//		table2.addCell(createCell("制作厂商", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("数量", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("单价", textfont, Element.ALIGN_CENTER));
		table2.addCell(createCell("规格", textfont, Element.ALIGN_CENTER));
		int count = 1;
//		ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:/spring-config.xml"); 
//		QuotationService quotationService = (QuotationService)context.getBean("quotationService");
		List<QuotationList> quotationList = quotationService.queryQuotationList(quotation.getQuotationId());
		for (QuotationList item : quotationList) {
/*			table2.addCell(createCell(String.valueOf(count), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(item.getQuotationList().getItem(), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(String.valueOf(item.getQuotationList().getUnitPrice()), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(item.getQuotationList().getSupplier().getName(), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell("", textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell("", textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(String.valueOf(item.getTaxQuotation()), textfont, Element.ALIGN_CENTER));*/
			table2.addCell(createCell(String.valueOf(count), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(item.getItem(), textfont, Element.ALIGN_CENTER));
			Double subTotal = item.getSubTotal()==null?new Double(0):item.getSubTotal();
			BigDecimal   b   =   new   BigDecimal(subTotal*(1+project.getTaxRate()*0.01));  
			double   f   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  

			table2.addCell(createCell(String.valueOf(f), textfont, Element.ALIGN_CENTER));
			//table2.addCell(createCell("00", textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(String.valueOf(item.getQuantity()==null?0:item.getQuantity()), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(String.valueOf(item.getUnitPrice()==null?0:item.getUnitPrice()), textfont, Element.ALIGN_CENTER));
			table2.addCell(createCell(String.valueOf(item.getSpec()), textfont, Element.ALIGN_CENTER));
			count++;
		}

		PdfPTable table3 = new PdfPTable(2);
		PdfPTable nested1 = new PdfPTable(2);
//		nested1.addCell(createCell("设计费", keyfont, Element.ALIGN_CENTER, 0, 2, true));
//		nested1.addCell(createCell("天数", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getDesingDays()), textfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell("费用", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getDesingAmount()), textfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell("活动支援", keyfont, Element.ALIGN_CENTER, 0, 2, true));
//		nested1.addCell(createCell("人次", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getActiveSupportDays()), textfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell("费用", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getActiveSupportAmount()), textfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell("杂支费", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getOtherAmount()), textfont, Element.ALIGN_CENTER, 2, 0, true));
//		nested1.addCell(createCell("公关费", keyfont, Element.ALIGN_CENTER));
//		nested1.addCell(createCell(String.valueOf(analysis.getRelationAmount()), textfont, Element.ALIGN_CENTER, 2, 0, true));
//		nested1.addCell(createCell("备注", textfont, Element.ALIGN_CENTER, 0, 2, true));
//		nested1.addCell(createCell(String.valueOf(analysis.getNotes()), textfont, Element.ALIGN_CENTER, 2, 2, true));
		nested1.addCell(createCell("分类", textfont, Element.ALIGN_CENTER));
		nested1.addCell(createCell("費用", textfont, Element.ALIGN_CENTER));
		for (OtherPFProjectFeeType item : list) {
			nested1.addCell(createCell(item.getName(), textfont, Element.ALIGN_CENTER));
			nested1.addCell(createCell(String.valueOf(item.getAmount()), textfont, Element.ALIGN_CENTER));
			//count++;
		}
		table3.addCell(nested1);


		PdfPTable nested2 = new PdfPTable(4);
		nested2.addCell(createCell("未税总报价", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getNoTaxQuotation()), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell("发票税", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getInvoiceTax()), textfont, Element.ALIGN_CENTER));

		nested2.addCell(createCell("含税报价", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getTaxQuotation()), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell("企所税", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getEnterpriseTax()), textfont, Element.ALIGN_CENTER));

		nested2.addCell(createCell("外部成本", keyfont, Element.ALIGN_CENTER));
//		nested2.addCell(createCell(String.valueOf(analysis.getOtherAmount()), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(amoutTotal), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell("毛利", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getGrossProfit()), textfont, Element.ALIGN_CENTER));

		nested2.addCell(createCell("内部成本", keyfont, Element.ALIGN_CENTER));
//		nested2.addCell(createCell(String.valueOf(analysis.getInvoiceTax()), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(otherAmoutTotal), textfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell("利润比", keyfont, Element.ALIGN_CENTER));
		nested2.addCell(createCell(String.valueOf(analysis.getProfitRate()), textfont, Element.ALIGN_CENTER));

		table3.addCell(nested2);

		document.add(table);
		document.add(table2);
		document.add(table3);
		document.close();
	}
	private List<OtherPFProjectFeeType> fildTypeIds(List<PFProjectFee> fList,List<OtherPFProjectFeeType> typeId){
		List<OtherPFProjectFeeType> list=new ArrayList<OtherPFProjectFeeType>();
		 for(OtherPFProjectFeeType obj:typeId){
			 obj.setAmount(0.00);
			 double a = 0; 
			 if(fList!=null&&fList.size()>0){
			 	for(PFProjectFee pf:fList){
			 		if(pf.getTypeId()==obj.getId()){
			 			a += pf.getAmount();
			 			obj.setAmount(a);
			 		}
			 	}
			 }
			 list.add(obj);
		 }
		return list;
	}
}