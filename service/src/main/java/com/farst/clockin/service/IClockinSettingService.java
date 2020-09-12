package com.farst.clockin.service;

import com.farst.clockin.entity.ClockinSetting;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡设置 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinSettingService extends IBasicService<ClockinSetting> {

	/**
	 * 根据用户id和标签id得到习惯设置
	 * 
	 * @param customerInfoId
	 * @param labelId
	 * @return
	 */
	public ClockinSetting getClockingSettingBy(Integer customerInfoId,Integer labelId);
}
