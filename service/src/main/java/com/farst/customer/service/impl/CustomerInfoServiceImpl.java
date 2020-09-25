package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.mapper.CustomerInfoMapper;
import com.farst.customer.service.ICustomerFollowService;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService;
import com.farst.customer.vo.CustomerInfoVo;
import com.farst.customer.vo.CustomerStatisticsVo;
import com.farst.customer.vo.TokenCustVo;

import io.jsonwebtoken.Claims;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinContentUpService;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.common.exception.ServiceException;
import com.farst.common.service.impl.BasicServiceImpl;
import com.farst.common.utils.JwtUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ICustomerLabelService customerLabelService;
	
	@Autowired
	private IClockinLabelService clockinLabelService;
	
	@Autowired
	private IClockinContentService clockinContentService;
	
	@Autowired
	private IClockinContentUpService clockinContentUpService;
	
	@Autowired
	private ICustomerFollowService customerFollowService;
	
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

	@Override
	public List<CustomerInfo> getListCustomerInfoBy(List<Integer> listCustId) {
		QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<CustomerInfo>();
		queryWrapper.eq("status", 0).in("id", listCustId);
		return this.list(queryWrapper);
	}

	@Override
	public CustomerInfoVo getCustomerInfoVoById(Integer custId) {
		CustomerInfoVo customerInfoVo = new CustomerInfoVo();
		
		//客户信息
		CustomerInfo customerInfo = this.getById(custId);
		customerInfoVo.setCustomerInfo(customerInfo);
		
		//客户拥有的标签信息
		List<CustomerLabel> listCustomerLabel = this.customerLabelService.getListCustomerLabel(custId);
		List<Integer> listLabelId = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(listCustomerLabel)) {
			listCustomerLabel.forEach(customerLabel->{
				listLabelId.add(customerLabel.getClockinLabelId());
			});
		}
		List<ClockinLabel> listClockinLabel = this.clockinLabelService.getListClockinLabelByListId(listLabelId);
		customerInfoVo.setListClockinLabel(listClockinLabel);
		
		//获取统计信息
		CustomerStatisticsVo statisticsVo = new CustomerStatisticsVo();
		Integer logCount = this.clockinContentService.getCountClockinContent(custId);
		statisticsVo.setLogCount(logCount);
		Integer followCount = this.customerFollowService.getCountMyFollow(custId);
		statisticsVo.setFollowCount(followCount);
		Integer fansCount = this.customerFollowService.getCountMyFans(custId);
		statisticsVo.setFansCount(fansCount);
		Integer upCount = this.clockinContentUpService.getMyUpCount(custId);
		statisticsVo.setUpCount(upCount);
		
		customerInfoVo.setStatisticsVo(statisticsVo);
		return customerInfoVo;
	} 
	
}
