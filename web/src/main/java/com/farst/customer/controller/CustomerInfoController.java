package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService;
import com.farst.customer.vo.PhoneLoginVo;
import com.farst.customer.vo.TokenCustVo;
import com.farst.customer.vo.TokenInfoVo;
import com.farst.customer.dto.CustomerInfoDto;
import com.farst.customer.entity.CustomerInfo;
import com.farst.common.web.response.RestResponse;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject; 
import com.farst.common.cache.redis.RedisUtils;
import com.farst.common.exception.ServiceException; 
import com.farst.common.utils.JwtUtils; 
import com.farst.common.utils.StringUtils; 
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户信息 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户信息"})
@RestController
@RequestMapping("/customer/customerInfo")
public class CustomerInfoController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private ICustomerLabelService customerLabelService;
 	
    @Resource
    private RedisUtils redisUtils;
    
    private final String KEY_PREFIX_FARST_SMS_VERIFY_CODE = "FARST_SMS_VERIFY_CODE_";
    
    //tokenid缓存过期时间
    private final long TOKENID_EXPIRE = 1000 * 60 * 60 * 24 * 1L;
    
    /**
	 * 登陆接口
	 */ 
	@PostMapping("/getVerifyCode")
	@ApiOperation(value = "获取验证码")
	public RestResponse<String> getVerifyCode(HttpServletRequest request,@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
		RestResponse<String> response = new RestResponse<>();
		//临时生成5位验证码,后续对接发送短信接口
		String verifyCode = null;		
		try {  
			//verifyCode = RandomUtils.nextInt(1000, 9999).toString();
			verifyCode = "1234";
			redisUtils.set(KEY_PREFIX_FARST_SMS_VERIFY_CODE+phoneNumber, verifyCode, 10*60);
		    response.setSuccess(verifyCode);
		}catch (Exception e) {
			response.setErrorMsg("获取验证码失败");
		}
		return response;
	}
	 
	@PostMapping("/phoneLogin")
	@ApiOperation(value = "手机登陆")
	public RestResponse<PhoneLoginVo> phoneAuthLogin(HttpServletRequest request,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,@RequestParam(value = "verifyCode", required = true) String verifyCode) {
		RestResponse<PhoneLoginVo> response = new RestResponse<>();
		TokenInfoVo tokenInfoVo = new TokenInfoVo();
		boolean hasNickName = false;
		boolean hasSex = false;
		boolean hasLabel = false;
		try {
			String _verifyCode = (String) redisUtils.get(KEY_PREFIX_FARST_SMS_VERIFY_CODE+phoneNumber); 
			
			if(StringUtils.isNotEmpty(_verifyCode) && _verifyCode.equals(verifyCode)) {
				String ipAddress = StringUtils.getIpAddr(request);
				CustomerInfo customerInfo = this.customerInfoService.getCustomerInfoByPhoneNumber(phoneNumber);
				if(customerInfo != null) {
					customerInfo.setLastLoginIp(ipAddress);
					customerInfo.setLastLoginTime(new Date());
					this.customerInfoService.saveOrUpdate(customerInfo); 
					
					hasNickName = (customerInfo.getNickName() != null) ? true :false;
					hasSex = (customerInfo.getSex() != null) ? true : false;
					hasLabel = this.customerLabelService.hasCustomerLabel(customerInfo.getId());
					
				}else {
					customerInfo = new CustomerInfo(); 
					customerInfo.setPhoneNumber(phoneNumber);
					customerInfo.setLastLoginIp(ipAddress);
					customerInfo.setLastLoginTime(new Date());
					customerInfo.setCreateDate(new Date());
					customerInfo.setStatus(0);
					customerInfo.setLevel(1);
					customerInfo.setSlogan("为自己打卡，为自己坚持!");
					customerInfoService.save(customerInfo);
				}

				Integer custId = customerInfo.getId();
				 
				TokenCustVo tokenCustVo = new TokenCustVo();
				tokenCustVo.setCustId(custId); 
				tokenCustVo.setPhoneNumber(phoneNumber);
				
				//根据tokenCustVo获取tokenid
				String subject = JSONObject.toJSONString(tokenCustVo);
				String tokenid = JwtUtils.createJWT(custId.toString(), subject, TOKENID_EXPIRE);
				
				tokenInfoVo.setTokenId(tokenid);
				tokenInfoVo.setCustomerId(custId);
				tokenInfoVo.setExpire(TOKENID_EXPIRE); 
				
				//返回对象
				PhoneLoginVo phoneLoginVo = new PhoneLoginVo();
				phoneLoginVo.setHasLabel(hasLabel);
				phoneLoginVo.setHasNickName(hasNickName);
				phoneLoginVo.setHasSex(hasSex);
				phoneLoginVo.setTokenInfoVo(tokenInfoVo);
				response.setSuccess(phoneLoginVo);
					
			}else {
				response.setErrorMsg("验证码错误");
			}
			
			return response; 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}
	
	@PostMapping("/validateToken")
	@ApiOperation(value = "token验证")
	public RestResponse<PhoneLoginVo> validateToken(HttpServletRequest request,@RequestHeader("tokenid") String tokenid) {
		RestResponse<PhoneLoginVo> response = new RestResponse<>();
		TokenInfoVo tokenInfoVo = new TokenInfoVo();
		boolean hasNickName = false;
		boolean hasSex = false;
		boolean hasLabel = false;
		try {

    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		CustomerInfo customerInfo = this.customerInfoService.getById(custId);
					
			hasNickName = (customerInfo.getNickName() != null) ? true :false;
			hasSex = (customerInfo.getSex() != null) ? true : false;
			hasLabel = this.customerLabelService.hasCustomerLabel(customerInfo.getId());
			 
			TokenCustVo tokenCustVo = new TokenCustVo();
			tokenCustVo.setCustId(custId); 
			tokenCustVo.setPhoneNumber(customerInfo.getPhoneNumber());
			
			//根据tokenCustVo获取tokenid
			String subject = JSONObject.toJSONString(tokenCustVo);
			tokenid = JwtUtils.createJWT(custId.toString(), subject, TOKENID_EXPIRE);
			
			tokenInfoVo.setTokenId(tokenid);
			tokenInfoVo.setCustomerId(custId);
			tokenInfoVo.setExpire(TOKENID_EXPIRE);
			
			//返回对象
			PhoneLoginVo phoneLoginVo = new PhoneLoginVo();
			phoneLoginVo.setHasLabel(hasLabel);
			phoneLoginVo.setHasNickName(hasNickName);
			phoneLoginVo.setHasSex(hasSex);
			phoneLoginVo.setTokenInfoVo(tokenInfoVo);
			response.setSuccess(phoneLoginVo); 
			
			return response; 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}
  
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据客户id查询客户信息")
    @GetMapping(value = "/getById")
    public RestResponse<CustomerInfo> getById(@RequestParam("id") Integer id){
      	 RestResponse<CustomerInfo> response = new RestResponse<>();
         try {
            CustomerInfo customerInfo = this.customerInfoService.getById(id);
            response.setSuccess(customerInfo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    @ApiOperation(value = "设置昵称")
    @PostMapping(value = "/editNickName")
    public RestResponse<String> editNickName(@RequestHeader("tokenid") String tokenid,@RequestParam("nickName") String nickName){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		CustomerInfo customerInfo = this.customerInfoService.getById(custId);
    		customerInfo.setNickName(nickName);
    		customerInfo.setLastEditTime(new Date());
    		this.customerInfoService.saveOrUpdate(customerInfo);
    		response.setSuccess(null,"设置成功");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
    

    @ApiOperation(value = "设置性别")
    @PostMapping(value = "/editSex")
    public RestResponse<String> editSex(@RequestHeader("tokenid") String tokenid,@RequestParam("sex") Integer sex){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		CustomerInfo customerInfo = this.customerInfoService.getById(custId);
    		customerInfo.setSex(sex);
    		customerInfo.setLastEditTime(new Date());
    		this.customerInfoService.saveOrUpdate(customerInfo);
    		response.setSuccess(null, "设置成功");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
    
 
    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/update")
    public RestResponse<CustomerInfo> update(@RequestHeader("tokenid") String tokenid,@RequestBody CustomerInfoDto customerInfoDto){
         RestResponse<CustomerInfo> response = new RestResponse<>();
         try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	CustomerInfo customerInfo = this.customerInfoService.getById(custId);
        	BeanUtils.copyProperties(customerInfoDto, customerInfo);
        	customerInfo.setLastEditTime(new Date());
            customerInfoService.saveOrUpdate(customerInfo);
            response.setSuccess(customerInfo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
    
 
 
}
