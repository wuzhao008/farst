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

import java.util.Random;

/**
 * TODO Comment of RandomUtils
 * 
 * @author MichalWoo
 * @version $Id: Md5Utils.java 2012-9-9 下午04:21:24 $
 */
public class RandomUtils {
	public static Integer nextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;

	}

	public static Integer nextInt(final int value) {
		Random rand = new Random();
		return rand.nextInt(value);
	}

	public static String getRandomString(int length){
	     String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	
	public static void main(String[] args) {
		//System.out.println(RandomUtils.nextInt(100000, 999999));
		System.out.println(RandomUtils.getRandomString(12));

	}
}
