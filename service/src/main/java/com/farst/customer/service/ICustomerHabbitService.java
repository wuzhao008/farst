package com.farst.customer.service;

import com.farst.customer.entity.CustomerHabbit;

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
public interface ICustomerHabbitService extends IBasicService<CustomerHabbit> {

	/**
	 * 根据客户ID获取用户习惯列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public List<CustomerHabbit> getListCustomerHabbit(Integer customerInfoId); 
	
	/**
	 * 根据习惯ID得到习惯信息列表
	 * 
	 * @param listHabbitId
	 * @return
	 */
	public List<CustomerHabbit> getListCustomerHabbitByListId(List<Integer> listHabbitId);
	
	/**
	 * 用户是否有设置习惯
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public boolean hasCustomerHabbit(Integer customerInfoId);
	
	/**
	 * 增加用户新习惯
	 * 
	 * @param customerHabbit
	 * 
	 */
	public void addCustomerHabbit(CustomerHabbit customerHabbit); 
}
