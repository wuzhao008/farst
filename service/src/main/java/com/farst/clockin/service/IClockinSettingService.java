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
	 * 根据用户id和标签id得到最新的习惯设置
	 *  
	 * @param customerLabelId
	 * @return
	 */
	public ClockinSetting getLatestClockingSettingBy(Integer customerLabelId);
	
	/**
	 * 修改规则的弹出日志方式
	 * 
	 * @param customerLabelId
	 * @param popupLog
	 */
	public void updateLatestPopupLog(Integer customerLabelId,Integer popupLog);
}
