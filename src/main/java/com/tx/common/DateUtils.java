package com.tx.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public final class DateUtils {

	public DateUtils() {
	}

	public static String toDateString(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return "";
		}
	}
	/**
	 * @param date to String: 30-12-2011 10:10:10
	 * @param format "DD-MM-YYYY hh:mm:ss"
	 * @return String
	 */
	
	public static String toDateString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(JConstants.DATE_FORMAT);
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return "";
		}
	}
	/**
	 * date to String By Format
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toDateString(Date date,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return "";
		}
	}
	/**
	 * @param string to Date: 30-12-2011 10:10:10
	 * @param format "DD-MM-YYYY hh:mm:ss"
	 * @return Date
	 */
	
	public static Date toDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(JConstants.DATE_FORMAT);
		java.util.Date date = null;
		try {
			date = dateFormat.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return null;
		}
	} 
	
	/**
	 * Convert DateString by format
	 * @param dateString
	 * @param format
	 * @return Date
	 */
	public static Date toDate(String dateString, String format) {
		// Remove offset
		if(dateString.contains(".") && dateString.endsWith("Z")){
			dateString= dateString.substring(0,dateString.indexOf(".")) +"Z";
		}
		//2013-11-23T13:59
		if(dateString.length()==15){
			dateString=dateString+":00Z";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return null;
		}
	} 

	public static Date toDateGMT(String dateString, String format) {
		if(dateString.contains(".") && dateString.endsWith("Z")){
			dateString= dateString.substring(0,dateString.indexOf(".")) +"Z";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return null;
		}
	}
	 
	//dd-MM-yyyy
	public static String addStartTime(String from) {
			if (from!=null && from.trim().length()>=10) {
				from=from.substring(0,10) + " 00:00:00";
			}
			return from;
		}
	//dd-MM-yyyy
	public static String addEndTime(String to) {
		if (to!=null && to.trim().length()>=10) {
			to=to.substring(0,10) + " 23:59:59";
		}
		return to;
	}
	
	/**
	 * GMT format ago day
	 * @param ago
	 * @return Date
	 */
	public static Date getDateGMT(int ago){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getNowGMT());
		calendar.add(Calendar.DATE, ago);
		return   calendar.getTime();
		
	}

	/**
	 * Get GMT now
	 * @return
	 */
	public static Date getNowGMT() {
		try {
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat(JConstants.TIME_ZONE);
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));// -06:00 and +7
			// Local time zone
			SimpleDateFormat dateFormatLocal = new SimpleDateFormat(JConstants.TIME_ZONE);
			// Time in GMT
			return dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Compare Date is today 
	 * @param dateTime
	 * @return
	 */
	public static boolean isToday(Date dateTime) {
		return toDateString(getNowGMT()).substring(0,10).equals(toDateString(dateTime).substring(0,10));
	}
	
	/**
	 * Get before minutes from specified date
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getDateBefore(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, -second);
		return calendar.getTime();
	}
	
	public static String toNow() {
		return toDateString(new Date(),JConstants.yyMMddHHmmss);
	}
	
	public static Date fromStringToDateForFormatter(String date, String dateTimePattern) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimePattern);
		return dateFormat.parse(date);
	}
}
