package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.mapper.ClockinLabelMapper;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡标签 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinLabelServiceImpl extends BasicServiceImpl<ClockinLabelMapper, ClockinLabel>
		implements IClockinLabelService {

	@Override
	public List<ClockinLabel> getListClockinLabelByPid(Integer pid) {
		QueryWrapper<ClockinLabel> queryWrapper = new QueryWrapper<ClockinLabel>();
		if (pid == null) {
			queryWrapper.eq("status", 0).isNull("pid").orderByAsc("sort");
		} else {
			queryWrapper.eq("status", 0).eq("pid", pid).orderByAsc("sort");
		}
		return this.list(queryWrapper);
	}

}
