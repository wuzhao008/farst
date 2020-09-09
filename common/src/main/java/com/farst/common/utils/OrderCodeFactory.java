package com.farst.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单编码码生成器，生成32位数字编码，
 * @生成规则 1位单号类型+17位时间戳+14位(用户id加密&随机数)
 * Date:2017年9月8日上午10:05:19
 */
public class OrderCodeFactory {
    /** 订单类别头 */
    private static final String ORDER_CODE = "";
    /** 退货类别头 */
    private static final String RETURN_ORDER = "";
    /** 退款类别头 */
    private static final String REFUND_ORDER = "";
    /** 未付款重新支付别头 */
    private static final String AGAIN_ORDER = "";
    /** 优惠券批次号头 **/
    private static final String BATCH_NO = "";
    /** 随机编码 */
    private static final int[] random = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};
    /** 用户id和随机数总长度 */
    private static final int maxLength = 6;            
    
    /**
     * 更具id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Integer id) {
        String idStr = id.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1 ; i >= 0; i--) {
            idsbs.append(random[idStr.charAt(i)-'0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }
     
    /**
     * 生成时间戳
     */
    private static String getDateTime(){
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }
    
    /**
     * 生成固定长度随机码
     * @param n    长度
     */
    private static long getRandom(long n) {
        long min = 1,max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
        return rangeLong;
    }
    
    /**
     * 生成不带类别标头的编码
     * @param userId
     */
    private static synchronized String getCode(Integer userId){
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }
    
    /**
     * 生成订单验证码
     * @return
     */
    public static String getOrderVerificationCode() {		
		// 0 代表前面补充0
		// 7 代表长度为7
		// d 代表参数为正数型
		return String.format("%08d", getRandom(8));
	}
    
    /**
     * 生成提现单号编码
     * @param userId
     */
    public static String getWithDrowCode(){
        return ORDER_CODE + getCode(1);
    }
    
    /**
     * 生成订单单号编码
     * @param userId
     */
    public static String getOrderCode(){
        return ORDER_CODE + getCode(0);
    }
    
    /**
     * 生成退货单号编码
     * @param userId
     */
    public static String getReturnCode(Integer userId){
        return RETURN_ORDER + getCode(userId);
    }
    
    /**
     * 生成退款单号编码
     * @param userId
     */
    public static String getRefundCode(Integer userId){
        return REFUND_ORDER + getCode(userId);
    }
    
    /**
     * 未付款重新支付
     * @param userId
     */
    public static String getAgainCode(Integer userId){
        return AGAIN_ORDER + getCode(userId);
    }
    
    /**
     * 优惠券批次号
     * @return
     */
    public static String getCouponBatchNo(){
        return BATCH_NO + getCode(9);
    }
    
    
    public static void main(String[] args) {    	
    	System.out.println("生成订单验证码:" + getOrderVerificationCode());
    	System.out.println("生成不带类别标头的编码:" + getCode(1));		
		System.out.println("生成订单单号编码:" + getOrderCode());		
		System.out.println("生成订单单号编码:" + getReturnCode(123));	
		System.out.println("生成退货单号编码:" + getRefundCode(123));
		System.out.println("未付款重新支付:" + getAgainCode(123));
	}
    
}

