package com.farst.sys.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.sys.service.ISysHelpCenterService;
import com.farst.sys.entity.SysHelpCenter;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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
     * 帮助中心列表
     */
    @ApiOperation(value = "帮助中心列表")
    @GetMapping(value = "/list")
    public RestResponse<List<SysHelpCenter>> list(){
        RestResponse<List<SysHelpCenter>> response = new RestResponse<>();
    	try {
	    	List<SysHelpCenter> listHelp = this.sysHelpCenterService.getListHelp();
	    	response.setSuccess(listHelp);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	 
 
}
