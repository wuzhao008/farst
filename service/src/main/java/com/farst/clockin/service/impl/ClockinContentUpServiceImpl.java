package com.farst.clockin.service.impl;

import com.farst.clockin.entity.ClockinContentUp;
import com.farst.clockin.mapper.ClockinContentUpMapper;
import com.farst.clockin.service.IClockinContentUpService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.List;
import java.util.Map;

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

}
