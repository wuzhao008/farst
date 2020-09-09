package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.entity.ClockinContent;
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
    * 打卡文字内容 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡文字内容"})
@RestController
@RequestMapping("/clockin/clockinContent")
public class ClockinContentController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinContentService clockinContentService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinContent>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinContent>> response = new RestResponse<>();
    	IPage<ClockinContent> page = new Page<ClockinContent>(pageNum, pageSize);
    	QueryWrapper<ClockinContent> wrapper = new QueryWrapper<ClockinContent>();
    	ClockinContent clockinContent = new ClockinContent();
    	wrapper.setEntity(clockinContent);
    	try {
	    	page = this.clockinContentService.page(page, wrapper);
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
    public RestResponse<ClockinContent> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinContent> response = new RestResponse<>();
         try {
            ClockinContent clockinContent = this.clockinContentService.getById(id);
            response.setSuccess(clockinContent);
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
    public RestResponse<ClockinContent> add(@RequestBody ClockinContent clockinContent){
    	 RestResponse<ClockinContent> response = new RestResponse<>();
         try {
            clockinContentService.save(clockinContent);
            response.setSuccess(clockinContent);
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
    public RestResponse<ClockinContent> update(@RequestBody ClockinContent clockinContent){
         RestResponse<ClockinContent> response = new RestResponse<>();
         try {
            clockinContentService.saveOrUpdate(clockinContent);
            response.setSuccess(clockinContent);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
