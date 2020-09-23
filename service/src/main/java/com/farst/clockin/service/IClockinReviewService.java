package com.farst.clockin.service;

import java.util.List;
import java.util.Map;

import com.farst.clockin.entity.ClockinReview;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡内容评论 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinReviewService extends IBasicService<ClockinReview> {
	
	/**
	 * 根据内容ID列表，得到每条对应的评论总数
	 * 
	 * @param listContentId
	 * @return
	 */
	public List<Map<String,Object>> getMapReviewCountsByListContentId(List<Integer> listContentId);
	
}
