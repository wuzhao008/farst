package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinContentUpService;
import com.farst.clockin.entity.ClockinContentUp;
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
    * 打卡内容顶UP 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡内容顶UP"})
@RestController
@RequestMapping("/clockin/clockinContentUp")
public class ClockinContentUpController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinContentUpService clockinContentUpService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinContentUp>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinContentUp>> response = new RestResponse<>();
    	IPage<ClockinContentUp> page = new Page<ClockinContentUp>(pageNum, pageSize);
    	QueryWrapper<ClockinContentUp> wrapper = new QueryWrapper<ClockinContentUp>();
    	ClockinContentUp clockinContentUp = new ClockinContentUp();
    	wrapper.setEntity(clockinContentUp);
    	try {
	    	page = this.clockinContentUpService.page(page, wrapper);
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
    public RestResponse<ClockinContentUp> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinContentUp> response = new RestResponse<>();
         try {
            ClockinContentUp clockinContentUp = this.clockinContentUpService.getById(id);
            response.setSuccess(clockinContentUp);
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
    public RestResponse<ClockinContentUp> add(@RequestBody ClockinContentUp clockinContentUp){
    	 RestResponse<ClockinContentUp> response = new RestResponse<>();
         try {
            clockinContentUpService.save(clockinContentUp);
            response.setSuccess(clockinContentUp);
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
    public RestResponse<ClockinContentUp> update(@RequestBody ClockinContentUp clockinContentUp){
         RestResponse<ClockinContentUp> response = new RestResponse<>();
         try {
            clockinContentUpService.saveOrUpdate(clockinContentUp);
            response.setSuccess(clockinContentUp);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
