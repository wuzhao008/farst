package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinReviewUpService;
import com.farst.clockin.entity.ClockinReviewUp;
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
    * 打卡评论顶UP 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡评论顶UP"})
@RestController
@RequestMapping("/clockin/clockinReviewUp")
public class ClockinReviewUpController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinReviewUpService clockinReviewUpService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinReviewUp>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinReviewUp>> response = new RestResponse<>();
    	IPage<ClockinReviewUp> page = new Page<ClockinReviewUp>(pageNum, pageSize);
    	QueryWrapper<ClockinReviewUp> wrapper = new QueryWrapper<ClockinReviewUp>();
    	ClockinReviewUp clockinReviewUp = new ClockinReviewUp();
    	wrapper.setEntity(clockinReviewUp);
    	try {
	    	page = this.clockinReviewUpService.page(page, wrapper);
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
    public RestResponse<ClockinReviewUp> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinReviewUp> response = new RestResponse<>();
         try {
            ClockinReviewUp clockinReviewUp = this.clockinReviewUpService.getById(id);
            response.setSuccess(clockinReviewUp);
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
    public RestResponse<ClockinReviewUp> add(@RequestBody ClockinReviewUp clockinReviewUp){
    	 RestResponse<ClockinReviewUp> response = new RestResponse<>();
         try {
            clockinReviewUpService.save(clockinReviewUp);
            response.setSuccess(clockinReviewUp);
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
    public RestResponse<ClockinReviewUp> update(@RequestBody ClockinReviewUp clockinReviewUp){
         RestResponse<ClockinReviewUp> response = new RestResponse<>();
         try {
            clockinReviewUpService.saveOrUpdate(clockinReviewUp);
            response.setSuccess(clockinReviewUp);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
