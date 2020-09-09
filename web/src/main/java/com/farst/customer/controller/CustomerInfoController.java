package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerInfoService; 
import com.farst.customer.entity.CustomerInfo;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.cache.redis.RedisUtils;
import com.farst.common.utils.RandomUtils;
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
 	
    @Resource
    private RedisUtils redisUtils;
    
    private final String KEY_PREFIX_FARST_SMS_VERIFY_CODE = "FARST_SMS_VERIFY_CODE_";
    
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
	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<CustomerInfo>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerInfo>> response = new RestResponse<>();
    	IPage<CustomerInfo> page = new Page<CustomerInfo>(pageNum, pageSize);
    	QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<CustomerInfo>();
    	CustomerInfo customerInfo = new CustomerInfo();
    	wrapper.setEntity(customerInfo);
    	try {
	    	page = this.customerInfoService.page(page, wrapper);
	    	response.setData(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
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
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public RestResponse<CustomerInfo> add(@RequestBody CustomerInfo customerInfo){
    	 RestResponse<CustomerInfo> response = new RestResponse<>();
         try {
            customerInfoService.save(customerInfo);
            response.setSuccess(customerInfo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
 
    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/update")
    public RestResponse<CustomerInfo> update(@RequestBody CustomerInfo customerInfo){
         RestResponse<CustomerInfo> response = new RestResponse<>();
         try {
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
