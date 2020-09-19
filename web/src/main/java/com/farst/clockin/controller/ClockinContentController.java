package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.vo.TodayClockinVo; 
import com.farst.common.web.response.RestResponse; 
import com.farst.customer.service.ICustomerInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 
}
