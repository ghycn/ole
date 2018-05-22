package com.etiansoft.ole.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	/**
	 * 一天中的天数
	 */
	public static long millionSecondsOfDay = 86400000;

	public final static String YYYYMMDD24HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public final static String YYMMDD24HHMMSSSSS = "yyMMddHHmmssSSS";

	/**
	 * 得到两个日期之间的天数,两头不算,取出数据后，可以根据需要再加
	 * 
	 * @author Vincent
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDay(Date date1, Date date2) {
		Long d2 = date2.getTime();
		Long d1 = date1.getTime();
		return (int) ((d2 - d1) / millionSecondsOfDay);
	}

	/**
	 * 计算日期加天数
	 * 
	 * @author Vincent
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	/**
	 * 根据指定日期格式格式化日期
	 * 
	 * @author Vincent
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String format(Date date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @author Vincent
	 * @param date
	 * @param formater
	 * @return
	 */
	public static Date parse(String date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		Date result = null;
		try {
			result = sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据日期取出是星期几
	 * 
	 * @author Vincent
	 * @param date
	 *            Date
	 * @return int 返回1-7
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * format "yyyy-MM-dd HH:mm:ss"
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).trim();

	}

	/**
	 * 取Java虚拟机系统时间, 下一天时间
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateNext(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DATE, 1);
		String monthValue = "";
		String dateValue = "";
		if (cl.get(Calendar.MONTH) + 1 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 1);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 1);
		}

		if (cl.get(Calendar.DATE) < 10) {
			dateValue = "0" + cl.get(Calendar.DATE);
		} else {
			dateValue = String.valueOf(cl.get(Calendar.DATE));
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + dateValue;
	}

	/**
	 * 取Java虚拟机系统时间, 上一月
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateNext() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		String monthValue = "";
		if (cl.get(Calendar.MONTH) + 2 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 2);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 2);
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + 01;
	}

	/**
	 * 取Java虚拟机系统时间, 上一月
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateNextByDate(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		String monthValue = "";
		if (cl.get(Calendar.MONTH) + 2 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 2);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 2);
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + 01;
	}

	/**
	 * 取Java虚拟机系统时间, 当天月
	 * 
	 * @param selectMonthDate
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringTodayDate(Date selectMonthDate) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(selectMonthDate);
		String monthValue = "";
		if (cl.get(Calendar.MONTH) + 1 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 1);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 1);
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + 01;
	}

	/**
	 * 取Java虚拟机系统时间, 前一月
	 * 
	 * @param selectMonthDate
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateBack(Date selectMonthDate) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(selectMonthDate);
		String monthValue = "";
		if (cl.get(Calendar.MONTH) < 10) {
			monthValue = "0" + cl.get(Calendar.MONTH);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH));
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + 01;
	}

	/**
	 * 取Java虚拟机系统时间, 下一天时间
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateNextByDate(String strDate) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(DateUtil.parse(strDate, "yyyy-MM-dd"));
		cl.add(Calendar.DATE, 1);
		String monthValue = "";
		String dateValue = "";
		if (cl.get(Calendar.MONTH) + 1 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 1);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 1);
		}

		if (cl.get(Calendar.DATE) < 10) {
			dateValue = "0" + cl.get(Calendar.DATE);
		} else {
			dateValue = String.valueOf(cl.get(Calendar.DATE));
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + dateValue;
	}

	/**
	 * 取Java虚拟机系统时间, 前一天时间
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	public static String getSysStringDateBackByDate(String strDate) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(DateUtil.parse(strDate, "yyyy-MM-dd"));
		cl.add(Calendar.DATE, -1);
		String monthValue = "";
		String dateValue = "";
		if (cl.get(Calendar.MONTH) + 1 < 10) {
			monthValue = "0" + (cl.get(Calendar.MONTH) + 1);
		} else {
			monthValue = String.valueOf(cl.get(Calendar.MONTH) + 1);
		}

		if (cl.get(Calendar.DATE) < 10) {
			dateValue = "0" + cl.get(Calendar.DATE);
		} else {
			dateValue = String.valueOf(cl.get(Calendar.DATE));
		}
		return cl.get(Calendar.YEAR) + "-" + monthValue + "-" + dateValue;
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取当前年份
	 */
	public static String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		return year;
	}
}