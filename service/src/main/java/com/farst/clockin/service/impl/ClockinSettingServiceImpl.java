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
	public ClockinSetting getLatestClockingSettingBy(Integer customerInfoId, Integer labelId) {
		QueryWrapper<ClockinSetting> queryWrapper = new QueryWrapper<ClockinSetting>();
		Date firstDayOfCurMonth = DateUtils.getSpecialDate(new Date(), 3);
		queryWrapper.eq("status", 0).eq("customer_info_id", customerInfoId)
					.eq("clockin_label_id", labelId)
					.le("month",firstDayOfCurMonth)
					.orderByDesc("month");
		List<ClockinSetting> listCs = this.list(queryWrapper);
		return CollectionUtils.isEmpty(listCs) ? null : listCs.get(0);
		
	}
	
}
