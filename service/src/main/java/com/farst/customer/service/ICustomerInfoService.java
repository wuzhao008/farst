package com.farst.customer.service;

import com.farst.customer.entity.CustomerInfo;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerInfoService extends IBasicService<CustomerInfo> {

	/**
	 * 根据手机号码获取客户信息
	 * 
	 * @param phoneNumber 手机号码
	 * @return 客户信息
	 */
	public CustomerInfo getCustomerInfoByPhoneNumber(String phoneNumber);

}
