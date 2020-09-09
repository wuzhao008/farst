package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerMessage;
import com.farst.customer.mapper.CustomerMessageMapper;
import com.farst.customer.service.ICustomerMessageService;
import com.farst.common.service.impl.BasicServiceImpl;
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

}
