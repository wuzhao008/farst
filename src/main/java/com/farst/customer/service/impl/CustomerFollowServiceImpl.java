package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerFollow;
import com.farst.customer.mapper.CustomerFollowMapper;
import com.farst.customer.service.ICustomerFollowService;
import com.farst.common.service.impl.BasicServiceImpl;
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

}
