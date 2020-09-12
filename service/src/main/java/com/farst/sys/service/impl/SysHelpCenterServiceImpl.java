package com.farst.sys.service.impl;

import com.farst.sys.entity.SysHelpCenter;
import com.farst.sys.mapper.SysHelpCenterMapper;
import com.farst.sys.service.ISysHelpCenterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.common.service.impl.BasicServiceImpl; 

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 帮助中心 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class SysHelpCenterServiceImpl extends BasicServiceImpl<SysHelpCenterMapper, SysHelpCenter> implements ISysHelpCenterService {

	@Override
	public List<SysHelpCenter> getListHelp() {
		QueryWrapper<SysHelpCenter> queryWrapper = new QueryWrapper<SysHelpCenter>();
		queryWrapper.eq("status", 0).orderByAsc("sort");
		return this.list(queryWrapper);
	}

}
