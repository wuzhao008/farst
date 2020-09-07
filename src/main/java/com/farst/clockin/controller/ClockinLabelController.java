package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.entity.ClockinLabel;
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
    * 打卡标签 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡标签"})
@RestController
@RequestMapping("/clockin/clockinLabel")
public class ClockinLabelController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IClockinLabelService clockinLabelService;
 	
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    public RestResponse<IPage<ClockinLabel>> findListByPage(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinLabel>> response = new RestResponse<>();
    	IPage<ClockinLabel> page = new Page<ClockinLabel>(pageNum, pageSize);
    	QueryWrapper<ClockinLabel> wrapper = new QueryWrapper<ClockinLabel>();
    	ClockinLabel clockinLabel = new ClockinLabel();
    	wrapper.setEntity(clockinLabel);
    	try {
	    	page = this.clockinLabelService.page(page, wrapper);
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
    public RestResponse<ClockinLabel> getById(@RequestParam("id") Integer id){
      	 RestResponse<ClockinLabel> response = new RestResponse<>();
         try {
            ClockinLabel clockinLabel = this.clockinLabelService.getById(id);
            response.setSuccess(clockinLabel);
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
    public RestResponse<ClockinLabel> add(@RequestBody ClockinLabel clockinLabel){
    	 RestResponse<ClockinLabel> response = new RestResponse<>();
         try {
            clockinLabelService.save(clockinLabel);
            response.setSuccess(clockinLabel);
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
    public RestResponse<ClockinLabel> update(@RequestBody ClockinLabel clockinLabel){
         RestResponse<ClockinLabel> response = new RestResponse<>();
         try {
            clockinLabelService.saveOrUpdate(clockinLabel);
            response.setSuccess(clockinLabel);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
 
}
