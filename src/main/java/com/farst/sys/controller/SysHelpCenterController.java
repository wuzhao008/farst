package com.farst.sys.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.sys.service.ISysHelpCenterService;
import com.farst.sys.entity.SysHelpCenter;
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
    * 帮助中心 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"帮助中心"})
@RestController
@RequestMapping("/sys/sysHelpCenter")
public class SysHelpCenterController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ISysHelpCenterService sysHelpCenterService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<SysHelpCenter>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<SysHelpCenter>> response = new RestResponse<>();
    	IPage<SysHelpCenter> page = new Page<SysHelpCenter>(pageNum, pageSize);
    	QueryWrapper<SysHelpCenter> wrapper = new QueryWrapper<SysHelpCenter>();
    	SysHelpCenter sysHelpCenter = new SysHelpCenter();
    	wrapper.setEntity(sysHelpCenter);
    	try {
	    	page = this.sysHelpCenterService.page(page, wrapper);
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
    public RestResponse<SysHelpCenter> getById(@RequestParam("id") Integer id){
      	 RestResponse<SysHelpCenter> response = new RestResponse<>();
         try {
            SysHelpCenter sysHelpCenter = this.sysHelpCenterService.getById(id);
            response.setSuccess(sysHelpCenter);
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
    public RestResponse<SysHelpCenter> add(@RequestBody SysHelpCenter sysHelpCenter){
    	 RestResponse<SysHelpCenter> response = new RestResponse<>();
         try {
            sysHelpCenterService.save(sysHelpCenter);
            response.setSuccess(sysHelpCenter);
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
    public RestResponse<SysHelpCenter> update(@RequestBody SysHelpCenter sysHelpCenter){
         RestResponse<SysHelpCenter> response = new RestResponse<>();
         try {
            sysHelpCenterService.saveOrUpdate(sysHelpCenter);
            response.setSuccess(sysHelpCenter);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
