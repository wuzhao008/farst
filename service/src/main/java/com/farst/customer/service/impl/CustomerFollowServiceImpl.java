package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerFollow;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.mapper.CustomerFollowMapper;
import com.farst.customer.service.ICustomerFollowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage; 
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.Date;

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

	@Override
	public Integer getCountMyFollow(Integer customerInfoId) {
		QueryWrapper<CustomerFollow> queryWrapper = new QueryWrapper<CustomerFollow>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("status", 0);
		return this.count(queryWrapper);
	}
	
	@Override
	public Integer getCountMyFans(Integer customerInfoId) {
		QueryWrapper<CustomerFollow> queryWrapper = new QueryWrapper<CustomerFollow>();
		queryWrapper.eq("follow_customer_info_id", customerInfoId).eq("status", 0);
		return this.count(queryWrapper);
	}
	
	@Override
	public CustomerFollow getCustomerFollowRecord(Integer customerInfoId, Integer followCustomerInfoId) {
		QueryWrapper<CustomerFollow> queryWrapper = new QueryWrapper<CustomerFollow>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("follow_customer_info_id", followCustomerInfoId);
		return this.getOne(queryWrapper);
	}
	
	@Override
	public void follow(Integer customerInfoId, Integer followCustomerInfoId) {
		CustomerFollow cf = this.getCustomerFollowRecord(customerInfoId, followCustomerInfoId);
		if(cf == null) {
			cf = new CustomerFollow();
			cf.setCreateDate(new Date());
			cf.setCustomerInfoId(customerInfoId);
			cf.setFollowCustomerInfoId(followCustomerInfoId);
			cf.setStatus(0);
		}else if(cf.getStatus().intValue() != 0) {
			cf.setStatus(0);
			cf.setLastEditTime(new Date());
		}
		this.saveOrUpdate(cf);
	}
	@Override
	public void unfollow(Integer customerInfoId, Integer followCustomerInfoId) {
		CustomerFollow cf = this.getCustomerFollowRecord(customerInfoId, followCustomerInfoId);
		if(cf != null && cf.getStatus().intValue() == 0) { 
			cf.setLastEditTime(new Date());
			cf.setStatus(1);
		}
		this.saveOrUpdate(cf);
	}

	
}
