package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.mapper.ClockinContentMapper;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.vo.TodayClockinVo;
import com.farst.common.service.impl.BasicServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡文字内容 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinContentServiceImpl extends BasicServiceImpl<ClockinContentMapper, ClockinContent> implements IClockinContentService {

	@Autowired
	private ClockinContentMapper clockinContentMapper;
	
	@Override
	public IPage<TodayClockinVo> getPageTodayClockinVo(IPage<TodayClockinVo> page, Integer customerInfoId) {
		return this.clockinContentMapper.selectPageTodayClockinVo(page, customerInfoId);
	}
	
}
