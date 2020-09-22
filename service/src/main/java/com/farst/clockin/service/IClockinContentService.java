package com.farst.clockin.service; 
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.vo.ClockinContentVo;
import com.farst.clockin.vo.TodayClockinVo;
import com.farst.common.service.IBasicService; 

/**
 * <p>
 * 打卡文字内容 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinContentService extends IBasicService<ClockinContent> {

	/**
	 * 查询当天打卡列表信息
	 * 
	 * @param page
	 * @param customerInfoId 当前用户ID 
	 * @return
	 */
	public IPage<TodayClockinVo> getPageTodayClockinVo(IPage<TodayClockinVo> page,Integer customerInfoId);
	
	/**
	 * 得到当天用户对应标签的打卡内容
	 * 
	 * @param customerInfoId
	 * @param labelId
	 * @return
	 */
	public ClockinContent getTodayClockinContent(Integer customerInfoId,Integer labelId);
	
	/**
	 * 得到当天用户对应标签打卡内容VO（含图片url）
	 * @param customerInfoId
	 * @param labelId
	 * @return
	 */
	public ClockinContentVo getTodayClockinContentVo(Integer customerInfoId,Integer labelId);
	
	/**
	 * 今日打卡
	 * 
	 * @param customerInfoId
	 * @param labelId
	 */
	public void todayClockin(Integer customerInfoId,Integer labelId);
	
	/**
	 * 撤销今日打卡
	 * 
	 * @param customerInfoId
	 * @param labelId
	 */
	public void reverseTodayClockin(Integer customerInfoId,Integer labelId);
	
	/**
	 * 发布今日打卡内容 
	 * 
	 * @param customerInfoId
	 * @param labelId
	 * @param content
	 * @param picUrls
	 * @param isPublic
	 */
	public void publishTodayClockinContent(Integer customerInfoId,Integer labelId,String content,List<String> picUrls,Integer isPublic);
	
	
}
