package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerFollowService;
import com.farst.customer.service.ICustomerInfoService; 
import com.farst.customer.entity.CustomerInfo;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户关注 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户关注"})
@RestController
@RequestMapping("/customer/customerFollow")
public class CustomerFollowController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerInfoService customerInfoService; 
    
    @Autowired
    private ICustomerFollowService customerFollowService;

    /**
     * 查询我的关注分页信息
     */
    @ApiOperation(value = "查询我的关注分页信息")
    @GetMapping(value = "/pageMyFollow")
    public RestResponse<IPage<CustomerInfo>> pageMyFollow(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerInfo>> response = new RestResponse<>();
    	IPage<CustomerInfo> page = new Page<CustomerInfo>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.customerFollowService.getPageMyFollow(page, custId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    } 
    
    /**
     * 查询我的粉丝分页信息
     */
    @ApiOperation(value = "查询我的粉丝分页信息")
    @GetMapping(value = "/pageMyFans")
    public RestResponse<IPage<CustomerInfo>> pageMyFans(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerInfo>> response = new RestResponse<>();
    	IPage<CustomerInfo> page = new Page<CustomerInfo>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.customerFollowService.getPageMyFans(page, custId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    } 
 

    /**
     * 关注某用户
     */
    @ApiOperation(value = "关注某用户")
    @GetMapping(value = "/follow")
    public RestResponse<String> follow(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "customerInfoId") int customerInfoId){
        RestResponse<String> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		this.customerFollowService.follow(custId, customerInfoId);
	    	response.setSuccess("关注成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    } 
    

    /**
     * 取关某用户
     */
    @ApiOperation(value = "取关某用户")
    @GetMapping(value = "/unfollow")
    public RestResponse<String> unfollow(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "customerInfoId") int customerInfoId){
        RestResponse<String> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		this.customerFollowService.unfollow(custId, customerInfoId);
	    	response.setSuccess("取关成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    } 
    
}
