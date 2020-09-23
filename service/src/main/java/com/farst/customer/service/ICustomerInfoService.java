package com.farst.customer.service;

import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.vo.TokenCustVo;

import java.util.List;

import com.farst.common.exception.ServiceException;
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
	 * 根据tokenid解密用户信息
	 * 
	 * @param jwt
	 * @return
	 * @throws ServiceException
	 */
	public TokenCustVo getTokenCustVo(String jwt) throws ServiceException;
	
	/**
	 * 根据手机号码获取客户信息
	 * 
	 * @param phoneNumber 手机号码
	 * @return 客户信息
	 */
	public CustomerInfo getCustomerInfoByPhoneNumber(String phoneNumber);
	
	/**
	 * 根据客户ID列表获取客户信息列表
	 * 
	 * @param listCustId
	 * @return
	 */
	public List<CustomerInfo> getListCustomerInfoBy(List<Integer> listCustId);

}
