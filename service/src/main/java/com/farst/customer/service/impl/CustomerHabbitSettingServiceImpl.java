package com.farst.customer.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.mapper.ClockinSettingMapper;
import com.farst.common.service.impl.BasicServiceImpl; 
import com.farst.customer.entity.CustomerHabbitSetting;
import com.farst.customer.service.ICustomerHabbitSettingService; 
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
public class CustomerHabbitSettingServiceImpl extends BasicServiceImpl<ClockinSettingMapper, CustomerHabbitSetting> implements ICustomerHabbitSettingService {

	@Override
	public CustomerHabbitSetting getLatestHabbitSettingBy(Integer customerHabbitId) {
		QueryWrapper<CustomerHabbitSetting> queryWrapper = new QueryWrapper<CustomerHabbitSetting>();
		queryWrapper.eq("status", 0)
					.eq("customer_habbit_id", customerHabbitId)
					.orderByDesc("freq_start_date");
		List<CustomerHabbitSetting> listCs = this.list(queryWrapper);
		return CollectionUtils.isEmpty(listCs) ? null : listCs.get(0);
		
	} 
	
}
