package com.farst.clockin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.vo.ClockinTrendStatisticsVo;
import com.farst.clockin.vo.TodayClockinVo;
import com.farst.common.mybatis.mapper.BasicMapper; 

/**
 * <p>
 * 打卡文字内容 Mapper 接口
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ClockinContentMapper extends BasicMapper<ClockinContent> {
	
	IPage<TodayClockinVo> selectPageTodayClockinVo(IPage<TodayClockinVo> page,@Param("customerInfoId") Integer customerInfoId);

	IPage<ClockinContent> selectPageSimilarClockinContent(IPage<ClockinContent> page,@Param("customerInfoId") Integer customerInfoId);
	
	IPage<ClockinContent> selectPageMyClockinContent(IPage<ClockinContent> page,@Param("customerInfoId") Integer customerInfoId);
	
	void updateContent(@Param("id") Integer id,@Param("content") String content);
	
	ClockinContent selectTodayClockinContent(@Param("customerInfoId") Integer customerInfoId,@Param("customerHabbitId") Integer customerHabbitId);
	
	List<ClockinContent> selectCurMonthListClockinContent(@Param("customerInfoId") Integer customerInfoId,@Param("customerHabbitId") Integer customerHabbitId);
	
	List<ClockinContent> selectMonthListClockinContent(@Param("customerInfoId") Integer customerInfoId,@Param("customerHabbitId") Integer customerHabbitId,@Param("month") String month);
	
	List<ClockinTrendStatisticsVo> selectListClockinTrendStatisticsVo(@Param("customerInfoId") Integer customerInfoId,@Param("customerHabbitId") Integer customerHabbitId,@Param("type") Integer type);
	
}
