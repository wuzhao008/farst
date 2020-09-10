package com.farst.customer.service.impl;
 
import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.mapper.CustomerLabelMapper;
import com.farst.customer.service.ICustomerLabelService;
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
public class CustomerLabelServiceImpl extends BasicServiceImpl<CustomerLabelMapper, CustomerLabel> implements ICustomerLabelService {

	@Override
	public List<CustomerLabel> getListCustomerLabel(Integer customerInfoId) {
		QueryWrapper<CustomerLabel> queryWrapper = new QueryWrapper<CustomerLabel>();
		queryWrapper.eq("status", 0).eq("customer_info_id", customerInfoId);
		return this.list(queryWrapper);
	}
	
	@Override
	public boolean hasCustomerLabel(Integer customerInfoId) {
		List<CustomerLabel> listCustomerLabel = this.getListCustomerLabel(customerInfoId);
		return CollectionUtils.isEmpty(listCustomerLabel) ? false : true;
	}

}
