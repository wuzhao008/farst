package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.customer.service.ICustomerLabelService;
import com.farst.customer.entity.CustomerLabel;
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
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<CustomerLabel>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<CustomerLabel>> response = new RestResponse<>();
    	IPage<CustomerLabel> page = new Page<CustomerLabel>(pageNum, pageSize);
    	QueryWrapper<CustomerLabel> wrapper = new QueryWrapper<CustomerLabel>();
    	CustomerLabel customerLabel = new CustomerLabel();
    	wrapper.setEntity(customerLabel);
    	try {
	    	page = this.customerLabelService.page(page, wrapper);
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
    public RestResponse<CustomerLabel> getById(@RequestParam("id") Integer id){
      	 RestResponse<CustomerLabel> response = new RestResponse<>();
         try {
            CustomerLabel customerLabel = this.customerLabelService.getById(id);
            response.setSuccess(customerLabel);
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
    public RestResponse<CustomerLabel> add(@RequestBody CustomerLabel customerLabel){
    	 RestResponse<CustomerLabel> response = new RestResponse<>();
         try {
            customerLabelService.save(customerLabel);
            response.setSuccess(customerLabel);
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
    public RestResponse<CustomerLabel> update(@RequestBody CustomerLabel customerLabel){
         RestResponse<CustomerLabel> response = new RestResponse<>();
         try {
            customerLabelService.saveOrUpdate(customerLabel);
            response.setSuccess(customerLabel);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
