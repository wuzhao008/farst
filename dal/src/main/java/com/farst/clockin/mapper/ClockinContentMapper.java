package com.farst.clockin.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
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
	
}
