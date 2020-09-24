package com.farst.clockin.service;

import java.util.List;
import java.util.Map;

import com.farst.clockin.entity.ClockinContentUp;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡内容顶UP 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinContentUpService extends IBasicService<ClockinContentUp> {
	
	/**
	 * 根据内容ID列表，得到每条对应的UP数
	 * 
	 * @param listContentId
	 * @return
	 */
	public List<Map<String,Object>> getMapContentUpsByListContentId(List<Integer> listContentId);
	
	/**
	 * 根据内容ID列表，获取当前用户有顶过的内容ID
	 * @param listContents
	 * @return
	 */
	public List<Integer> getMyUpContentIds(List<Integer> listContentId,Integer customerInfoId);
	
	/**
	 * 根据内容ID得到顶的数量
	 * 
	 * @param clockinContentId
	 * @return
	 */
	public Long getUpCountByContentId(Integer clockinContentId);
	
	/**
	 * 查询当前用户是否点赞这个日志
	 * 
	 * @param clockinContentId
	 * @param customerInfoId
	 * @return
	 */
	public boolean hasUpClockinContent(Integer clockinContentId,Integer customerInfoId);
	
	/**
	 * 根据用户ID和内容ID得到点赞记录
	 * 
	 * @param customerInfoId
	 * @param clockinContentId
	 * @return
	 */
	public ClockinContentUp getClockinContentRecord(Integer customerInfoId,Integer clockinContentId);
	
}
