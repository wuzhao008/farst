package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; 
import com.farst.clockin.entity.ClockinPicture;
import com.farst.clockin.mapper.ClockinPictureMapper;
import com.farst.clockin.service.IClockinPictureService;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡图片内容 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinPictureServiceImpl extends BasicServiceImpl<ClockinPictureMapper, ClockinPicture> implements IClockinPictureService {

	@Override
	public List<ClockinPicture> getAllClockinPictureByContentId(Integer contentId) {
		QueryWrapper<ClockinPicture> queryWrapper = new QueryWrapper<ClockinPicture>();
		queryWrapper.eq("status", 0).eq("clockin_content_id", contentId).orderByAsc("sort"); 
		return this.list(queryWrapper);
	}

	@Override
	public List<ClockinPicture> getAllClockinPictureByListContentId(List<Integer> listContentId) {
		QueryWrapper<ClockinPicture> queryWrapper = new QueryWrapper<ClockinPicture>();
		queryWrapper.eq("status", 0).in("clockin_content_id", listContentId).orderByAsc("clockin_content_id").orderByAsc("sort"); 
		return this.list(queryWrapper);
	}

}
