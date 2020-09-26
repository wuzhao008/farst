package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerSuggest;
import com.farst.customer.mapper.CustomerSuggestMapper;
import com.farst.customer.service.ICustomerSuggestService;
import com.farst.common.service.impl.BasicServiceImpl;
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

}
