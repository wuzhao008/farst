package com.farst.clockin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
	 * 得到内容对应的评论分页信息
	 * 
	 * @param clockinContentId
	 * @return
	 */
	public IPage<ClockinReview> getPageClockinReviewByContentId(IPage<ClockinReview> page,Integer clockinContentId);
	
	/**
	 * 根据内容ID列表，得到每条对应的评论总数
	 * 
	 * @param listContentId
	 * @return
	 */
	public List<Map<String,Object>> getMapReviewCountsByListContentId(List<Integer> listContentId);
	
	/**
	 * 查询内容对应评论总数
	 * 
	 * @param clockinContentId
	 * @return
	 */
	public Long getReviewCountByContentId(Integer clockinContentId);
	
	/**
	 * 审核拒绝日志评论
	 * 
	 * @param reviewId
	 */
	public void checkRejectClockinReview(Integer reviewId);
	
}
