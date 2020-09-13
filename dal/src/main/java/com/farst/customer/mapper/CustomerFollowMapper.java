package com.farst.customer.mapper;

import com.farst.common.mybatis.mapper.BasicMapper;
import com.farst.customer.entity.CustomerFollow;
import com.farst.customer.entity.CustomerInfo; 

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户关注 Mapper 接口
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface CustomerFollowMapper extends BasicMapper<CustomerFollow> {

	IPage<CustomerInfo> selectPageMyFollow(IPage<CustomerInfo> page,@Param("customerInfoId") Integer customerInfoId);
	
	IPage<CustomerInfo> selectPageMyFans(IPage<CustomerInfo> page,@Param("customerInfoId") Integer customerInfoId);
	
}
