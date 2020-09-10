package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.mapper.CustomerInfoMapper;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.vo.TokenCustVo;

import io.jsonwebtoken.Claims;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.common.exception.ServiceException;
import com.farst.common.service.impl.BasicServiceImpl;
import com.farst.common.utils.JwtUtils;

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
	 
	
	@Override
	public CustomerInfo getCustomerInfoByPhoneNumber(String phoneNumber) {
		QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<CustomerInfo>();
		queryWrapper.eq("status", 0).eq("phone_number", phoneNumber);
		return this.getOne(queryWrapper); 
	}

	@Override
	public TokenCustVo getTokenCustVo(String jwt) throws ServiceException {
		TokenCustVo tokenCustVo = new TokenCustVo();
		try {
			Claims claims = JwtUtils.parseJWT(jwt);
			String subject = claims.getSubject();			
			JSON json = (JSON) JSONObject.parse(subject);                      
			tokenCustVo = JSONObject.toJavaObject(json, TokenCustVo.class);
		} catch (Exception e) {
			System.out.println("tokonId="+jwt +" \r\n exception:"+e.getMessage());
			throw new ServiceException("获取用户信息失败",e);
		}
		return tokenCustVo;
	} 
	
}
