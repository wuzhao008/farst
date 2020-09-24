package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.clockin.dto.ClockinContentDto;
import com.farst.clockin.dto.ClockinReviewDto;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinReview;
import com.farst.common.web.response.RestResponse;
import com.farst.customer.service.ICustomerInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Arrays;
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
    * 打卡内容评论 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡内容评论"})
@RestController
@RequestMapping("/clockin/clockinReview")
public class ClockinReviewController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IClockinReviewService clockinReviewService;
 	
    @Autowired
    private IClockinContentService clockinContentService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    /**
     * 查询日志内容对应的分页评论信息
     */
    @ApiOperation(value = "查询日志内容对应的分页评论信息")
    @GetMapping(value = "/getPageReviewByContentId")
    public RestResponse<IPage<ClockinReview>> getPageReviewByContentId(@RequestParam(name="clockinContentId") Integer clockinContentId,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinReview>> response = new RestResponse<>();
    	IPage<ClockinReview> page = new Page<ClockinReview>(pageNum, pageSize);
    	try {
	    	page = this.clockinReviewService.getPageClockinReviewByContentId(page, clockinContentId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	
    @ApiOperation(value = "评论打卡日志")
    @PostMapping(value = "/reviewContent")
    public RestResponse<String> reviewContent(@RequestHeader("tokenid") String tokenid,@RequestBody ClockinReviewDto clockinReviewDto){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinContent cc = this.clockinContentService.getById(clockinReviewDto.getClockinContentId());
        	if(cc == null || cc.getStatus() != 0) {
        		response.setErrorMsg("非法日志内容");
        		return response;
        	}
        	
        	ClockinReview cr = new ClockinReview();
        	cr.setClockinContentId(clockinReviewDto.getClockinContentId());
        	cr.setContent(clockinReviewDto.getReviewContent());
        	cr.setStatus(0);
        	cr.setCustomerInfoId(custId);
        	cr.setCreateDate(new Date());
        	this.clockinReviewService.save(cr);
     		response.setSuccess("评论成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    @ApiOperation(value = "删除某条我的评论")
    @PostMapping(value = "/removeReview")
    public RestResponse<String> removeReview(@RequestHeader("tokenid") String tokenid,@RequestParam(name="id") Integer id){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinReview cr = this.clockinReviewService.getById(id);
        	if(cr == null || cr.getStatus() != 0 || cr.getCustomerInfoId() != custId) {
        		response.setErrorMsg("非法请求");
        	}
        	cr.setStatus(1);
        	cr.setLastEditTime(new Date());
        	this.clockinReviewService.saveOrUpdate(cr);
     		response.setSuccess("删除评论成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    
    
}
