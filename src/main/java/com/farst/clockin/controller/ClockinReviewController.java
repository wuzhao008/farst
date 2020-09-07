package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.clockin.entity.ClockinReview;
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
    * 打卡内容评论 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡内容评论"})
@RestController
@RequestMapping("/clockin/clockinReview")
public class ClockinReviewController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinReviewService clockinReviewService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinReview>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinReview>> response = new RestResponse<>();
    	IPage<ClockinReview> page = new Page<ClockinReview>(pageNum, pageSize);
    	QueryWrapper<ClockinReview> wrapper = new QueryWrapper<ClockinReview>();
    	ClockinReview clockinReview = new ClockinReview();
    	wrapper.setEntity(clockinReview);
    	try {
	    	page = this.clockinReviewService.page(page, wrapper);
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
    public RestResponse<ClockinReview> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinReview> response = new RestResponse<>();
         try {
            ClockinReview clockinReview = this.clockinReviewService.getById(id);
            response.setSuccess(clockinReview);
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
    public RestResponse<ClockinReview> add(@RequestBody ClockinReview clockinReview){
    	 RestResponse<ClockinReview> response = new RestResponse<>();
         try {
            clockinReviewService.save(clockinReview);
            response.setSuccess(clockinReview);
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
    public RestResponse<ClockinReview> update(@RequestBody ClockinReview clockinReview){
         RestResponse<ClockinReview> response = new RestResponse<>();
         try {
            clockinReviewService.saveOrUpdate(clockinReview);
            response.setSuccess(clockinReview);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
