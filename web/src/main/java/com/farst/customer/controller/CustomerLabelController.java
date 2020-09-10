package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerLabel;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.clockin.vo.SelectClockinLabelVo;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户打卡标签 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户打卡标签"})
@RestController
@RequestMapping("/customer/customerLabel")
public class CustomerLabelController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerLabelService customerLabelService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
 	
    /**
     * 根据id查询所有标签信息
     */
    @ApiOperation(value = "根据客户id查询所有标签信息")
    @GetMapping(value = "/getListLabel")
    public RestResponse<List<SelectClockinLabelVo>> getById(@RequestHeader("tokenid") String tokenid){
      	 RestResponse<List<SelectClockinLabelVo>> response = new RestResponse<>();
      	List<SelectClockinLabelVo> listSelectClockinLabelVo = new ArrayList<SelectClockinLabelVo>();
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	 listSelectClockinLabelVo = this.customerLabelService.getListSelectClockinLabelVo(custId);
        	 response.setSuccess(listSelectClockinLabelVo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
 
}
