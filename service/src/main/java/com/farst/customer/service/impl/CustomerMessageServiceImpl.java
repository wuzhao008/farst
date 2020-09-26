package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerMessage;
import com.farst.customer.mapper.CustomerMessageMapper;
import com.farst.customer.service.ICustomerMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户消息 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class CustomerMessageServiceImpl extends BasicServiceImpl<CustomerMessageMapper, CustomerMessage> implements ICustomerMessageService {

	@Override
	public Integer getUnReadMessageCount(Integer customerInfoId) {
		QueryWrapper<CustomerMessage> queryWrapper = new QueryWrapper<CustomerMessage>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("read_status", 0).eq("status", 0);
		return this.count(queryWrapper);
	}

	@Override
	public IPage<CustomerMessage> getPageMyMessage(IPage<CustomerMessage> page, Integer customerInfoId) {
		QueryWrapper<CustomerMessage> queryWrapper = new QueryWrapper<CustomerMessage>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("status", 0).orderByDesc("create_date");
		return this.page(page, queryWrapper);
	}

	@Override
	public void updateAllMessageToRead(Integer customerInfoId) {
		UpdateWrapper<CustomerMessage> updateWrapper = new UpdateWrapper<CustomerMessage>();
		updateWrapper.eq("customer_info_id", customerInfoId).eq("status", 0).eq("read_status", 0);
		updateWrapper.set("read_status", 1);
		updateWrapper.set("last_edit_time", new Date());
		this.update(updateWrapper);
	}

}
