package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.mapper.CustomerLabelMapper;
import com.farst.customer.service.ICustomerLabelService;
import com.farst.common.service.impl.BasicServiceImpl;
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

}
