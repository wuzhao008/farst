package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerHabbit;
import com.farst.customer.mapper.CustomerLabelMapper;
import com.farst.customer.service.ICustomerHabbitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; 
import com.farst.common.service.impl.BasicServiceImpl;
 
import java.util.List;

import org.apache.commons.collections.CollectionUtils; 
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户打卡标签 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class CustomerHabbitServiceImpl extends BasicServiceImpl<CustomerLabelMapper, CustomerHabbit>
		implements ICustomerHabbitService { 

	@Override
	public List<CustomerHabbit> getListCustomerHabbit(Integer customerInfoId) {
		QueryWrapper<CustomerHabbit> queryWrapper = new QueryWrapper<CustomerHabbit>();
		queryWrapper.eq("status", 0).eq("customer_info_id", customerInfoId).orderByAsc("id");
		return this.list(queryWrapper);
	}
	 
	@Override
	public void addCustomerHabbit(CustomerHabbit customerHabbit) {
		this.save(customerHabbit);
	} 

	@Override
	public boolean hasCustomerHabbit(Integer customerInfoId) {
		List<CustomerHabbit> list = this.getListCustomerHabbit(customerInfoId);
		if(CollectionUtils.isNotEmpty(list)) {
			return true;
		}
		return false;
	}

	@Override
	public List<CustomerHabbit> getListCustomerHabbitByListId(List<Integer> listHabbitId) {
		QueryWrapper<CustomerHabbit> queryWrapper = new QueryWrapper<CustomerHabbit>();
		queryWrapper.eq("status", 0).in("id", listHabbitId);
		return this.list(queryWrapper);
	}

}
