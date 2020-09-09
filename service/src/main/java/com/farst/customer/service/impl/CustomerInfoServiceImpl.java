package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.mapper.CustomerInfoMapper;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.common.service.impl.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class CustomerInfoServiceImpl extends BasicServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

}
