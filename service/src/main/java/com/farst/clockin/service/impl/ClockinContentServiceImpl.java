package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinPicture;
import com.farst.clockin.mapper.ClockinContentMapper;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinPictureService;
import com.farst.clockin.vo.ClockinContentVo;
import com.farst.clockin.vo.TodayClockinVo;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡文字内容 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinContentServiceImpl extends BasicServiceImpl<ClockinContentMapper, ClockinContent> implements IClockinContentService {

	@Autowired
	private ClockinContentMapper clockinContentMapper;
	
	@Autowired
	private IClockinPictureService clockinPictureService;
	
	@Override
	public IPage<TodayClockinVo> getPageTodayClockinVo(IPage<TodayClockinVo> page, Integer customerInfoId) {
		return this.clockinContentMapper.selectPageTodayClockinVo(page, customerInfoId);
	}
	
	@Override
	public IPage<ClockinContent> getPageSimilarClockinContent(IPage<ClockinContent> page,Integer customerInfoId){
		return this.clockinContentMapper.selectPageSimilarClockinContent(page, customerInfoId);
	}
	
	@Override
	public ClockinContent getTodayClockinContent(Integer customerInfoId, Integer labelId) {
		return this.clockinContentMapper.selectTodayClockinContent(customerInfoId, labelId);
	}


	@Override
	public ClockinContentVo getTodayClockinContentVo(Integer customerInfoId, Integer labelId) {
		ClockinContentVo ccVo = new ClockinContentVo();
		ClockinContent cc = this.getTodayClockinContent(customerInfoId, labelId);
		ccVo.setClockinContent(cc);
		if(cc!=null) {
			List<ClockinPicture> clockinPictures = clockinPictureService.getAllClockinPictureByContentId(cc.getId());
			ccVo.setClockinPictures(clockinPictures);
		}
		return ccVo;
	}
	
	@Override
	public void todayClockin(Integer customerInfoId, Integer labelId) {
		ClockinContent cc = this.getTodayClockinContent(customerInfoId, labelId);
		if(cc == null) {
			cc = new ClockinContent();
			cc.setCustomerInfoId(customerInfoId);
			cc.setClockinLabelId(labelId);
			cc.setCreateDate(new Date());
			cc.setCheckStatus(0);
			cc.setStatus(0);
			this.save(cc);
		}
	}
	
	@Override
	public void reverseTodayClockin(Integer customerInfoId, Integer labelId) {
		ClockinContent cc = this.getTodayClockinContent(customerInfoId, labelId);
		if(cc != null) {
			cc.setStatus(1);
			cc.setLastEditTime(new Date());
			this.saveOrUpdate(cc);
		}
	}
	
	@Override
	public void publishTodayClockinContent(Integer customerInfoId, Integer labelId, String content,
			List<String> picUrls, Integer isPublic) {
		ClockinContent cc = this.getTodayClockinContent(customerInfoId, labelId);
		if(cc != null) {
			cc.setContent(content);
			cc.setIsPublic(isPublic);
			cc.setLastEditTime(new Date());
			this.saveOrUpdate(cc);
			//先删除
			List<ClockinPicture> listCp = this.clockinPictureService.getAllClockinPictureByContentId(cc.getId());
			if(CollectionUtils.isNotEmpty(listCp)) {
				for(ClockinPicture cp : listCp) {
					cp.setStatus(1);
					cp.setLastEditTime(new Date());
					this.clockinPictureService.saveOrUpdate(cp);
				}
			}
			//再插入
			if(CollectionUtils.isNotEmpty(picUrls)) {
				for(int i=0;i<picUrls.size();i++) {
					ClockinPicture cp = new ClockinPicture();
					cp.setClockinContentId(cc.getId());
					cp.setPicUrl(picUrls.get(i));
					cp.setCreateDate(new Date());
					cp.setSort(i);
					cp.setStatus(0);
					this.clockinPictureService.save(cp);
				}
			}
		}
	}
		
}
