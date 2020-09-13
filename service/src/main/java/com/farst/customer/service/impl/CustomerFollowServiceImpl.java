package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerFollow;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.mapper.CustomerFollowMapper;
import com.farst.customer.service.ICustomerFollowService;
import com.baomidou.mybatisplus.core.metadata.IPage; 
import com.farst.common.service.impl.BasicServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关注 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class CustomerFollowServiceImpl extends BasicServiceImpl<CustomerFollowMapper, CustomerFollow> implements ICustomerFollowService {

	@Autowired
	private CustomerFollowMapper customerFollowMapper;

	@Override
	public IPage<CustomerInfo> getPageMyFollow(IPage<CustomerInfo> page,Integer customerInfoId) {
		return this.customerFollowMapper.selectPageMyFollow(page, customerInfoId);
	}
	@Override
	public IPage<CustomerInfo> getPageMyFans(IPage<CustomerInfo> page,Integer customerInfoId) {
		return this.customerFollowMapper.selectPageMyFans(page, customerInfoId);
	}

	
}
