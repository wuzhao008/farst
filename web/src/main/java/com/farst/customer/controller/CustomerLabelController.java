package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService; 
import com.farst.customer.entity.CustomerLabel;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;  
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.entity.ClockinSetting;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.service.IClockinSettingService;
import com.farst.clockin.vo.AllClockinLabelVo;
import com.farst.clockin.vo.ClockinLabelSettingVo;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户打卡标签 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户打卡标签"})
@RestController
@RequestMapping("/customer/customerLabel")
public class CustomerLabelController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerLabelService customerLabelService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
 	
    @Autowired
    private IClockinLabelService clockinLabelService;
    
    @Autowired
    private IClockinSettingService clockinSettingService; 
    
    /**
     * 根据用户查询所有当前用户还未选择的标签信息
     */
    @ApiOperation(value = "所有还未选择的标签习惯信息")
    @GetMapping(value = "/getAllListLabel")
    public RestResponse<List<AllClockinLabelVo>> getListLabel(@RequestHeader("tokenid") String tokenid){
      	 RestResponse<List<AllClockinLabelVo>> response = new RestResponse<>();
      	List<AllClockinLabelVo> listSCLVo = new ArrayList<AllClockinLabelVo>();
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		 listSCLVo = this.customerLabelService.getListSelectClockinLabelVo(custId);
        	 response.setSuccess(listSCLVo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    /**
     * 查询我的标签习惯设置信息列表
     */
    @ApiOperation(value = "我的标签习惯设置列表信息")
    @GetMapping(value = "/getMyListLabelSetting")
    public RestResponse<List<ClockinLabelSettingVo>> getMyListLabelSetting(@RequestHeader("tokenid") String tokenid){
      	 RestResponse<List<ClockinLabelSettingVo>> response = new RestResponse<>();
      	List<ClockinLabelSettingVo> listMyClsVo = new ArrayList<ClockinLabelSettingVo>();
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		 List<CustomerLabel> listCl = this.customerLabelService.getListCustomerLabel(custId);
     		 if(CollectionUtils.isNotEmpty(listCl)) {
     			 for(CustomerLabel cl : listCl) {
      				ClockinLabel label = this.clockinLabelService.getById(cl.getClockinLabelId());
     				ClockinSetting setting = this.clockinSettingService.getLatestClockingSettingBy(cl.getId());
      				ClockinLabelSettingVo clsVo = new ClockinLabelSettingVo();  
     				clsVo.setClockinLabel(label);
     				clsVo.setClockinSetting(setting);
     				listMyClsVo.add(clsVo);
     			 }
     		 }
        	 response.setSuccess(listMyClsVo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    /**
     * 根据ID查询标签习惯设置信息
     */
    @ApiOperation(value = "根据ID查询标签习惯设置信息")
    @GetMapping(value = "/getLabelSettingByCustomerLabelId")
    public RestResponse<ClockinLabelSettingVo> getLabelSettingById(@RequestHeader("tokenid") String tokenid,@RequestParam("customerLabelId") Integer customerLabelId){
      	 RestResponse<ClockinLabelSettingVo> response = new RestResponse<>(); 
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		 CustomerLabel cl = this.customerLabelService.getById(customerLabelId);
     		 ClockinSetting setting = this.clockinSettingService.getLatestClockingSettingBy(cl.getId());
     		 if(cl.getCustomerInfoId() != custId || cl.getStatus() != 0) {
     			 response.setErrorMsg("非法请求");
     			 return response; 
     		 }
     		 ClockinLabel label = this.clockinLabelService.getById(cl.getClockinLabelId());
     		 ClockinLabelSettingVo clsVo = new ClockinLabelSettingVo();
     		 clsVo.setClockinLabel(label);
     		 clsVo.setClockinSetting(setting);
        	 response.setSuccess(clsVo); 
             return response;
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    

    @ApiOperation(value = "选择我的标签并保存")
    @PostMapping(value = "/selectMyLabel")
    public RestResponse<String> selectMyLabel(@RequestHeader("tokenid") String tokenid,@RequestParam("labelIds") String labelIds){
    	RestResponse<String> response = new RestResponse<>();
    	if(StringUtils.isEmpty(labelIds)) {
    		response.setErrorMsg("至少需要选择一个标签");
    	}
    	try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		List<Integer> listLabelId = new ArrayList<Integer>();
    		String[] arrLabelId = labelIds.split(",");
    		for(String labelId : arrLabelId) {
    			listLabelId.add(Integer.valueOf(labelId));
    		}
    		this.customerLabelService.addCustomerLabel(custId, listLabelId);
    		response.setSuccess(null, "选择标签并保存成功");
    	}catch(Exception e) {
    		response.setErrorMsg(e.getMessage());
    	}
    	return response;
    }
    

 
}
