package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContentUp;
import com.farst.clockin.entity.ClockinReview;
import com.farst.clockin.mapper.ClockinReviewMapper;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡内容评论 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinReviewServiceImpl extends BasicServiceImpl<ClockinReviewMapper, ClockinReview> implements IClockinReviewService {

	@Autowired
	private ClockinReviewMapper clockinReviewMapper;
	
	@Override
	public List<Map<String, Object>> getMapReviewCountsByListContentId(List<Integer> listContentId) {
		return this.clockinReviewMapper.selectMapReviewCountsByListContentId(listContentId);
	}
	
	@Override
	public Long getReviewCountByContentId(Integer clockinContentId) {
		QueryWrapper<ClockinReview> queryWrapper = new QueryWrapper<ClockinReview>();
		queryWrapper.eq("clockin_content_id", clockinContentId).eq("status", 0);
		List<ClockinReview> list = this.list(queryWrapper);
		return ((list == null) ? (long)0 : (long)list.size());
	}

	@Override
	public IPage<ClockinReview> getPageClockinReviewByContentId(IPage<ClockinReview> page,Integer clockinContentId) {
		QueryWrapper<ClockinReview> queryWrapper = new QueryWrapper<ClockinReview>();
		queryWrapper.eq("clockin_content_id", clockinContentId).eq("status", 0).orderByDesc("id");
		return this.page(page, queryWrapper); 
	}

}
