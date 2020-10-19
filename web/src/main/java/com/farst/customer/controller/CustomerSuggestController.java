package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerMessageService;
import com.farst.customer.service.ICustomerSuggestService;
import com.farst.customer.entity.CustomerMessage;
import com.farst.customer.entity.CustomerSuggest;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.utils.StringUtils;
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
    @Autowired
    private ICustomerMessageService customerMessageService;
 	 
 
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
        	customerSuggest.setOperStatus(0);
        	customerSuggest.setReply(null);
            customerSuggestService.save(customerSuggest);
            response.setSuccess("收到建议，及时回复");
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    

    /**
     * 删除建议记录
     */
    @ApiOperation(value = "删除建议记录")
    @PostMapping(value = "/deleteSuggest")
    public RestResponse<String> deleteSuggest(@RequestHeader("tokenid") String tokenid,@RequestParam("id") Integer id){
    	 RestResponse<String> response = new RestResponse<>();
         try {
       		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	CustomerSuggest cs = this.customerSuggestService.getById(id);
        	if(cs == null || cs.getStatus() != 0 || cs.getCustomerInfoId().equals(custId) == false) {
        		response.setErrorMsg("非法操作");
        		return response;
        	}
        	this.customerSuggestService.deleteCustomerSuggest(id);
            response.setSuccess("删除成功");
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    

    /**
     * 查询我的反馈分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/pageMySuggest")
    public RestResponse<IPage<CustomerSuggest>> pageMySuggest(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerSuggest>> response = new RestResponse<>();
    	IPage<CustomerSuggest> page = new Page<CustomerSuggest>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.customerSuggestService.getPageMySuggest(page, custId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
  

    /**
     * 回复建议
     */
    @ApiOperation(value = "回复建议-【由于无后台系统，暂时作为建议回复功能】")
    @PostMapping(value = "/replySuggest")
    public RestResponse<String> replySuggest(@RequestParam("suggestId") Integer suggestId,@RequestBody String content){
    	 RestResponse<String> response = new RestResponse<>();
         try {
        	CustomerSuggest cs = this.customerSuggestService.getById(suggestId);
        	if(cs == null || cs.getStatus().intValue() != 0) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	if(StringUtils.isEmpty(content)) {
        		response.setErrorMsg("回复内容不能为空");
        		return response;
        	}
        	cs.setReply(content);
        	cs.setOperStatus(1);
        	cs.setReplyDate(new Date());
        	this.customerSuggestService.saveOrUpdate(cs);
        	

        	CustomerMessage cm = new CustomerMessage();
        	cm.setContent(content);
        	cm.setCreateDate(new Date());
        	//以惯性管理员的身份，固定为0的用户ID
        	cm.setCustomerInfoId(0);
        	cm.setMessageType(1);
        	cm.setObjectContent(cs.getContent());
        	cm.setObjectId(cs.getId());
        	cm.setReadStatus(0);
        	cm.setStatus(0);
        	this.customerMessageService.save(cm);
        	
            response.setSuccess("回复建议成功");
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    
}
