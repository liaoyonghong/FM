package com.versionsystem.common;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 日期工具类
 */
public class DateTools {

	private static final int FIRST_DAY = Calendar.SUNDAY;
	private static final String MINUTE_TIME_FORMAT = "HH:mm";
	private static final String DEFAULT_STR = "yyyy-MM-dd";
	private static final String DATE_TIME_STR = "yyyy-MM-dd HH:mm";
	private static SimpleDateFormat defaultSdf = new SimpleDateFormat(DEFAULT_STR);
	private static SimpleDateFormat dTimeSdf = new SimpleDateFormat(DATE_TIME_STR);

	/**
	 *  将日期字符串转化为新格式的字符串
	 * @param timestr
	 * @param form
	 * @param newForm
	 * @return
	 */
	public static String paraseStringToString(String timestr, String form, String newForm) {

		SimpleDateFormat sdf1 = new SimpleDateFormat(form);
		SimpleDateFormat sdf2 = new SimpleDateFormat(newForm);
		Date d = null;
		try {
			d = sdf1.parse(timestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf2.format(d);
	}
	
	/**
	 * 将日期转化为指定格式的字符串
	 * @param d
	 * @param form
	 * @return
	 */
	public static String parseToString(Timestamp d, String form) {
		form = form == null ? DEFAULT_STR : form;
		SimpleDateFormat sdf = new SimpleDateFormat(form);
		return sdf.format(d);
	}

	public static String parseToString(Date d, String form) {
		form = form == null ? DEFAULT_STR : form;
		SimpleDateFormat sdf = new SimpleDateFormat(form);
		return sdf.format(d);
	}

	public static String toDateTimeString(Timestamp d) {
		return parseToString(d, DATE_TIME_STR);
	}

	public static String toMTimeString(Timestamp d) {
		return parseToString(d, MINUTE_TIME_FORMAT);
	}

	/**
	 *  将字符串转化为日期
	 * @param timestr
	 * @param form
	 * @return
	 */
	public static Date paraseStringToDate(String timestr, String form) {
		Date date = null;
		form = form == null ? DEFAULT_STR : form;
		Format f = new SimpleDateFormat(form);
		try {
			date = (Date) f.parseObject(timestr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 *  根据传入的日期获取所在月份所有日期
	 * @param d
	 * @return
	 */
	public static List<String> getAllDaysMonthByDate(Date d, String format) {
		format = format == null ? DateTools.DEFAULT_STR : format;
		List<String> lst = new ArrayList<String>();
		Date date = getMonthStart(d);
		Date monthEnd = getMonthEnd(d);
		SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		while (!date.after(monthEnd)) {
			lst.add(sdf1.format(date));
			date = getNext(date);
		}
		return lst;
	}

	// 获取月初日期
	public String getMonthStart() {
		Date d = new Date();
		return defaultSdf.format(getMonthStart(d));
	}

	// 根据传入日期来获取一个月的开始时间
	public static String getMonthStartStr(Date d) {
		return defaultSdf.format(getMonthStart(d));
	}

	// 根据传入时间获取一个月月末时间
	public static String getMonthEndStr(Date d) {
		return defaultSdf.format(getMonthEnd(d));
	}

	// 获取月末日期
	public String getMonthEnd() {
		Date d = new Date();
		return defaultSdf.format(getMonthEnd(d));
	}

	public static List<String> getAllDaysMonth() {
		List<String> lst = new ArrayList<String>();
		Date d = new Date();

		Date date = getMonthStart(d);
		Date monthEnd = getMonthEnd(d);
		while (!date.after(monthEnd)) {
			lst.add(defaultSdf.format(date));
			date = getNext(date);
		}
		return lst;
	}

	private static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}

	private static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}

	public static Date getNext(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	// 根据日期来获取一周的第一天
	public static String getWeekStartDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		setToFirstDay(c);
		return printDay(c, DEFAULT_STR);
	}

	public static Timestamp getWeekStartDay(Timestamp d) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			setToFirstDay(c);
			return new Timestamp(defaultSdf.parse(printDay(c, DEFAULT_STR)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 根据日期来获取一周的最后一天
	public static String getWeekEndtDay(String dateStr) {
		try {
			Date d = defaultSdf.parse(dateStr);
			return getWeekEndtDay(d);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 根据日期来获取一周的最后一天
	public static String getWeekEndtDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		setToFirstDay(c);
		c.add(Calendar.DATE, 6);
		return printDay(c, DEFAULT_STR);
	}

	/**
	 * 根据日期来获取其所在周的每一天
	 * 
	 * @param d
	 * @return
	 */
	public static List<String> getAllweekDays(Date d, String format) {
		Calendar c = Calendar.getInstance();
		List<String> lst = new ArrayList<String>();
		c.setTime(d);
		setToFirstDay(c);
		for (int i = 0; i < 7; i++) {
			String day = printDay(c, format);
			lst.add(day);
			c.add(Calendar.DATE, 1);
		}
		return lst;
	}
	
	public static List<String> getAllweekDays(String dateStr, String format) {
		if(StringUtils.isBlank(format))	format = DEFAULT_STR;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return getAllweekDays(sdf.parse(dateStr), format);
		} catch (ParseException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	/**
	 * 获取某日期为星期几，1代表Mon，7代表Sun
	 * @param pTime
	 * @param format
	 * @return
	 */
	public static int getWeekDay(String pTime, String format) {
		int dayForWeek = DateTools.toCalendarWeekDay(pTime, format);
		return (dayForWeek == 1 ? 7 : dayForWeek - 1);
	}
	
	/**
	 * 获取某日期为星期几，1代表Mon，0代表Sun
	 * @param pTime
	 * @param format
	 * @return
	 */
	public static int getWesternWeekDay(String pTime, String format) {
		return DateTools.toCalendarWeekDay(pTime, format) - 1;
	}
	
	/**
	 * 获取某日期为星期几，1代表Mon，0代表Sun
	 * @param date
	 * @return
	 */
	public static int getWesternWeekDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	public static int toCalendarWeekDay(String pTime, String format) {
		
		if(StringUtils.isBlank(format))	format = DEFAULT_STR;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			Calendar c = Calendar.getInstance();
			if(StringUtils.isBlank(pTime))
				c.setTime(new Date());
			else
				c.setTime(sdf.parse(pTime));
		
			return c.get(Calendar.DAY_OF_WEEK);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String[] getDateArr(Timestamp date) {
		String dTimeStr = "";
		if(date != null) {
			dTimeStr = dTimeSdf.format(date);
		}
		return dTimeStr.split(" ", 2);
	}

	private static void setToFirstDay(Calendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	private static String printDay(Calendar calendar, String format) {
		if (format == null)
			return defaultSdf.format(calendar.getTime());
		else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(calendar.getTime());
		}
	}

    public static long getDurationTime(Timestamp d1, Timestamp d2) {
        return (d2.getTime() - d1.getTime()) / 60000;
    }
	
	public static List<String> countAge(Date date) {
		List<String> agel = new ArrayList<String>();
		int[] ymdToNow = getYmdToNow(date);
		agel.add(ymdToNow[0] + "");
		agel.add(ymdToNow[1] + "");
		agel.add(ymdToNow[2] + "");
		return agel;
	}

	public static int[] getYmdToNow(Date date) {
		int y = 0, m = 0, d = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int now_year = cal.get(Calendar.YEAR);
		int now_month = cal.get(Calendar.MONTH);
		int now_day = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(date);
		int dob_year = cal.get(Calendar.YEAR);
		int dob_month = cal.get(Calendar.MONTH);
		int dob_day = cal.get(Calendar.DAY_OF_MONTH);
		int MaxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		y = now_year - dob_year; // 年差
		m = now_month - dob_month; // 月差
		d = now_day - dob_day; // 日差
		if (m < 0) {
			y = y - 1;
			m = 12 + m;
		} else if (m == 0 && d < 0) {
			y = y - 1;
			m = 12 - 1 + m;
			d = d + MaxDayOfMonth;
		}
		if (d < 0) {
			m = m - 1;
			d = d + MaxDayOfMonth;
		}

		return new int[] { y, m, d };
	}

	public static Date getDateWithoutTime() {
		return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getDateWithoutTime(Date date) {
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}