package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerSuggestService;
import com.farst.customer.entity.CustomerSuggest;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户建议 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-26
 * @version v1.0
 */
@Api(tags = {"用户建议"})
@RestController
@RequestMapping("/customer/customerSuggest")
public class CustomerSuggestController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ICustomerSuggestService customerSuggestService;
    @Autowired
    private ICustomerInfoService customerInfoService;
 	 
 
    /**
     * 提建议
     */
    @ApiOperation(value = "提建议")
    @PostMapping(value = "/addSuggest")
    public RestResponse<String> addSuggest(@RequestHeader("tokenid") String tokenid,@RequestBody String content){
    	 RestResponse<String> response = new RestResponse<>();
         try {
       		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	CustomerSuggest customerSuggest = new CustomerSuggest();
        	customerSuggest.setContent(content);
        	customerSuggest.setCreateDate(new Date());
        	customerSuggest.setCustomerInfoId(custId);
        	customerSuggest.setStatus(0);
            customerSuggestService.save(customerSuggest);
            response.setSuccess("收到建议，及时回复");
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
  
 
}
