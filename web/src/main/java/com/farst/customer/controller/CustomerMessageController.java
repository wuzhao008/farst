package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerMessageService;
import com.farst.customer.entity.CustomerMessage;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户消息 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户消息"})
@RestController
@RequestMapping("/customer/customerMessage")
public class CustomerMessageController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ICustomerMessageService customerMessageService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/pageMyMessage")
    public RestResponse<IPage<CustomerMessage>> pageMyMessage(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerMessage>> response = new RestResponse<>();
    	IPage<CustomerMessage> page = new Page<CustomerMessage>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.customerMessageService.getPageMyMessage(page, custId);
	    	response.setData(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	
    /**
     * 查询未读消息总数
     */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getUnReadMessageCount")
    public RestResponse<Integer> getUnReadMessageCount(@RequestHeader("tokenid") String tokenid){
      	 RestResponse<Integer> response = new RestResponse<>();
         try {
      		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
            Integer count = this.customerMessageService.getUnReadMessageCount(custId);
            response.setSuccess(count);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
  

    @ApiOperation(value = "删除消息")
    @PostMapping(value = "/deleteMessage")
    public RestResponse<String> updateAllMessage2ReadStatus(@RequestHeader("tokenid") String tokenid,@RequestParam("messageId") Integer messageId){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId(); 
    		CustomerMessage customerMessage = this.customerMessageService.getById(messageId);
    		if(customerMessage == null || customerMessage.getCustomerInfoId().equals(custId) == false) {
    			response.setErrorMsg("非法请求");
    			return response;
    		}
    		customerMessage.setStatus(1);
    		customerMessage.setLastEditTime(new Date());
    		this.customerMessageService.saveOrUpdate(customerMessage);
    		response.setSuccess("删除成功");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
    

    @ApiOperation(value = "将所有消息设置为已读")
    @PostMapping(value = "/updateAllMessageToRead")
    public RestResponse<String> updateAllMessageToRead(@RequestHeader("tokenid") String tokenid){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId(); 
    		this.customerMessageService.updateAllMessageToRead(custId);
    		response.setSuccess("成功设置为已读");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
 
}
