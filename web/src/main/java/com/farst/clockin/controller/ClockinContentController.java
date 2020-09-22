package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.dto.ClockinContentDto;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.vo.ClockinContentVo;
import com.farst.clockin.vo.TodayClockinVo; 
import com.farst.common.web.response.RestResponse;
import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 打卡文字内容 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡文字内容"})
@RestController
@RequestMapping("/clockin/clockinContent")
public class ClockinContentController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private IClockinContentService clockinContentService;
    
    @Autowired
    private ICustomerLabelService customerLabelService;
 	
    /**
     * 查询今日打卡列表信息
     */
    @ApiOperation(value = "查询今日打卡列表信息")
    @GetMapping(value = "/pageTodayClockin")
    public RestResponse<IPage<TodayClockinVo>> pageTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<TodayClockinVo>> response = new RestResponse<>();
    	IPage<TodayClockinVo> page = new Page<TodayClockinVo>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.clockinContentService.getPageTodayClockinVo(page, custId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    /**
     * 查询今日打卡详细信息
     */
    @ApiOperation(value = "查询今日打卡详细信息")
    @GetMapping(value = "/detailTodayClockin")
    public RestResponse<ClockinContentVo> detailTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "labelId") Integer labelId){
        RestResponse<ClockinContentVo> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		ClockinContentVo ccVo = this.clockinContentService.getTodayClockinContentVo(custId, labelId);
	    	response.setSuccess(ccVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
 
    @ApiOperation(value = "今日打卡")
    @PostMapping(value = "/todayClockin")
    public RestResponse<String> todayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "labelId") int labelId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		CustomerLabel cl = this.customerLabelService.getCustomerLabel(custId, labelId);
     		if(cl == null) {
     			response.setErrorMsg("非法习惯，请先选择习惯");
     			return response;
     		}
    		this.clockinContentService.todayClockin(custId, labelId);
    		response.setSuccess("打卡成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    @ApiOperation(value = "撤销今日打卡")
    @PostMapping(value = "/reverseTodayClockin")
    public RestResponse<String> reverseTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "labelId") int labelId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		this.clockinContentService.reverseTodayClockin(custId, labelId);
    		response.setSuccess("撤销打卡成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    


    @ApiOperation(value = "发布打卡日志")
    @PostMapping(value = "/publishTodayClockinContent")
    public RestResponse<String> publishTodayClockinContent(@RequestHeader("tokenid") String tokenid,@RequestBody ClockinContentDto clockinContentDto){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		ClockinContent cc = this.clockinContentService.getTodayClockinContent(custId, clockinContentDto.getLabelId());
     		if(cc == null) {
     			response.setErrorMsg("打卡后才能发布日志");
     			return response;
     		}
     		
     		List<String> listPicUrl = new ArrayList<String>();
     		if(StringUtils.isNoneEmpty(clockinContentDto.getPicUrl())) {
     			listPicUrl = Arrays.asList(clockinContentDto.getPicUrl().split(","));
     		}
     		this.clockinContentService.publishTodayClockinContent(custId, clockinContentDto.getLabelId(), clockinContentDto.getContent(), listPicUrl, clockinContentDto.getIsPublic());
     		response.setSuccess("发布打卡日志成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
}
