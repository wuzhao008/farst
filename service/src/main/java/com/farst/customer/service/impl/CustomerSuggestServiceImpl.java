package com.farst.customer.service.impl;
import com.farst.customer.entity.CustomerSuggest;
import com.farst.customer.mapper.CustomerSuggestMapper;
import com.farst.customer.service.ICustomerSuggestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户建议 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-26
 */
@Service
public class CustomerSuggestServiceImpl extends BasicServiceImpl<CustomerSuggestMapper, CustomerSuggest> implements ICustomerSuggestService {


	@Override
	public IPage<CustomerSuggest> getPageMySuggest(IPage<CustomerSuggest> page, Integer customerInfoId) {
		QueryWrapper<CustomerSuggest> queryWrapper = new QueryWrapper<CustomerSuggest>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("status", 0).orderByDesc("create_date");
		return this.page(page, queryWrapper);
	}

	@Override
	public void deleteCustomerSuggest(Integer id) {
		CustomerSuggest cs = this.getById(id);
		cs.setStatus(1);
		cs.setLastEditTime(new Date());
		this.saveOrUpdate(cs);
	}
}
