package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerMessageService;
import com.farst.customer.vo.CustomerMessageVo; 
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerMessage;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
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
    public RestResponse<IPage<CustomerMessageVo>> pageMyMessage(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerMessageVo>> response = new RestResponse<>();
    	IPage<CustomerMessage> pageCm = new Page<CustomerMessage>(pageNum, pageSize); 
    	IPage<CustomerMessageVo> pageCmVo = new Page<CustomerMessageVo>(pageNum, pageSize);
    	
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		pageCm = this.customerMessageService.getPageMyMessage(pageCm, custId);
     		pageCmVo.setPages(pageCm.getPages());
     		pageCmVo.setTotal(pageCm.getTotal());
	    	
	    	List<CustomerMessage> listCm = pageCm.getRecords();
	    	List<CustomerMessageVo> listCmVo = new ArrayList<CustomerMessageVo>();
      		List<Integer> listCustId = new ArrayList<Integer>();
      		List<CustomerInfo> listCust = null;

      		if(CollectionUtils.isNotEmpty(listCm)) {
      			listCm.forEach(cm->{ 
      				listCustId.add(cm.getSourceCustomerInfoId()); 
      			});
          		listCust = this.customerInfoService.getListCustomerInfoBy(listCustId);
      			
          		for(CustomerMessage cm : listCm) {
      				CustomerMessageVo cmVo = new CustomerMessageVo();
      				cmVo.setCustomerMessage(cm);
      				
      				//构造用户信息
      				if(CollectionUtils.isNotEmpty(listCust)) {
      					for(CustomerInfo cust : listCust) {
      						if(cust.getId().equals(cm.getSourceCustomerInfoId())) {
      							cmVo.setSourceCustomerInfo(cust);
      							break;
      						}
      					}
      				}
      				listCmVo.add(cmVo);
      			}
      		}
      		pageCmVo.setRecords(listCmVo);
      		response.setSuccess(pageCmVo); 
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
  

    @ApiOperation(value = "发送系统消息-【暂时替代后台系统发送】")
    @PostMapping(value = "/sendSysMessage")
    public RestResponse<String> sendSysMessage(@RequestHeader("tokenid") String tokenid,@RequestBody String content){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId(); 
    		if(custId != 0) {
    			response.setErrorMsg("非法请求，必须系统管理员才能操作");
    			return response;
    		}
    		IPage<CustomerInfo> pageCi = new Page<CustomerInfo>(1, 1);
    		pageCi = this.customerInfoService.page(pageCi);
    		long total = pageCi.getTotal();
    		
    		for(long i =1; (i-1) * 100 < total ;i++ ) {
    			pageCi = new Page<CustomerInfo>(i, 100);
    			pageCi = this.customerInfoService.page(pageCi);
    			List<CustomerInfo> listCi = pageCi.getRecords();
    			List<CustomerMessage> listCm = new ArrayList<CustomerMessage>();
    			if(CollectionUtils.isNotEmpty(listCi)) {
    				for(CustomerInfo ci: listCi) {
    					CustomerMessage cm = new CustomerMessage();
    					cm.setContent(content);
    					cm.setCreateDate(new Date());
    					cm.setCustomerInfoId(ci.getId());
    					cm.setMessageType(0);
    					cm.setReadStatus(0);
    					cm.setStatus(0);
    					cm.setSourceCustomerInfoId(custId);
    					listCm.add(cm);
    				}
    			}
    			this.customerMessageService.saveBatch(listCm);
    		}
    		
    		response.setSuccess("系统消息发送成功");
    	}catch(Exception e) {
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
    

    @ApiOperation(value = "清空消息")
    @PostMapping(value = "/deleteAllMessage")
    public RestResponse<String> deleteAllMessage(@RequestHeader("tokenid") String tokenid){
    	RestResponse<String> response = new RestResponse<>();
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId(); 
    		this.customerMessageService.deleteAllMessage(custId);
    		response.setSuccess("清空消息成功");
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
