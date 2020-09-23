package com.farst.clockin.service.impl;

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

}
