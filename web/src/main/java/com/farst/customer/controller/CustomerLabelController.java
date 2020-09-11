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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
    @ApiOperation(value = "得到所有标签信息-含用户是否已选择标记")
    @GetMapping(value = "/getListLabel")
    public RestResponse<List<SelectClockinLabelVo>> getListLabel(@RequestHeader("tokenid") String tokenid){
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
    

    @ApiOperation(value = "选择标签并保存")
    @PostMapping(value = "/selectLabel")
    public RestResponse<String> editSex(@RequestHeader("tokenid") String tokenid,@RequestParam("labelIds") String labelIds){
    	RestResponse<String> response = new RestResponse<>();
    	if(StringUtils.isEmpty(labelIds)) {
    		response.setErrorMsg("至少需要选择一个标签");
    	}
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		List<Integer> listLabelId = new ArrayList<Integer>();
    		String[] arrLabelId = labelIds.split(",");
    		for(String labelId : arrLabelId) {
    			listLabelId.add(Integer.valueOf(labelId));
    		}
    		this.customerLabelService.updCustomerLabel(custId, listLabelId);
    		response.setSuccess(null, "选择标签并保存成功");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
    
 
}
