package com.farst.clockin.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.farst.clockin.entity.ClockinContentUp;
import com.farst.common.mybatis.mapper.BasicMapper;

/**
 * <p>
 * 打卡内容顶UP Mapper 接口
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ClockinContentUpMapper extends BasicMapper<ClockinContentUp> {
	
	List<Map<String, Object>> selectMapContentUpsByListContentId(@RequestParam("listContentId") List<Integer> listContentId);
	
	public Integer selectMyUpCount(Integer customerInfoId);
	
}
