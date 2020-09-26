package com.farst.clockin.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.farst.clockin.entity.ClockinReviewUp;
import com.farst.common.mybatis.mapper.BasicMapper;

/**
 * <p>
 * 打卡评论顶UP Mapper 接口
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ClockinReviewUpMapper extends BasicMapper<ClockinReviewUp> {

	List<Map<String, Object>> selectMapReviewUpsByListReviewId(@RequestParam("listReviewId") List<Integer> listReviewId);
	
}
