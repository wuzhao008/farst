package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinSettingService;
import com.farst.clockin.entity.ClockinSetting;
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
    * 打卡设置 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡设置"})
@RestController
@RequestMapping("/clockin/clockinSetting")
public class ClockinSettingController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinSettingService clockinSettingService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinSetting>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinSetting>> response = new RestResponse<>();
    	IPage<ClockinSetting> page = new Page<ClockinSetting>(pageNum, pageSize);
    	QueryWrapper<ClockinSetting> wrapper = new QueryWrapper<ClockinSetting>();
    	ClockinSetting clockinSetting = new ClockinSetting();
    	wrapper.setEntity(clockinSetting);
    	try {
	    	page = this.clockinSettingService.page(page, wrapper);
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
    public RestResponse<ClockinSetting> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinSetting> response = new RestResponse<>();
         try {
            ClockinSetting clockinSetting = this.clockinSettingService.getById(id);
            response.setSuccess(clockinSetting);
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
    public RestResponse<ClockinSetting> add(@RequestBody ClockinSetting clockinSetting){
    	 RestResponse<ClockinSetting> response = new RestResponse<>();
         try {
            clockinSettingService.save(clockinSetting);
            response.setSuccess(clockinSetting);
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
    public RestResponse<ClockinSetting> update(@RequestBody ClockinSetting clockinSetting){
         RestResponse<ClockinSetting> response = new RestResponse<>();
         try {
            clockinSettingService.saveOrUpdate(clockinSetting);
            response.setSuccess(clockinSetting);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
