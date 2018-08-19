package com.tx.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public final class DateUtils {

	public DateUtils() {
	}

	
	/**
	 * @param string to Date: 30-12-2011 10:10:10
	 * @param format "HH:mm:ss dd/MM/yyyy"
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
	 * Get before seconds from specified date
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getDateAfter(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}
	
}
