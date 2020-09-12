package com.farst.sys.service;

import com.farst.sys.entity.SysHelpCenter;

import java.util.List;

import com.farst.common.service.IBasicService;

/**
 * <p>
 * 帮助中心 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ISysHelpCenterService extends IBasicService<SysHelpCenter> {

	public List<SysHelpCenter> getListHelp();
}
