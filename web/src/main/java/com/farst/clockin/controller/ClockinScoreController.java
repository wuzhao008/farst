package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.service.IClockinContentService; 
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.vo.ClockinCurMonthRecordVo;
import com.farst.clockin.vo.ClockinTrendRecordVo;
import com.farst.clockin.vo.ClockinTrendStatisticsVo;
import com.farst.common.web.response.RestResponse;
import com.farst.customer.entity.CustomerHabbitSetting;
import com.farst.customer.entity.CustomerHabbit;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerHabbitSettingService;
import com.farst.customer.service.ICustomerHabbitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 打卡成绩 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡成绩"})
@RestController
@RequestMapping("/clockin/clockinContent")
public class ClockinScoreController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private IClockinContentService clockinContentService;   
    
    @Autowired
    private ICustomerHabbitService customerHabbitService;
    
    @Autowired
    private IClockinLabelService clockinLabelService;
    
    @Autowired
    private ICustomerHabbitSettingService customerHabbitSettingService;;
 	
    /**
     * 查询本月的打卡记录
     */
    @ApiOperation(value = "查询本月打卡记录-进入后默认数据")
    @GetMapping(value = "/listDefaultCurMonthClockinRecord")
    public RestResponse<List<ClockinCurMonthRecordVo>> listDefaultCurMonthClockinRecord(@RequestHeader("tokenid") String tokenid){
    	RestResponse<List<ClockinCurMonthRecordVo>> response = new RestResponse<>();
    	List<ClockinCurMonthRecordVo> listCcmrVo = new ArrayList<ClockinCurMonthRecordVo>();
        try {
    		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		 List<CustomerHabbit> listCh = this.customerHabbitService.getListCustomerHabbit(custId);
    		 if(CollectionUtils.isNotEmpty(listCh)) {
    			 for(CustomerHabbit ch : listCh) {
     				ClockinLabel label = this.clockinLabelService.getById(ch.getClockinLabelId());
    				CustomerHabbitSetting setting = this.customerHabbitSettingService.getLatestHabbitSettingBy(ch.getId());
     				if(setting != null) {
     					List<ClockinContent> listCc = this.clockinContentService.getCurMonthListClockinContent(custId, ch.getId());
     					List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
     					if(CollectionUtils.isNotEmpty(listCc)) {
     						listCc.forEach(cc ->{
     							Map<String,Object> map = new HashMap<String, Object>();
     							map.put("contentId", cc.getId());
     							map.put("createDate", cc.getCreateDate());
     							listMap.add(map);
     						});
     					}
     					ClockinCurMonthRecordVo ccmrVo = new ClockinCurMonthRecordVo();
     					ccmrVo.setCustomerHabbit(ch);
     					ccmrVo.setClockinLabel(label);
     					ccmrVo.setListClockinContent(listMap);
     					ccmrVo.setClockinDays(listMap.size());
     					listCcmrVo.add(ccmrVo);
     				}
    			 }
    		 }
       	 response.setSuccess(listCcmrVo);
        } catch (Exception e) {
           e.printStackTrace();
           logger.error(e.getMessage());
           response.setErrorMsg(e.getMessage());
        }
        return response;
    }
    
    /**
     * 查询本月的打卡记录
     */
    @ApiOperation(value = "查询本月打卡记录-根据当前格式如2020-09格式的月份查询对应的打卡记录")
    @GetMapping(value = "/clockinRecordByMonth")
    public RestResponse<ClockinCurMonthRecordVo> clockinRecordByMonth(@RequestHeader("tokenid") String tokenid,@RequestParam("customerHabbitId") Integer customerHabbitId,@RequestParam("month") String month){
    	RestResponse<ClockinCurMonthRecordVo> response = new RestResponse<>(); 
        try {
    		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId(); 
    		CustomerHabbit habbit = this.customerHabbitService.getById(customerHabbitId);
			ClockinLabel label = this.clockinLabelService.getById(habbit.getClockinLabelId());  
			List<ClockinContent> listCc = this.clockinContentService.getMonthListClockinContent(custId, customerHabbitId, month);
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			if(CollectionUtils.isNotEmpty(listCc)) {
				listCc.forEach(cc ->{
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("contentId", cc.getId());
					map.put("createDate", cc.getCreateDate());
					listMap.add(map);
				});
			}
			ClockinCurMonthRecordVo ccmrVo = new ClockinCurMonthRecordVo();
			ccmrVo.setCustomerHabbit(habbit);
			ccmrVo.setClockinLabel(label);
			ccmrVo.setListClockinContent(listMap);
			ccmrVo.setClockinDays(listMap.size()); 
       	 	response.setSuccess(ccmrVo);
        } catch (Exception e) {
           e.printStackTrace();
           logger.error(e.getMessage());
           response.setErrorMsg(e.getMessage());
        }
        return response;
    }
    
    /**
     * 查询用户对应各标签按周期统计趋势图
     */
    @ApiOperation(value = "查询用户对应各标签按周期统计趋势图")
    @GetMapping(value = "/listClockinTrendRecord")
    public RestResponse<List<ClockinTrendRecordVo>> listClockinTrendRecord(@RequestHeader("tokenid") String tokenid,@RequestParam("type") Integer type){
    	RestResponse<List<ClockinTrendRecordVo>> response = new RestResponse<>();
    	List<ClockinTrendRecordVo> listCtrVo = new ArrayList<ClockinTrendRecordVo>();
        try {
    		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		 List<CustomerHabbit> listCh = this.customerHabbitService.getListCustomerHabbit(custId);
    		 if(CollectionUtils.isNotEmpty(listCh)) {
    			 for(CustomerHabbit ch : listCh) {
     				ClockinLabel label = this.clockinLabelService.getById(ch.getClockinLabelId());
 					List<ClockinTrendStatisticsVo> listCtsVo = this.clockinContentService.getListClockinTrendStatisticsVo(custId, ch.getId(), type);
 					ClockinTrendRecordVo ctrVo = new ClockinTrendRecordVo();
 					ctrVo.setCustomerHabbit(ch);
 					ctrVo.setClockinLabel(label);
 					ctrVo.setListClockinTrendStatisticsVo(listCtsVo);
 					listCtrVo.add(ctrVo); 
    			 }
    		 }
       	 response.setSuccess(listCtrVo);
        } catch (Exception e) {
           e.printStackTrace();
           logger.error(e.getMessage());
           response.setErrorMsg(e.getMessage());
        }
        return response;
    }
    
    
}
