package com.farst.clockin.service; 
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.vo.ClockinContentVo; 
import com.farst.clockin.vo.ClockinTrendStatisticsVo;
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
	 * 得到和我同习惯的打卡列表
	 * 
	 * @param page
	 * @param customerInfoId
	 * @return
	 */
	public IPage<ClockinContent> getPageSimilarClockinContent(IPage<ClockinContent> page,Integer customerInfoId);

	/**
	 * 得到我的打卡列表
	 * 
	 * @param page
	 * @param customerInfoId
	 * @return
	 */
	public IPage<ClockinContent> getPageMyClockinContent(IPage<ClockinContent> page,Integer customerInfoId);
	
	/**
	 * 得到用户发布的日志总数
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public Integer getCountClockinContent(Integer customerInfoId);
	
	/**
	 * 得到当天用户对应习惯的打卡内容
	 * 
	 * @param customerInfoId
	 * @param habbitId
	 * @return
	 */
	public ClockinContent getTodayClockinContent(Integer customerInfoId,Integer habbitId);
	
	/**
	 * 查询当月用户对应习惯打卡内容列表
	 * 
	 * @param customerInfoId
	 * @param habbitId
	 * @return
	 */
	public List<ClockinContent> getCurMonthListClockinContent(Integer customerInfoId,Integer habbitId);
	
	/**
	 * 查询用户对应习惯对应月份的打卡内容列表
	 * @param customerInfoId
	 * @param month
	 * @return
	 */
	public List<ClockinContent> getMonthListClockinContent(Integer customerInfoId,Integer habbitId,String month);
	
	/**
	 * 查询用户对应习惯的趋势统计记录
	 * @param customerInfoId
	 * @param customerLabelId
	 * @param labelId
	 * @param type
	 * @return
	 */
	public List<ClockinTrendStatisticsVo> getListClockinTrendStatisticsVo(Integer customerInfoId,Integer habbitId, Integer type);
	
	/**
	 * 得到当天用户对应习惯打卡内容VO（含图片url）
	 * @param customerInfoId
	 * @param habbitId
	 * @return
	 */
	public ClockinContentVo getTodayClockinContentVo(Integer customerInfoId,Integer habbitId);
	
	/**
	 * 今日打卡
	 * 
	 * @param customerInfoId
	 * @param labelId
	 */
	public void todayClockin(Integer customerInfoId,Integer habbitId);
	
	/**
	 * 撤销今日打卡
	 * 
	 * @param customerInfoId
	 * @param labelId
	 */
	public void reverseTodayClockin(Integer customerInfoId,Integer habbitId);
	
	/**
	 * 发布今日打卡内容 
	 * 
	 * @param customerInfoId
	 * @param habbitId
	 * @param content
	 * @param picUrls
	 * @param isPublic
	 */
	public void publishTodayClockinContent(Integer customerInfoId,Integer habbitId,String content,List<String> picUrls,Integer isPublic);
	
	/**
	 * 修改id对应记录的content
	 * 
	 * @param id
	 * @param content
	 */
	public void updateContent(Integer id,String content);
	
	/**
	 * 审核拒绝打卡日志
	 * 
	 * @param contentId
	 */
	public void checkRejectClockinContent(Integer contentId);
	
	
}
