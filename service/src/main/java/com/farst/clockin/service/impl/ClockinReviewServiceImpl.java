package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage; 
import com.farst.clockin.entity.ClockinReview;
import com.farst.clockin.mapper.ClockinReviewMapper;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.common.service.impl.BasicServiceImpl;
import com.farst.customer.entity.CustomerMessage;
import com.farst.customer.service.ICustomerMessageService;

import java.util.Date;
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
	
	@Autowired
	private ICustomerMessageService customerMessageService;
	
	@Override
	public List<Map<String, Object>> getMapReviewCountsByListContentId(List<Integer> listContentId) {
		return this.clockinReviewMapper.selectMapReviewCountsByListContentId(listContentId);
	}
	
	@Override
	public Long getReviewCountByContentId(Integer clockinContentId) {
		QueryWrapper<ClockinReview> queryWrapper = new QueryWrapper<ClockinReview>();
		queryWrapper.eq("clockin_content_id", clockinContentId).eq("status", 0).eq("check_status", 1);
		List<ClockinReview> list = this.list(queryWrapper);
		return ((list == null) ? (long)0 : (long)list.size());
	}

	@Override
	public IPage<ClockinReview> getPageClockinReviewByContentId(IPage<ClockinReview> page,Integer clockinContentId) {
		QueryWrapper<ClockinReview> queryWrapper = new QueryWrapper<ClockinReview>();
		queryWrapper.eq("clockin_content_id", clockinContentId).eq("status", 0).eq("check_status", 1).orderByDesc("id");
		return this.page(page, queryWrapper); 
	}

	@Override
	public void checkRejectClockinReview(Integer reviewId) {

		//将内容干掉 
		ClockinReview cr = this.getById(reviewId);
		String content = cr.getContent();
		if(cr!=null) {
			cr.setCheckStatus(2);
			cr.setStatus(1);
			cr.setLastEditTime(new Date());
			this.saveOrUpdate(cr);
			
			//发送消息
	    	CustomerMessage cm = new CustomerMessage();
	    	cm.setContent("您的评论存在违规内容，已被系统删除");
	    	cm.setCreateDate(new Date());
	    	cm.setCustomerInfoId(cr.getCustomerInfoId());
	    	cm.setSourceCustomerInfoId(0);
	    	cm.setMessageType(6);
	    	cm.setObjectContent(content);
	    	cm.setObjectId(cr.getId());
	    	cm.setReadStatus(0);
	    	cm.setStatus(0);
	    	this.customerMessageService.save(cm);
		}
		
	}

}
