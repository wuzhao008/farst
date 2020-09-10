package com.farst.clockin.service;

import java.util.List;

import com.farst.clockin.entity.ClockinLabel;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡标签 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinLabelService extends IBasicService<ClockinLabel> {

	/**
	 * 根据父标签id得到标签列表
	 * 
	 * @param pid 父标签ID
	 * 
	 * @return
	 */
	public List<ClockinLabel> getListClockinLabelByPid(Integer pid);
}
