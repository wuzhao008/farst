package com.farst.clockin.service;

import java.util.List;

import com.farst.clockin.entity.ClockinPicture;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 打卡图片内容 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface IClockinPictureService extends IBasicService<ClockinPicture> {

	/**
	 * 根据内容ID得到对应的
	 * 
	 * @param contentId
	 * @return
	 */
	List<ClockinPicture> getAllClockinPictureByContentId(Integer contentId);
	
}
