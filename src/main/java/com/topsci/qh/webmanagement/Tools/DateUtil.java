package com.topsci.qh.webmanagement.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

/**
 * 日期处理类
 * 
 * @author CM-TOPSCI
 * 
 */
@Component
public class DateUtil {

	/**
	 * 获得当月月份，格式为yyMM
	 * 
	 * @return 当月月份，格式为yyMM
	 */
	public static String getNowMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
		String strDate = dateFormat.format(new Date());
		return strDate;
	}

	/**
	 * 获得当天时间，格式为yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getNow2() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(new Date());
		return strDate;
	}

	/**
	 * 获得当天时间，格式为yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getNow3() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = dateFormat.format(new Date());
		return strDate;
	}

	/**
	 * 获得当天时间，格式为yyyyMMddHHmmss
	 *
	 * @return String
	 */
	public static String getNow4() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(new Date());
		return strDate;
	}

	/**
	 * 获得当天时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getNowTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(new Date());
		return strDate;
	}

	/**
	 * 修改时间格式 原格式为yyyy-MM-dd HH:mm:ss.o 修改后 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getNormalTime(String oldDate) {
		String strDate = "";
		if (oldDate != null && !"".equals(oldDate)) {
			strDate = oldDate.substring(0, oldDate.length() - 2);
		}
		return strDate;
	}

	public static Date parseDate(String time,String format) throws Exception
	{
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		return format1.parse(time);
	}

	/**
	 * 比较两个时间字符串，是否相差时间内
	 * 
	 * @param src
	 *            源时间 YyyyMMddhhmmss
	 * @param dst
	 *            目的时间 YyyyMMddhhmmss
	 * @param call
	 *            相差时间单位 y-年，M-月，d-日 ,h-时，m-分，s-秒
	 * @param value
	 *            相差时间值
	 * @return 0-相差value，小于0-小于相差值，大于0-大于相差值
	 */
	public static int compareTo_Date(String src, String dst, char call,
			int value) {
		int year = Integer.parseInt(dst.substring(0, 4));
		int month = Integer.parseInt(dst.substring(4, 6));
		int day = Integer.parseInt(dst.substring(6, 8));
		int hour = Integer.parseInt(dst.substring(8, 10));
		int minute = Integer.parseInt(dst.substring(10, 12));
		int second = Integer.parseInt(dst.substring(12, 14));
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hour, minute, second);
		switch (call) {
		case 'y':
			cal.add(Calendar.YEAR, value);
			break;
		case 'M':
			cal.add(Calendar.MONTH, value);
			break;
		case 'd':
			cal.add(Calendar.DAY_OF_MONTH, value);
			break;
		case 'h':
			cal.add(Calendar.HOUR_OF_DAY, value);
			break;
		case 'm':
			cal.add(Calendar.MINUTE, value);
			break;
		case 's':
			cal.add(Calendar.SECOND, value);
			break;
		default:
			break;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = dateFormat.format(cal.getTime());
		return src.compareTo(strDate);
	}

	/**
	 * 将给定日期按 HHmm 转成 HH:mm
	 * 
	 * @param datetime
	 *            String 日期
	 * @return String 格式化后的日期
	 */
	public static String formatDateHHmm(String datetime) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("HHmm");
			Date date1 = df.parse(datetime);
			df = new SimpleDateFormat("HH:mm");
			datetime = df.format(date1);
		} catch (Exception ex) {
			datetime = "";
		}
		return datetime;
	}

	/**
	 * 将给定日期(yyyy-MM-dd HH:mm)转成 HH:mm
	 * 
	 * @param datetime
	 *            String 日期
	 * @return String 格式化后的日期
	 */
	public static String formatHHmm(String datetime) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = df.parse(datetime);
			df = new SimpleDateFormat("HH:mm");
			datetime = df.format(date);
		} catch (Exception ex) {
			datetime = "";
		}
		return datetime;
	}

	/**
	 * 将日期转换为给定格式的字符串(yyyy-MM-dd HH:mm:ss)形式
	 * 
	 * @param datetime
	 *            Date 日期
	 * @return String 格式化后的日期
	 */
	public static String DateToString(Date date) {
		String dateStr = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = df.format(date);
		} catch (Exception ex) {
			dateStr = "";
		}
		return dateStr;
	}

	/**
	 * 将给定日期 HH:mm 转换为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param day
	 *            String yyyy-MM-dd格式的日期
	 * @param time
	 *            String HH:mm 格式的时间对（01:35+1格式的需作处理）
	 * @return String 格式化后的日期
	 */
	public static String HHmmTodDate(String day, String time) {
		String datetime = null;
		try {
			if (day != null && !"".equals(day) && time != null
					&& !"".equals(time)) {
				if (time.length() > 5) {
					datetime = day + " " + time.substring(0, 5) + ":00";
					int i = Integer.parseInt(time.substring(6, 7));
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = df.parse(datetime);
					long l = date.getTime() + i * 24 * 60 * 60 * 1000;
					datetime = df.format(l);
				} else {
					datetime = day + " " + time + ":00";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datetime;
	}

	/**
	 * 将给定日期 HH:mm 转换为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param day
	 *            String yyyy-MM-dd格式的日期
	 * @param time
	 *            String HHmm 格式的时间
	 * @return String 格式化后的日期
	 */
	public static String getNomalTime(String day, String time) {
		String datetime = null;
		if (day != null && !"".equals(day) && time != null && !"".equals(time)) {
			datetime = day + " " + formatDateHHmm(time) + ":00";
		}
		return datetime;
	}

	/**
	 * 将给定源时间做加或者减去指定时间，返回yyyy-MM-dd HH:mm:ss格式的目地时间
	 * 
	 * @param time
	 *            格式为： yyyy-MM-dd HH:mm:ss
	 * @param value
	 *            相差时间值 '+'表示推后 '-'表示提前
	 * @param flg
	 *            相差时间单位 y-年，M-月，d-日 ,h-时，m-分，s-秒
	 * @return 返回yyyy-MM-dd HH:mm:ss格式的目地时间
	 */
	public static String getAimTime(String time, int value, char flg) {

		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(5, 7));
		int day = Integer.parseInt(time.substring(8, 10));
		int hour = Integer.parseInt(time.substring(11, 13));
		int minute = Integer.parseInt(time.substring(14, 16));
		int second = Integer.parseInt(time.substring(17, 19));
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hour, minute, second);
		switch (flg) {
		case 'y':
			cal.add(Calendar.YEAR, value);
			break;
		case 'M':
			cal.add(Calendar.MONTH, value);
			break;
		case 'd':
			cal.add(Calendar.DAY_OF_MONTH, value);
			break;
		case 'h':
			cal.add(Calendar.HOUR_OF_DAY, value);
			break;
		case 'm':
			cal.add(Calendar.MINUTE, value);
			break;
		case 's':
			cal.add(Calendar.SECOND, value);
			break;
		default:
			break;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 将给定时间与当前时间比差值
	 * 
	 * @param time
	 *            String yyyy-MM-dd HH:mm:ss
	 * @return String 时间差 格式：1小时31分
	 */
	public static String getSubTime(String time) {
		StringBuffer rest = new StringBuffer();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Date date = df.parse(time);
			long l = now.getTime() - date.getTime();
			if (l > 0) {
				long day = l / (24 * 60 * 60 * 1000);
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);

				if (hour != 0) {
					rest.append(hour + "小时");
				}
				if (min != 0) {
					rest.append(min + "分");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rest.toString();
	}

	/**
	 * 将yyyy-MM-dd格式的日期转换为Date型
	 * 
	 * @param strDate
	 *            yyyy-MM-dd格式的字符串
	 * @return date
	 */
	public static Date getDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将Date转换为yyyy-MM-dd格式的日期
	 * 
	 * @param date
	 * @return strDate yyyy-MM-dd格式的字符串
	 */
	public static String getStringDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = null;
		try {
			strDate = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * 将Date转换为DDMMMYY格式的日期
	 * 
	 * @param date
	 * @return strDate DDMMMYY格式的字符串
	 */
	public static String getDDMMMYYDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("DDMMMYY");
		String strDate = null;
		try {
			strDate = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 
	 * @param dateStr 字符串时间
	 * @param form	字符串时间的格式 例如：yyyy-MM-dd等
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar stringtoXML(String dateStr, String form) {
		XMLGregorianCalendar xmlCalendar = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(form);
			Date date =  format.parse(dateStr); 
			DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			xmlCalendar = dataTypeFactory.newXMLGregorianCalendar(gc);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return xmlCalendar;
	}
	
}
