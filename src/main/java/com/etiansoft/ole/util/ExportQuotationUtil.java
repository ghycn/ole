package com.etiansoft.ole.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;

import com.etiansoft.ole.po.Customer;
import com.etiansoft.ole.po.CustomerContact;
import com.etiansoft.ole.po.Quotation;
import com.etiansoft.ole.po.QuotationList;
import com.etiansoft.ole.po.SysConfiguration;
import com.etiansoft.ole.quotation.service.QuotationService;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExportQuotationUtil extends HttpServlet{
	@Autowired
	private static QuotationService quotationServiceImpl;
	public static void export(Workbook targetWork, Workbook sourceWork, boolean copyStyle, Quotation quotation, Customer customer, CustomerContact contact, SysConfiguration config) throws Exception {
		Sheet sourceSheet = sourceWork.getSheetAt(0);
		Sheet targetSheet = targetWork.createSheet("sheet1");
		// 复制源表中的行
		int maxColumnNum = 0;
		Map styleMap = (copyStyle) ? new HashMap() : null;
		Drawing patriarch = targetSheet.createDrawingPatriarch(); // 用于复制注释
		for (int i = 0; i <= sourceSheet.getLastRowNum(); i++) {
			Row sourceRow = sourceSheet.getRow(i);
			Row targetRow = targetSheet.createRow(i);
			if (sourceRow != null) {
				copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}
		// 复制源表中的合并单元格
		mergerRegion(targetSheet, sourceSheet);
		// 设置目标sheet的列宽
		for (int i = 0; i <= maxColumnNum; i++) {
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		setTitleCellVal(targetSheet, customer, contact);
		maxColumnNum = setRowVal(targetSheet, sourceSheet, targetWork, sourceWork, maxColumnNum, styleMap, patriarch, quotation, config);
	}

	// 头部的一些信息
	private static void setTitleCellVal(Sheet targetSheet, Customer customer, CustomerContact contact) {
//		Row row2 = targetSheet.getRow(2);
//		Cell row2Cell1 = row2.getCell(1);
//		row2Cell1.setCellValue(customer.getName());
//		Cell row2Cell3 = row2.getCell(4);
//		row2Cell3.setCellValue(DateTool.formatDate(new Date()));
//
//		Row row3 = targetSheet.getRow(3);
//		Cell row3Cell1 = row3.getCell(1);
//		row3Cell1.setCellValue(contact.getCustomContactName());
//		Cell row3Cell3 = row3.getCell(4);
//		row3Cell3.setCellValue(contact.getMobile());
//
//		Row row4 = targetSheet.getRow(4);
//		Cell row4Cell1 = row4.getCell(1);
//		row4Cell1.setCellValue(contact.getTel());
//		Cell row4Cell3 = row4.getCell(4);
//		row4Cell3.setCellValue(contact.getEmail());
//
//		Row row5 = targetSheet.getRow(5);
//		Cell row5Cell1 = row5.getCell(1);
//		row5Cell1.setCellValue(customer.getCustomerAddress());
		
		
		Row row3 = targetSheet.getRow(2);
		Cell row3Cell1 = row3.getCell(1);
		row3Cell1.setCellValue(customer.getName());//客户名
		Row row4 = targetSheet.getRow(3);
		Cell row4Cell1 = row4.getCell(1);
		row4Cell1.setCellValue(contact.getCustomContactName());//客户联络人
		Row row5 = targetSheet.getRow(4);
		Cell row5Cell1 = row5.getCell(1);
		row5Cell1.setCellValue(contact.getMobile());//客户座机
		Row row6 = targetSheet.getRow(5);
		Cell row6Cell1 = row6.getCell(1);
		row6Cell1.setCellValue(contact.getTel());//客户手机
		Row row7 = targetSheet.getRow(6);
		Cell row7Cell1 = row7.getCell(1);
		row7Cell1.setCellValue(contact.getEmail());//客户邮箱
		Row row8= targetSheet.getRow(7);
		Cell row8Cell1 = row8.getCell(1);
		row8Cell1.setCellValue(customer.getCustomerAddress());//客户地址
		
		
	}

	// 填充报价项的内容
	private static int setRowVal(Sheet targetSheet, Sheet sourceSheet, Workbook targetWork, Workbook sourceWork, int maxColumnNum, Map styleMap, Drawing patriarch, Quotation quotation, SysConfiguration config) throws Exception {
//		Set<QuotationList> list = quotation.getQuotationList();
//		ApplicationContext context =  new FileSystemXmlApplicationContext("classpath:/spring-config.xml"); 
//		QuotationService quotationService = (QuotationService)context.getBean("quotationService");
		List<QuotationList> list = quotationServiceImpl.queryQuotationList(quotation.getQuotationId());

		targetSheet.shiftRows(1, 50, -1);
		int size = list.size();
		targetSheet.shiftRows(10, 50, size);
		for (int i = 10; i < size + 10; i++) {
			Row sourceRow = sourceSheet.getRow(10);
			Row targetRow = targetSheet.createRow(i);
			if (sourceRow != null) {
				copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}
		int count = 9;
		String sum = "";
		int i = 0;
		Iterator<QuotationList> iterator = list.iterator();
		while (iterator.hasNext()) {
//			i++;
//			QuotationList item = iterator.next();
//			Row targetRow = targetSheet.getRow(count);
//			Cell cell0 = targetRow.getCell(0);
//			cell0.setCellValue(item.getCategory());//类别
//			Cell cell2 = targetRow.getCell(1);
//			cell2.setCellValue(item.getItem());//项目名
//			Cell cell3 = targetRow.getCell(2);
//			cell3.setCellValue(item.getQuantity());//数量
//			Cell cell4 = targetRow.getCell(3);
//			cell4.setCellValue(item.getUnitPrice());//单价
//			Cell cell5 = targetRow.getCell(4);
//			cell5.setCellValue(item.getSubTotal());//小计
//			Cell cell6 = targetRow.getCell(5);
//			cell6.setCellValue(item.getSpec());//规格
//			Cell cell7 = targetRow.getCell(6);
//			cell7.setCellValue(item.getNote());//备注
//			sum += "E" + (count * 1 + 1 * 1) + "+";
//			count++;
			
			i++;
			QuotationList item = iterator.next();
			Row targetRow = targetSheet.getRow(count);
			Cell cell0 = targetRow.getCell(0);
			cell0.setCellValue(i);//编号
			Cell cell1 = targetRow.getCell(1);
			cell1.setCellValue(item.getCategory());//分类
			Cell cell2 = targetRow.getCell(2);
			cell2.setCellValue(item.getItem());//品项
			Cell cell3 = targetRow.getCell(3);
			cell3.setCellValue(item.getQuantity());//数量
			Cell cell4 = targetRow.getCell(4);
			cell4.setCellValue(item.getQuantity());//单价
			Cell cell5 = targetRow.getCell(5);
			cell5.setCellValue(item.getSubTotal());//小计
			sum += "F" + (count * 1 + 1 * 1) + "+";
			count++;
		}
		setRowStyle(targetSheet, size, sum, config);
		return maxColumnNum;
	}

	private static void setRowStyle(Sheet targetSheet, int size, String sum, SysConfiguration config) {
//		Row row = targetSheet.getRow(0);
//		row.setHeight((short) 400);
//		Row row1 = targetSheet.getRow(1);
//		row1.setHeight((short) 400);
//		Row row2 = targetSheet.getRow(2);
//		row2.setHeight((short) 400);
//		Row row3 = targetSheet.getRow(3);
//		row3.setHeight((short) 400);
//		Row row4 = targetSheet.getRow(4);
//		row4.setHeight((short) 400);
//
//		Row row11 = targetSheet.getRow(11 + size);
//		row11.setHeight((short) 400);
//		Row row12 = targetSheet.getRow(12 + size);
//		row12.setHeight((short) 800);
//		Row row13 = targetSheet.getRow(13 + size);
//		row13.setHeight((short) 400);
//		Row row14 = targetSheet.getRow(14 + size);
//		row14.setHeight((short) 400);
//		Row row15 = targetSheet.getRow(15 + size);
//		row15.setHeight((short) 400);
//		Row row16 = targetSheet.getRow(16 + size);
//		row16.setHeight((short) 400);
//		Row row17 = targetSheet.getRow(17 + size);
//		row17.setHeight((short) 400);
//		Row row18 = targetSheet.getRow(18 + size);
//		row18.setHeight((short) 400);
		Row row19 = targetSheet.getRow(19 + size);
		row19.setHeight((short) 400);
//		Row row20 = targetSheet.getRow(20 + size);
//		row20.setHeight((short) 600);
//		Row titleRow = targetSheet.getRow(5);
//		titleRow.setHeight((short) 400);
//
		Row notTaxMoneyRow = targetSheet.getRow(10 + size);
		Cell notTaxMoneyCell = notTaxMoneyRow.getCell(6);
		sum = sum.substring(0, sum.length() - 1);
		notTaxMoneyCell.setCellFormula("SUM(" + sum + ")");
		
		String tax = config.getTaxRate().replaceAll("%", "");
		Float f = Float.valueOf(tax);

		Row taxRow = targetSheet.getRow(11 + size);
		Cell taxcell = taxRow.getCell(5);
		taxcell.setCellValue("税(" + tax + "%)");
		Cell taxcellval = taxRow.getCell(6);
		taxcellval.setCellFormula("G" + (11 * 1 + size * 1) + "*" + (f / 100) + "");
		Row taxRow9 = targetSheet.getRow(12 + size);
		Cell taxcellval9 = taxRow9.getCell(6);
		taxcellval9.setCellFormula("SUM(G"+(11 * 1 + size * 1)+":G"+(12 * 1 + size * 1)+")");
//		Row taxRow15 = targetSheet.getRow(15 + size);
//		Cell taxcellval15 = taxRow15.getCell(6); 
//		taxcellval15.setCellFormula("SUM(G"+(10 * 1 + size * 1)+")");
		
//		Row taxRow18 = targetSheet.getRow(18 + size);
//		
//		Cell taxcellval18 = taxRow18.getCell(1);
//		taxcellval18.setCellFormula("B2");
//		
//		Row taxRow19 = targetSheet.getRow(19 + size);
//		Cell taxcellval19 = taxRow19.getCell(1);
//		taxcellval19.setCellFormula("B5");
	}

	public static void copyRow(Row targetRow, Row sourceRow, Workbook targetWork, Workbook sourceWork, Drawing targetPatriarch, Map styleMap) throws Exception {
		// 设置行高
		targetRow.setHeight(sourceRow.getHeight());
		for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
			Cell sourceCell = sourceRow.getCell(i);
			Cell targetCell = targetRow.getCell(i);
			if (sourceCell != null) {
				if (targetCell == null) {
					targetCell = targetRow.createCell(i);
				}
				// 拷贝单元格，包括内容和样式
				copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);
			}
		}
	}

	public static void copyCell(Cell targetCell, Cell sourceCell, Workbook targetWork, Workbook sourceWork, Map styleMap) {
		if (styleMap != null) {
			if (targetWork == sourceWork) {
				targetCell.setCellStyle(sourceCell.getCellStyle());
			} else {
				String stHashCode = "" + sourceCell.getCellStyle().hashCode();
				CellStyle targetCellStyle = (CellStyle) styleMap.get(stHashCode);
				if (targetCellStyle == null) {
					targetCellStyle = targetWork.createCellStyle();
					targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
					styleMap.put(stHashCode, targetCellStyle);
				}
				targetCell.setCellStyle(targetCellStyle);
			}
		}
		// 处理单元格内容
		switch (sourceCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			targetCell.setCellValue(sourceCell.getRichStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			targetCell.setCellValue(sourceCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			targetCell.setCellType(Cell.CELL_TYPE_BLANK);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			targetCell.setCellValue(sourceCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			targetCell.setCellFormula(sourceCell.getCellFormula());
			break;
		default:
			break;
		}
	}

	/**
	 * 功能：复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 * @param sourceSheet
	 */
	public static void mergerRegion(Sheet targetSheet, Sheet sourceSheet) throws Exception {
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
			CellRangeAddress newRange = new CellRangeAddress(oldRange.getFirstRow(), oldRange.getLastRow(), oldRange.getFirstColumn(), oldRange.getLastColumn());
			targetSheet.addMergedRegion(newRange);
		}
	}

	public static QuotationService getQuotationServiceImpl() {
		return quotationServiceImpl;
	}

	public static void setQuotationServiceImpl(QuotationService quotationServiceImpl) {
		ExportQuotationUtil.quotationServiceImpl = quotationServiceImpl;
	}
	
}