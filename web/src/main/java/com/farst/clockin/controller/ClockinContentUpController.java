package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinContentUp; 
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinContentUpService;  
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farst.common.web.controller.BasicController;
import com.farst.common.web.response.RestResponse;
import com.farst.customer.service.ICustomerInfoService;
 
/**
 * <p>
    * 打卡内容顶UP 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡内容顶UP"})
@RestController
@RequestMapping("/clockin/clockinContentUp")
public class ClockinContentUpController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private IClockinContentService clockinContentService;
    
    @Autowired
    private IClockinContentUpService clockinContentUpService;
 	
    @ApiOperation(value = "点赞日志")
    @PostMapping(value = "/upContent")
    public RestResponse<String> upContent(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinContentId") Integer clockinContentId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinContent cc = this.clockinContentService.getById(clockinContentId);
        	if(cc == null || cc.getStatus() != 0) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	ClockinContentUp ccup = this.clockinContentUpService.getClockinContentRecord(custId, clockinContentId);
        	if(ccup == null) {
        		ccup = new ClockinContentUp();
        		ccup.setClockinContentId(clockinContentId);
        		ccup.setCustomerInfoId(custId);
        		ccup.setStatus(0);
        		ccup.setCreateDate(new Date());
        	}else {
        		ccup.setStatus(0);
        		ccup.setLastEditTime(new Date());
        	}
        	this.clockinContentUpService.saveOrUpdate(ccup);
     		response.setSuccess("点赞成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }

    @ApiOperation(value = "取消点赞日志")
    @PostMapping(value = "/cancelUpContent")
    public RestResponse<String> cancelUpContent(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinContentId") Integer clockinContentId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinContent cc = this.clockinContentService.getById(clockinContentId);
        	if(cc == null || cc.getStatus() != 0) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	ClockinContentUp ccup = this.clockinContentUpService.getClockinContentRecord(custId, clockinContentId);
        	if(ccup != null) {
        		ccup.setStatus(1);
        		ccup.setLastEditTime(new Date());
            	this.clockinContentUpService.saveOrUpdate(ccup);
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
