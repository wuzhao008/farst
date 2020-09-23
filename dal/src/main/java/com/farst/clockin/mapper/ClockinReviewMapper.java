package com.farst.clockin.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.farst.clockin.entity.ClockinReview;
import com.farst.common.mybatis.mapper.BasicMapper;

/**
 * <p>
 * 打卡内容评论 Mapper 接口
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ClockinReviewMapper extends BasicMapper<ClockinReview> {

	List<Map<String, Object>> selectMapReviewCountsByListContentId(@RequestParam("listContentId") List<Integer> listContentId);
	
}
