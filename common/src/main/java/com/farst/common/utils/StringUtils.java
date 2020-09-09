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

import java.math.BigDecimal;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO Comment of StringUtils
 * 
 * @author MichalWoo
 * @version $Id: StringUtils.java 2012-9-9 下午04:20:22 $
 */
public class StringUtils {

	/**
	 * 对象转换为字符串类型
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		return (obj == null || obj.equals("null")) ? "" : obj.toString();
	}

	/**
	 * 将字符传null转换成空串
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToBlank(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 将空串转换成null
	 * 
	 * @param str
	 * @return
	 */
	public static String blankToNull(String str) {
		return "".equals(str) ? null : str;
	}

	/**
	 * 是否为空（为空返回true，不为空返回false）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		else
			return org.apache.commons.lang3.StringUtils.isEmpty(str.trim());
	}

	/**
	 * 是否不为空(不为空返回true，为空返回false)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null)
			return false;
		else
			return org.apache.commons.lang3.StringUtils.isNotEmpty(str.trim());
	}

	/**
	 * 将字符串类型转换为Integer类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Integer string2Integer(String str) {
		return StringUtils.isEmpty(str) ? null : Integer.valueOf(str);
	}

	/**
	 * 将字符串类型转换为Long类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Long string2Long(String str) {
		return StringUtils.isEmpty(str) ? null : Long.valueOf(str);
	}

	/**
	 * 将字符串类型转换为Boolean类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Boolean string2Boolean(String str) {
		return StringUtils.isEmpty(str) ? null : Boolean.valueOf(str);
	}

	/**
	 * 将字符串类型转换为Byte类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Byte string2Byte(String str) {
		return StringUtils.isEmpty(str) ? null : Byte.valueOf(str);
	}

	/**
	 * 将字符串类型转换为Float类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Float string2Float(String str) {
		return StringUtils.isEmpty(str) ? null : Float.valueOf(str);
	}

	/**
	 * 将字符串类型转换为Double类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static Double string2Double(String str) {
		return StringUtils.isEmpty(str) ? null : Double.valueOf(str);
	}

	/**
	 * 将字符串类型转换为BigDecimal类型,如果输入为空值或空串，则返回null
	 * 
	 * @param str
	 */
	public static BigDecimal string2BigDecimal(String str) {
		return StringUtils.isEmpty(str) ? null : BigDecimal.valueOf(Double.valueOf(str));
	}

	/**
	 * 转换成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCase(String str) {
		return str == null ? null : str.toUpperCase();
	}

	/**
	 * 转换成小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerCase(String str) {
		return str == null ? null : str.toLowerCase();
	}

	/**
	 * 当不足指定长度时，前面填充对应字符
	 * 
	 * @param oldString
	 *            原串
	 * @param length
	 *            填充后长度
	 * @param fillChar
	 *            填充字符
	 * @return
	 */
	public static String fillString(String oldString, int length, String fillChar) {
		String newString = "";
		if (oldString == null)
			return null;
		int oldStringLength = oldString.length();
		if (oldString.length() >= length)
			return oldString;
		String fillString = "";
		for (int i = 0; i < length - oldStringLength; i++) {
			fillString = fillString + fillChar;
		}
		newString = fillString + oldString;
		return newString;
	}

	/**
	 * 两个串是否相等
	 * 
	 * @param arg
	 */
	public static boolean equal(String str1, String str2) {
		return nullToBlank(str1).equals(nullToBlank(str2));
	}

	/**
	 * 去掉所有的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String removeAllBlank(String str) {
		if (str != null) {
			str = str.replaceAll(" ", "");
			return str;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param arg
	 */
	public static String reSetStr(String param) {
		param = param.trim();
		param = param.replaceAll("&", "-");
		param = param.replaceAll(" ", "-");
		param = param.replace("/", "-");
		param = param.replace("[", "");
		param = param.replace("]", "");
		// 其余除去-_外全部设置成空串
		param = param.replaceAll("[`~!@#$%^*()+='\";:?/.><,！@￥……（）【】、|？》《]", "");
		return param;
	}

	public static void main(String[] arg) {
		System.out.println(reSetStr(" woman's&clothing heihei >< adf'\":;asdf》《' "));
	}

	public static String getRandomStrByLen(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * IpUtils工具类方法 获取真实的ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

}
