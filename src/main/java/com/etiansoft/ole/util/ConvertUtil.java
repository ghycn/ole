package com.etiansoft.ole.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.tools.hibernate.Pagation;
import com.etiansoft.tools.vo.Vo;

public class ConvertUtil {

	public static String getCurrentPage(String iDisplayStart, String iDisplayLength) {
		if (StringUtils.isNotEmpty(iDisplayStart) && StringUtils.isNotEmpty(iDisplayLength)) {
			int start = Integer.parseInt(iDisplayStart);
			int limit = Integer.parseInt(iDisplayLength);
			iDisplayStart = String.valueOf(start / limit + 1);
		}
		return iDisplayStart;
	}

	/**
	 * @see <code>{@link #generatorTablePage(Pagation, DataTablePage, Class)}</code>
	 */
	@Deprecated
	public static DataTablePage generatorTablePage(Pagation pagation, DataTablePage dataTablePage) {
		dataTablePage.setiTotalRecords(pagation.getCount());
		dataTablePage.setiTotalDisplayRecords(pagation.getCount());
		dataTablePage.setAaData(pagation.getRecords());
		return dataTablePage;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <PO, VO extends Vo<PO>> DataTablePage generatorTablePage(Pagation pagation, DataTablePage dataTablePage, Class<VO> voClass) {
		dataTablePage.setiTotalRecords(pagation.getCount());
		dataTablePage.setiTotalDisplayRecords(pagation.getCount());
		List<?> pos = pagation.getRecords();
		List<Vo<PO>> vos = new ArrayList<Vo<PO>>(pos.size());
		for (Object po : pos) {
			if (po == null) {
				continue;
			}
			try {
				Vo result = voClass.newInstance();
				result.from(po);
				vos.add(result);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		dataTablePage.setAaData(vos);
		return dataTablePage;
	}

	public static LinkedHashMap<String, Object> getParamsMap(Object obj) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		Field[] fs = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true);
			Object val;
			try {
				val = f.get(obj);
				String type = f.getType().toString();
				if (!type.endsWith("List") && !type.endsWith("int") && val != null && !"".equals(val)) {
					params.put(f.getName(), val);
				}
			} catch (Exception e) {
				new Exception("反射获取出错：" + e.getMessage());
			}
		}
		return params;
	}
}