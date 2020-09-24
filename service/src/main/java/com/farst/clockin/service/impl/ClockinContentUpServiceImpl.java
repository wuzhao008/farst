package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinContentUp; 
import com.farst.clockin.mapper.ClockinContentUpMapper;
import com.farst.clockin.service.IClockinContentUpService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡内容顶UP 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinContentUpServiceImpl extends BasicServiceImpl<ClockinContentUpMapper, ClockinContentUp> implements IClockinContentUpService {

	@Autowired
	private ClockinContentUpMapper clockinContentUpMapper;
	
	@Override
	public List<Map<String, Object>> getMapContentUpsByListContentId(List<Integer> listContentId) {
		return this.clockinContentUpMapper.selectMapContentUpsByListContentId(listContentId);
	}

	@Override
	public List<Integer> getMyUpContentIds(List<Integer> listContentId, Integer customerInfoId) {
		QueryWrapper<ClockinContentUp> queryWrapper = new QueryWrapper<ClockinContentUp>();
		queryWrapper.eq("customer_info_id", customerInfoId).in("clockin_content_id", listContentId).eq("status", 0);
		List<ClockinContentUp> listCcup = this.list(queryWrapper);
		List<Integer> listCcupId = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(listCcup)) {
			listCcup.forEach(ccu->{
				listCcupId.add(ccu.getClockinContentId());
			});
		}
		return listCcupId;
	}
	
	@Override
	public Long getUpCountByContentId(Integer clockinContentId) {
		QueryWrapper<ClockinContentUp> queryWrapper = new QueryWrapper<ClockinContentUp>();
		queryWrapper.eq("clockin_content_id", clockinContentId).eq("status", 0);
		List<ClockinContentUp> list = this.list(queryWrapper);
		return ((list == null) ? (long)0 : (long)list.size());
	}

	@Override
	public ClockinContentUp getClockinContentRecord(Integer customerInfoId, Integer clockinContentId) {
		QueryWrapper<ClockinContentUp> queryWrapper = new QueryWrapper<ClockinContentUp>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("clockin_content_id", clockinContentId);
		return this.getOne(queryWrapper);
	}
	
	@Override
	public boolean hasUpClockinContent(Integer clockinContentId,Integer customerInfoId) {
		QueryWrapper<ClockinContentUp> queryWrapper = new QueryWrapper<ClockinContentUp>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("clockin_content_id", clockinContentId).eq("status", 0);
		List<ClockinContentUp> list = this.list(queryWrapper);
		boolean result = false;
		if(list!=null && list.size() >0) {
			result = true;
		}
		return result;
	}

}
