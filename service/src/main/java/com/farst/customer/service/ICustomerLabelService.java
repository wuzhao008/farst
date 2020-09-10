package com.farst.customer.service;

import com.farst.customer.entity.CustomerLabel;

import java.util.List;

import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户打卡标签 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerLabelService extends IBasicService<CustomerLabel> {

	/**
	 * 根据客户ID获取用户标签列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public List<CustomerLabel> getListCustomerLabel(Integer customerInfoId);
	
	/**
	 * 查询用户ID对应是否有用户标签
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public boolean hasCustomerLabel(Integer customerInfoId);
}
