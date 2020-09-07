package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerFollowService;
import com.farst.customer.entity.CustomerFollow;
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
    * 用户关注 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户关注"})
@RestController
@RequestMapping("/customer/customerFollow")
public class CustomerFollowController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ICustomerFollowService customerFollowService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<CustomerFollow>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerFollow>> response = new RestResponse<>();
    	IPage<CustomerFollow> page = new Page<CustomerFollow>(pageNum, pageSize);
    	QueryWrapper<CustomerFollow> wrapper = new QueryWrapper<CustomerFollow>();
    	CustomerFollow customerFollow = new CustomerFollow();
    	wrapper.setEntity(customerFollow);
    	try {
	    	page = this.customerFollowService.page(page, wrapper);
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
    public RestResponse<CustomerFollow> getById(@RequestParam("id") Integer id){
      	 RestResponse<CustomerFollow> response = new RestResponse<>();
         try {
            CustomerFollow customerFollow = this.customerFollowService.getById(id);
            response.setSuccess(customerFollow);
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
    public RestResponse<CustomerFollow> add(@RequestBody CustomerFollow customerFollow){
    	 RestResponse<CustomerFollow> response = new RestResponse<>();
         try {
            customerFollowService.save(customerFollow);
            response.setSuccess(customerFollow);
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
    public RestResponse<CustomerFollow> update(@RequestBody CustomerFollow customerFollow){
         RestResponse<CustomerFollow> response = new RestResponse<>();
         try {
            customerFollowService.saveOrUpdate(customerFollow);
            response.setSuccess(customerFollow);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
