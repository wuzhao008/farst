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
	public IPage<CustomerInfo> getPageMyFollow(IPage<CustomerInfo> page,Integer customerInfoId);
	
	/**
	 * 得到关注的人总数
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public Integer getCountMyFollow(Integer customerInfoId);
	
	/**
	 * 得到用户的粉丝分页信息
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public IPage<CustomerInfo> getPageMyFans(IPage<CustomerInfo> page,Integer customerInfoId);

	/**
	 * 得到粉丝总数
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public Integer getCountMyFans(Integer customerInfoId);

	/**
	 * 根据用户ID和关注的用户ID获的关注记录
	 * 
	 * @param customerInfoId
	 * 
	 * @param followCustomerInfoId
	 * @return
	 */
	public CustomerFollow getCustomerFollowRecord(Integer customerInfoId,Integer followCustomerInfoId);
	
	/**
	 * 关注用户
	 * 
	 * @param customerInfoId	当前用户id
	 * @param followCustomerInfoId 	关注用户id
	 */
	public void follow(Integer customerInfoId,Integer followCustomerInfoId);

	/**
	 * 关注用户
	 * 
	 * @param customerInfoId	当前用户id
	 * @param followCustomerInfoId 	取关用户id
	 */
	public void unfollow(Integer customerInfoId,Integer followCustomerInfoId);
	
}
