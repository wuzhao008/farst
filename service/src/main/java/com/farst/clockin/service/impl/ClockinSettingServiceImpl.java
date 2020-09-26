package com.farst.clockin.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinSetting;
import com.farst.clockin.mapper.ClockinSettingMapper;
import com.farst.clockin.service.IClockinSettingService;
import com.farst.common.service.impl.BasicServiceImpl;
import com.farst.common.utils.DateUtils;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡设置 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinSettingServiceImpl extends BasicServiceImpl<ClockinSettingMapper, ClockinSetting> implements IClockinSettingService {

	@Override
	public ClockinSetting getLatestClockingSettingBy(Integer customerLabelId) {
		QueryWrapper<ClockinSetting> queryWrapper = new QueryWrapper<ClockinSetting>();
		queryWrapper.eq("status", 0)
					.eq("customer_label_id", customerLabelId)
					.orderByDesc("freq_start_date");
		List<ClockinSetting> listCs = this.list(queryWrapper);
		return CollectionUtils.isEmpty(listCs) ? null : listCs.get(0);
		
	}

	@Override
	public void updateLatestPopupLog(Integer customerLabelId, Integer popupLog) {
		QueryWrapper<ClockinSetting> queryWrapper = new QueryWrapper<ClockinSetting>();
		queryWrapper.eq("status", 0)
					.eq("customer_label_id", customerLabelId)
					.orderByDesc("month");
		List<ClockinSetting> listCs = this.list(queryWrapper);
		if(CollectionUtils.isNotEmpty(listCs)) {
			for(ClockinSetting cs : listCs) {
				cs.setPopupLog(popupLog);
				cs.setLastEditTime(new Date());
				this.saveOrUpdate(cs); 
			}
		}
	}
	
}
