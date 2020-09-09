/**
 * Project: sp-common
 * 
 * File Created at 2018-10-11
 * $Id$
 * 
 * Copyright 2018 uup.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * uup Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with uup.com.
 */
package com.farst.common.utils;
 
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TODO Comment of DateUtils
 * 
 * @author MichalWoo
 * @version $Id: DateUtils.java 2012-9-9 下午04:22:17 $
 */
public class DateUtils {
    /**yyyy-MM-dd hh:mm:ss*/
    public final static String FULL_PATTERN = "yyyy-MM-dd hh:mm:ss";
    /**yyyy-MM-dd HH:mm:ss*/
    public final static String FULL_PATTERN_24 = "yyyy-MM-dd HH:mm:ss";
    /**yyyy-MM-dd hh:mm*/
    public final static String DATEHHMM_PATTERN = "yyyy-MM-dd hh:mm";
    /**yyyy-MM-dd HH:mm*/
    public final static String DATEHHMM_PATTERN_24 = "yyyy-MM-dd HH:mm";
    /**yyyy-MM-dd*/
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**yyyyMMdd*/
    public final static String DATE_PATTERN_2 = "yyyyMMdd";
    /**yyyyMMdd*/
    public final static String DATE_PATTERN_3 = "yyyy.MM.dd";
    /**hh:mm:ss*/
    public final static String TIME_PATTERN = "hh:mm:ss";
    /**HH:mm:ss*/
    public final static String TIME_PATTERN_24 = "HH:mm:ss";
    /**HH:mm*/
    public final static String TIME_PATTERN_24_2 = "HH:mm";
    /**yyyy年MM月dd日H时mm分ss秒*/
    public final static String FULL_PATTERN_ZH = "yyyy年MM月dd日H时mm分ss秒";
    /**yyyyMMddHHmmssSSS*/
    public final static String FULL_PATTERN_SSS = "yyyyMMddHHmmssSSS";
    /**yyyyMMddHHmmss*/
    public final static String TIME_FORMAT = "yyyyMMddHHmmss";
    /**yyyy/MM/dd HH:mm:ss*/
    public static final String LINUX_FORMAT = "yyyy/MM/dd HH:mm:ss";
    /**MM/dd/yyyy*/
    public final static String DATE_PATTERN_US = "MM/dd/yyyy";
    
    public final static String[] MONTH_DE_GE = new String[]{"Jan","Feb","Mar","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez"};
    
    /**
     * 时间字符串转化成 Date
     * @param dateStr 时间字符串
     * @param dateFormat 时间字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return Date  返回日期
     */
    public static Date toDate(String dateStr, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        try {
            return df.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 时间字符串转化成 Calendar
     * @param dateStr 时间字符串
     * @param dateFormat 时间字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return Calendar  返回日期
     */
    public static Calendar toCalendar(String dateStr, String dateFormat) {
        Date temp = toDate(dateStr, dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        return cal;
    }
    
    /**
     * 时间转化成字符串
     * @param date 时间，null为当前时间
     * @param dateFormat 时间字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return String  返回日期字符串
     */
    public static String DatetoString(Date date, String dateFormat) {
        date = date == null ? new Date() : date;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
    
    /**
     * 时间转化成字符串
     * @param date
     * @param dateFormat 时间字符串格式，如：MMM dd yyyy
     * @param locale
     * @return
     */
    public static String DateToString(Date date, String dateFormat, Locale locale) {
        date = date == null ? new Date() : date;
        locale =  locale == null ? Locale.ENGLISH : locale;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
        return sdf.format(date);
    }
    
    /**
     * 时间计算
     * @param date 时间，null为当前时间
     * @param field 对哪个属性进行计算；1：年；2：月；3：日；4：小时；5：分钟
     * @param amount 要增加的值
     * @return Date  返回日期
     */
    public static Date addTime(Date date, int field, int amount) {
        date = date == null ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(field == 1){
            cal.add(Calendar.YEAR, amount);
        }else if(field == 2){
            cal.add(Calendar.MONTH, amount);
        }else if(field == 3){
            cal.add(Calendar.DATE, amount);
        }else if(field == 4){
            cal.add(Calendar.HOUR, amount);
        }else if(field == 5){
            cal.add(Calendar.MINUTE, amount);
        }else if(field == 6) {
        	cal.add(Calendar.SECOND, amount);
        }
        return cal.getTime();
    }
    
    /**
     * 取得特殊时间
     * @param date 时间，null为当前时间
     * @param field 特殊标志；1：本年第一个月；2：本年最后一个月；3：本月第一天；4：本月最后一天
     * @return Date  返回日期
     */
    public static Date getSpecialDate(Date date, int field) {
        date = date == null ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(field == 1){//取得本年第一个月
            cal.set(Calendar.MONTH, 0);
        }else if(field == 2){//取得本年最后一个月
            cal.set(Calendar.MONTH, 11);
        }else if(field == 3){//取得本月第一天
            cal.set(Calendar.DATE, 1);
        }else if(field == 4){//取得本月最后一天
            cal.set(Calendar.DATE, 1);
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DATE, -1);
        }
        return cal.getTime();
    }
    
    /**
     * 两个时间之间的天数
     * @param date1  起始时间
     * @param date2  结束时间
     * @return long   两个时间间的天数
     */
    public static long daysBetweenTwoDate(Date date1, Date date2) {
        long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }
    
    /**
     * 两个时间之间的天数
     * @param date1  起始时间
     * @param date2  结束时间
     * @return long   两个时间间的天数
     */
    public static long daysBetweenSecond(Date date1, Date date2) {
        long second = (date2.getTime() - date1.getTime()) / (1000);
        return second;
    }
    
    /**
     * 
     * 获得当前时间与某个时间的差值           单位：毫秒
     * 
     * */
    public static long timeDifference(Date date){
        return new Date().getTime() - date.getTime();
    }
    
    /**
     * 把当前时间转换为linux下时间戳，精确到秒
     */
    public static long now2timestamp() {
        return new Date().getTime() / 1000;
    }
    
    /**
     * 把当前时间转换为linux下时间戳，精确到秒
     */
    public static long date2timestamp(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 把linux下时间戳转换成时间，精确到秒
     */
    public static Date timestamp2Date(long timestamp) {
        return new Date(timestamp * 1000L);
    }
    
    /**
     * 把linux下时间戳转换成时间，精确到秒，并按指定格式进行格式化
     */
    public static String timestamp2Date(long timestamp, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date(timestamp * 1000L));
    }
    
    /**
     * 把linux下时间戳转换成时间，精确到秒，并按指定格式进行格式化
     * @param timestamp
     * @param dateFormat
     * @param locale
     * @return
     */
    public static String timestamp2Date(long timestamp, String dateFormat, Locale locale) {
        locale = locale == null ? Locale.ENGLISH : locale;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat, locale);
        return df.format(new Date(timestamp * 1000L));
    } 
    
    /**
     * 将自然日转换成工作日
     * @param naturalDay   自然日
     * @return
     */
    public static Integer naturalDay2WorkingDay(Integer naturalDay) {
        Integer result = null;
        int remainder = naturalDay % 7;
        //如果余数为6则用(6-1)
        if (remainder == 6)
            remainder = 5;
        result = 5 * (naturalDay / 7) + remainder;
        return result;
    }
    

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		return new Date();
	}
	
	/**
	 * 按照年月日组成路径
	 * 
	 * @return
	 */
	public static String getFilePathUseTime(String strSeparator) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate = sdf.format(date);
		if (strSeparator == null)
			return File.separator + strDate.substring(0, 4) + File.separator
					+ File.separator + strDate.substring(4, 6) + File.separator
					+ File.separator + strDate.substring(6, 8);
		else
			return  strSeparator + strDate.substring(0, 4)
					 + strSeparator + strDate.substring(4, 6)
					 + strSeparator + strDate.substring(6, 8);
	}
	
	/**
	 * 获取某一天的起始时间
	 * @param date
	 * @param index
	 * @return
	 */
	public static Date getDay(Date date, int index) {
		Calendar c = Calendar.getInstance();
		date = date == null ? new Date() : date;
		
		c.setTime(date); 
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        c.add(Calendar.DATE, index);
        return c.getTime();
	}
	
	public static int getWeekOfDate(Date dt) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
	 * 返回当前月份的第几天
	 * @param dt
	 * @return
	 */
	public static int getDayOfMonth(Date dt){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(cal.DAY_OF_MONTH);
    }
	
	/**
	 * 获取当前周几
	 * @return
	 */
	public static String getCurWeek(){
		return getWeek(new Date());
	}

	/**
	 * 获取指定日期是周几
	 * @return
	 */
	public static String getWeek(Date date) {
		String week = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String weekStr="星期";
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			week = "日";
		} else if (weekday == 2) {
			week = "一";
		} else if (weekday == 3) {
			week = "二";
		} else if (weekday == 4) {
			week = "三";
		} else if (weekday == 5) {
			week = "四";
		} else if (weekday == 6) {
			week = "五";
		} else if (weekday == 7) {
			week = "六";
		}
		return weekStr+week;
	}
	
	/**
	 * 获取指定日期是周几
	 * @return
	 */
	public static String getWeek2(Date date) {
		String week = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String weekStr="周";
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			week = "日";
		} else if (weekday == 2) {
			week = "一";
		} else if (weekday == 3) {
			week = "二";
		} else if (weekday == 4) {
			week = "三";
		} else if (weekday == 5) {
			week = "四";
		} else if (weekday == 6) {
			week = "五";
		} else if (weekday == 7) {
			week = "六";
		}
		
		week = weekStr+week;
		String dateStr = DatetoString(date,DATE_PATTERN);
		String curDateStr = DatetoString(new Date(),DATE_PATTERN);
		if(dateStr.equals(curDateStr)){
			week = "今天";
		}else if(DatetoString(addTime(new Date(),3,1),DATE_PATTERN).equals(dateStr)){
			week = "明天";
		}
		
		return week;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getWeek2(toDate("2020-02-23",DATE_PATTERN)));		
		System.out.println(DateUtils.DatetoString(DateUtils.toDate("2020-02-23","MM.dd"),"MM.dd"));
		System.out.println(DateUtils.DatetoString(DateUtils.toDate("2020-02-24","MM.dd"),"MM.dd"));
	}

}
