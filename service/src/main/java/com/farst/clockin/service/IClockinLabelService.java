package com.farst.clockin.service;

import java.util.List;

import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.vo.AllClockinLabelVo;
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
	
	/**
	 * 根据标签ID列表获取标签信息列表
	 *  
	 * @param listLabelId 
	 * @return
	 */
	public List<ClockinLabel> getListClockinLabelByListId(List<Integer> listLabelId);
	 
	/**
	 * 查询所有的层级标签信息列表
	 *  
	 * @return
	 */
	public List<AllClockinLabelVo> getListAllClockinLabelVo();
}
