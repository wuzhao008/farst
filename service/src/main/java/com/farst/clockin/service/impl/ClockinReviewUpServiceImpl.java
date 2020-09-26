package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; 
import com.farst.clockin.entity.ClockinReviewUp;
import com.farst.clockin.mapper.ClockinReviewUpMapper;
import com.farst.clockin.service.IClockinReviewUpService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡评论顶UP 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinReviewUpServiceImpl extends BasicServiceImpl<ClockinReviewUpMapper, ClockinReviewUp> implements IClockinReviewUpService {

	@Autowired
	private ClockinReviewUpMapper clockinReviewUpMapper;
	
	@Override
	public List<Map<String,Object>> getMapReviewUpsByListReviewId(List<Integer> listReviewId){
		return this.clockinReviewUpMapper.selectMapReviewUpsByListReviewId(listReviewId);
	}

	@Override
	public List<Integer> getMyUpReviewIds(List<Integer> listReviewId, Integer customerInfoId) {
		QueryWrapper<ClockinReviewUp> queryWrapper = new QueryWrapper<ClockinReviewUp>();
		queryWrapper.eq("customer_info_id", customerInfoId).in("clockin_review_id", listReviewId).eq("status", 0);
		List<ClockinReviewUp> listCrup = this.list(queryWrapper);
		List<Integer> listCrupId = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(listCrup)) {
			listCrup.forEach(cru->{
				listCrupId.add(cru.getClockinReviewId());
			});
		}
		return listCrupId;
	}

	@Override
	public ClockinReviewUp getClockinReviewRecord(Integer customerInfoId, Integer clockinReviewId) {
		QueryWrapper<ClockinReviewUp> queryWrapper = new QueryWrapper<ClockinReviewUp>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("clockin_review_id", clockinReviewId);
		return this.getOne(queryWrapper);
	}
}
