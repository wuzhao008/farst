package com.farst.customer.service;

import com.farst.common.service.IBasicService;
import com.farst.customer.entity.CustomerHabbitSetting;

/**
 * <p>
 * 打卡设置 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerHabbitSettingService extends IBasicService<CustomerHabbitSetting> {

	/**
	 * 根据用户习惯ID得到最新的习惯设置
	 *  
	 * @param customerHabbitId
	 * @return
	 */
	public CustomerHabbitSetting getLatestHabbitSettingBy(Integer customerHabbitId); 
}
