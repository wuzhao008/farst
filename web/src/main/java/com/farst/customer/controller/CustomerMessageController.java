package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerMessageService;
import com.farst.customer.entity.CustomerMessage;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RestController;
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
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<CustomerMessage>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerMessage>> response = new RestResponse<>();
    	IPage<CustomerMessage> page = new Page<CustomerMessage>(pageNum, pageSize);
    	QueryWrapper<CustomerMessage> wrapper = new QueryWrapper<CustomerMessage>();
    	CustomerMessage customerMessage = new CustomerMessage();
    	wrapper.setEntity(customerMessage);
    	try {
	    	page = this.customerMessageService.page(page, wrapper);
	    	response.setData(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getById")
    public RestResponse<CustomerMessage> getById(@RequestParam("id") Integer id){
      	 RestResponse<CustomerMessage> response = new RestResponse<>();
         try {
            CustomerMessage customerMessage = this.customerMessageService.getById(id);
            response.setSuccess(customerMessage);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public RestResponse<CustomerMessage> add(@RequestBody CustomerMessage customerMessage){
    	 RestResponse<CustomerMessage> response = new RestResponse<>();
         try {
            customerMessageService.save(customerMessage);
            response.setSuccess(customerMessage);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
 
    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/update")
    public RestResponse<CustomerMessage> update(@RequestBody CustomerMessage customerMessage){
         RestResponse<CustomerMessage> response = new RestResponse<>();
         try {
            customerMessageService.saveOrUpdate(customerMessage);
            response.setSuccess(customerMessage);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
