package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinPictureService;
import com.farst.clockin.entity.ClockinPicture;
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
    * 打卡图片内容 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡图片内容"})
@RestController
@RequestMapping("/clockin/clockinPicture")
public class ClockinPictureController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinPictureService clockinPictureService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinPicture>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinPicture>> response = new RestResponse<>();
    	IPage<ClockinPicture> page = new Page<ClockinPicture>(pageNum, pageSize);
    	QueryWrapper<ClockinPicture> wrapper = new QueryWrapper<ClockinPicture>();
    	ClockinPicture clockinPicture = new ClockinPicture();
    	wrapper.setEntity(clockinPicture);
    	try {
	    	page = this.clockinPictureService.page(page, wrapper);
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
    public RestResponse<ClockinPicture> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinPicture> response = new RestResponse<>();
         try {
            ClockinPicture clockinPicture = this.clockinPictureService.getById(id);
            response.setSuccess(clockinPicture);
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
    public RestResponse<ClockinPicture> add(@RequestBody ClockinPicture clockinPicture){
    	 RestResponse<ClockinPicture> response = new RestResponse<>();
         try {
            clockinPictureService.save(clockinPicture);
            response.setSuccess(clockinPicture);
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
    public RestResponse<ClockinPicture> update(@RequestBody ClockinPicture clockinPicture){
         RestResponse<ClockinPicture> response = new RestResponse<>();
         try {
            clockinPictureService.saveOrUpdate(clockinPicture);
            response.setSuccess(clockinPicture);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
