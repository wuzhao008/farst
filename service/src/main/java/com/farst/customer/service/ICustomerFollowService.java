package com.farst.customer.service;

import com.farst.customer.entity.CustomerFollow;
import com.farst.customer.entity.CustomerInfo;
import com.baomidou.mybatisplus.core.metadata.IPage; 
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户关注 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerFollowService extends IBasicService<CustomerFollow> {
	/**
	 * 得到用户的关注用户分页信息
	 * 
	 * @param customerInfoId
	 * @return
	 */
	IPage<CustomerInfo> getPageMyFollow(IPage<CustomerInfo> page,Integer customerInfoId);
	/**
	 * 得到用户的粉丝分页信息
	 * 
	 * @param customerInfoId
	 * @return
	 */
	IPage<CustomerInfo> getPageMyFans(IPage<CustomerInfo> page,Integer customerInfoId);
	
}
