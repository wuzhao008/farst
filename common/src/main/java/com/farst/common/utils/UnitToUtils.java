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

/**
 * 
 * UnitToUtils
 * 
 * @author rbg
 * @version $Id: UnitToUtils.java 2012-10-27 下午06:39:26 $
 */
public class UnitToUtils {
	/**
	 * 单位转换
	 * @param cmParam 原始值
	 * @param unit 类型(此处暂时固定为cm和kg,针对cm转换成inch,kg转换成pound)
	 * @return
	 */
    public static String unitTo(BigDecimal cmParam,String unit) {
        BigDecimal zero =new  BigDecimal(0);
        if(cmParam.compareTo(zero) == 0){
            return "";
        }
        Double tempUnit = null;
        if("cm".equals(unit))
        	tempUnit = Double.valueOf(0.39370078740157);
        if("kg".equals(unit))
        	tempUnit = Double.valueOf(2.2046);
        Double toUnit = cmParam.doubleValue() * tempUnit;
        Integer intUnit =   toUnit.intValue();

        String unitStr = intUnit.toString();

        Double toUnitPoint = toUnit - intUnit;
        if ((toUnitPoint > 0.0) && (toUnitPoint <= 0.25)) {
            unitStr += " <font size=1>1/4</font>";
        } else if ((toUnitPoint > 0.25) && (toUnitPoint <= 0.5)) {
            unitStr += " <font size=1>1/2</font>";
        } else if ((toUnitPoint > 0.5) && (toUnitPoint <= 0.75)) {
            unitStr += " <font size=1>3/4</font>";
        } else {
            intUnit += 1;
            unitStr = intUnit.toString();
        }
        return unitStr;
    } 
}
