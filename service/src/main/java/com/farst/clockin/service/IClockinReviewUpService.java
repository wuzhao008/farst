package com.farst.clockin.service;

import java.util.List;
import java.util.Map;

import com.farst.clockin.entity.ClockinReviewUp;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡评论顶UP 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinReviewUpService extends IBasicService<ClockinReviewUp> {

	/**
	 * 根据评论ID列表，得到每条评论对应的UP数
	 * 
	 * @param listReviewId
	 * @return
	 */
	public List<Map<String,Object>> getMapReviewUpsByListReviewId(List<Integer> listReviewId);
	
	/**
	 * 在这些评论中，哪些评论我赞过
	 * 
	 * @param listReviewId
	 * @param customerInfoId
	 * @return
	 */
	public List<Integer> getMyUpReviewIds(List<Integer> listReviewId,Integer customerInfoId);
}
