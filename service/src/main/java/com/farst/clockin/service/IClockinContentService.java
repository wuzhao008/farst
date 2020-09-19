package com.farst.clockin.service;

import java.util.Date;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.vo.TodayClockinVo;
import com.farst.common.service.IBasicService;
import com.farst.customer.entity.CustomerInfo;

/**
 * <p>
 * 打卡文字内容 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinContentService extends IBasicService<ClockinContent> {

	/**
	 * 查询当天打卡列表信息
	 * 
	 * @param page
	 * @param customerInfoId 当前用户ID 
	 * @return
	 */
	public IPage<TodayClockinVo> getPageTodayClockinVo(IPage<TodayClockinVo> page,Integer customerInfoId);
}
