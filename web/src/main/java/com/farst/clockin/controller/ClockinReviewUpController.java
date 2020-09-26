package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.service.IClockinReviewService;
import com.farst.clockin.service.IClockinReviewUpService; 
import com.farst.clockin.entity.ClockinReview;
import com.farst.clockin.entity.ClockinReviewUp; 
import com.farst.common.web.response.RestResponse;
import com.farst.customer.service.ICustomerInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 打卡评论顶UP 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡评论顶UP"})
@RestController
@RequestMapping("/clockin/clockinReviewUp")
public class ClockinReviewUpController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass()); 
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private IClockinReviewService clockinReviewService;
    
    @Autowired
    private IClockinReviewUpService clockinReviewUpService;
 	
    @ApiOperation(value = "点赞评论")
    @PostMapping(value = "/upReview")
    public RestResponse<String> upReview(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinReviewId") Integer clockinReviewId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinReview cc = this.clockinReviewService.getById(clockinReviewId);
        	if(cc == null || cc.getStatus() != 0) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	ClockinReviewUp ccup = this.clockinReviewUpService.getClockinReviewRecord(custId, clockinReviewId);
        	if(ccup == null) {
        		ccup = new ClockinReviewUp();
        		ccup.setClockinReviewId(clockinReviewId);
        		ccup.setCustomerInfoId(custId);
        		ccup.setStatus(0);
        		ccup.setCreateDate(new Date());
        	}else {
        		ccup.setStatus(0);
        		ccup.setLastEditTime(new Date());
        	}
        	this.clockinReviewUpService.saveOrUpdate(ccup);
     		response.setSuccess("点赞成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }

    @ApiOperation(value = "取消点赞日志")
    @PostMapping(value = "/cancelUpReview")
    public RestResponse<String> cancelUpReview(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinReviewId") Integer clockinReviewId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinReview cc = this.clockinReviewService.getById(clockinReviewId);
        	if(cc == null || cc.getStatus() != 0) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	ClockinReviewUp ccup = this.clockinReviewUpService.getClockinReviewRecord(custId, clockinReviewId);
        	if(ccup != null) {
        		ccup.setStatus(1);
        		ccup.setLastEditTime(new Date());
            	this.clockinReviewUpService.saveOrUpdate(ccup);
        	}
     		response.setSuccess("取消点赞成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
}
