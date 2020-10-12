package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.farst.clockin.service.IClockinLabelService; 
import com.farst.clockin.vo.AllClockinLabelVo; 
import com.farst.common.web.response.RestResponse; 

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList; 
import java.util.List; 
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
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
     * 查询所有的标签信息
     */
    @ApiOperation(value = "所有的标签信息")
    @GetMapping(value = "/getAllListLabel")
    public RestResponse<List<AllClockinLabelVo>> getAllListLabel(){
      	 RestResponse<List<AllClockinLabelVo>> response = new RestResponse<>();
      	List<AllClockinLabelVo> listSclVo = new ArrayList<AllClockinLabelVo>();
         try {
        	 listSclVo = this.clockinLabelService.getListAllClockinLabelVo();
        	 response.setSuccess(listSclVo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
}
