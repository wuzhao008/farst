package com.farst.customer.controller;

import org.springframework.web.bind.annotation.*; 
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.vo.CustomerHabbitSettingVo;
import com.farst.customer.service.ICustomerHabbitSettingService;
import com.farst.customer.service.ICustomerHabbitService;
import com.farst.customer.entity.CustomerHabbitSetting;
import com.farst.customer.dto.CustomerHabbitDto;
import com.farst.customer.entity.CustomerHabbit;
import com.farst.common.web.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.service.IClockinLabelService; 
import com.farst.common.utils.DateUtils;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 用户打卡习惯 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"用户习惯"})
@RestController
@RequestMapping("/customer/customerHabbit")
public class CustomerHabbitController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ICustomerHabbitService customerHabbitService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
 	
    @Autowired
    private IClockinLabelService clockinLabelService;
    
    @Autowired
    private ICustomerHabbitSettingService customerHabbitSettingService; 
    

    
    /**
     * 查询我的习惯设置信息列表
     */
    @ApiOperation(value = "我的习惯设置列表信息")
    @GetMapping(value = "/getMyListHabbitSetting")
    public RestResponse<List<CustomerHabbitSettingVo>> getMyListHabbitSetting(@RequestHeader("tokenid") String tokenid){
      	 RestResponse<List<CustomerHabbitSettingVo>> response = new RestResponse<>();
      	List<CustomerHabbitSettingVo> listMyChsVo = new ArrayList<CustomerHabbitSettingVo>();
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		 List<CustomerHabbit> listCh = this.customerHabbitService.getListCustomerHabbit(custId);
     		 if(CollectionUtils.isNotEmpty(listCh)) {
     			 for(CustomerHabbit ch : listCh) {
      				ClockinLabel label = this.clockinLabelService.getById(ch.getClockinLabelId());
     				CustomerHabbitSetting setting = this.customerHabbitSettingService.getLatestHabbitSettingBy(ch.getId());
      				CustomerHabbitSettingVo chsVo = new CustomerHabbitSettingVo(); 
      				chsVo.setCustomerHabbit(ch);
      				chsVo.setClockinLabel(label);
      				chsVo.setCustomerHabbitSetting(setting);
     				listMyChsVo.add(chsVo);
     			 }
     		 }
        	 response.setSuccess(listMyChsVo);
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    /**
     * 根据习惯ID查询习惯设置信息
     */
    @ApiOperation(value = "根据ID查询习惯设置信息")
    @GetMapping(value = "/getHabbitSettingByCustomerHabbitId")
    public RestResponse<CustomerHabbitSettingVo> getHabbitSettingByCustomerHabbitId(@RequestHeader("tokenid") String tokenid,@RequestParam("customerHabbitId") Integer customerHabbitId){
      	 RestResponse<CustomerHabbitSettingVo> response = new RestResponse<>(); 
         try {
     		 Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		 CustomerHabbit ch = this.customerHabbitService.getById(customerHabbitId);
     		 CustomerHabbitSetting setting = this.customerHabbitSettingService.getLatestHabbitSettingBy(customerHabbitId);
     		 if(ch.getCustomerInfoId() != custId || ch.getStatus() != 0) {
     			 response.setErrorMsg("非法请求");
     			 return response; 
     		 }
     		 ClockinLabel label = this.clockinLabelService.getById(ch.getClockinLabelId());
     		 CustomerHabbitSettingVo chsVo = new CustomerHabbitSettingVo();
     		 chsVo.setClockinLabel(label);
     		 chsVo.setCustomerHabbitSetting(setting);
     		 chsVo.setCustomerHabbit(ch);
        	 response.setSuccess(chsVo); 
             return response;
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
    }
    
    

    /**
     * 保存我的习惯规则
     */
    @ApiOperation(value = "保存我的习惯规则")
    @PostMapping(value = "/saveCustomerHabbit")
    public RestResponse<String> setClockinRule(@RequestHeader("tokenid") String tokenid,@RequestBody CustomerHabbitDto customerHabbitDto){
         RestResponse<String> response = new RestResponse<>();
         try {  
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	//如果是新建习惯，则直接保存相关的信息
        	if(customerHabbitDto.getId() == null) {
        		CustomerHabbit customerHabbit = new CustomerHabbit();
        		customerHabbit.setClockinLabelId(customerHabbitDto.getClockinLabelId());
        		customerHabbit.setCreateDate(new Date());
        		customerHabbit.setCustomerInfoId(custId);
        		customerHabbit.setHabbitName(customerHabbitDto.getHabbitName());
        		customerHabbit.setPopupLog(customerHabbitDto.getPopupLog());
        		customerHabbit.setStatus(0);
        		this.customerHabbitService.save(customerHabbit); 
        		
        		CustomerHabbitSetting customerHabbitSetting = new CustomerHabbitSetting();
     			Date curFreqDateStart = null;
     			if(customerHabbitDto.getFreqType().intValue() == 1 ) {
     				curFreqDateStart = DateUtils.getWeekStart();
     			}else if(customerHabbitDto.getFreqType().intValue() == 2) {
     				curFreqDateStart = DateUtils.getSpecialDate(new Date(), 3);
     			}else if(customerHabbitDto.getFreqType().intValue() == 3) {
     				curFreqDateStart = DateUtils.getCurrYearFirst();
     			}
     			customerHabbitSetting.setCreateDate(new Date());
     			customerHabbitSetting.setCustomerHabbitId(customerHabbit.getId());
     			customerHabbitSetting.setFreqStartDate(curFreqDateStart);
     			customerHabbitSetting.setFreqType(customerHabbitDto.getFreqType());
     			customerHabbitSetting.setFreqValue(customerHabbitDto.getFreqValue());
     			customerHabbitSetting.setStatus(0);
     			this.customerHabbitSettingService.save(customerHabbitSetting);
                response.setSuccess("新增习惯成功");        		
        	}else { 
	        	CustomerHabbit customerHabbit = this.customerHabbitService.getById(customerHabbitDto.getId());
	     		CustomerHabbitSetting customerHabbitSetting = this.customerHabbitSettingService.getLatestHabbitSettingBy(customerHabbitDto.getId());
	     		customerHabbit.setClockinLabelId(customerHabbitDto.getClockinLabelId());
	     		customerHabbit.setHabbitName(customerHabbitDto.getHabbitName());
	     		customerHabbit.setLastEditTime(new Date());
	     		customerHabbit.setPopupLog(customerHabbitDto.getPopupLog());
	     		this.customerHabbitService.saveOrUpdate(customerHabbit);
	     		
     			//如果修改内容没有变，则直接返回
     			if(customerHabbitSetting.getFreqType().equals(customerHabbitDto.getFreqType())
     				&&customerHabbitSetting.getFreqValue().equals(customerHabbitDto.getFreqValue())) {
     				response.setSuccess("修改习惯成功");
     				return response;
     			}
 				if(customerHabbitDto.getFreqType().equals(customerHabbitSetting.getFreqType()) == false) {
 					response.setErrorMsg("习惯周期类型不能更改！");
 					return response;
 				}
 				
     			String strCurFreqStartDate = DateUtils.DatetoString(customerHabbitSetting.getFreqStartDate(), "yyyy-MM-dd");
     			Date dateNextFreqStartDate = null;
     			if(customerHabbitDto.getFreqType().intValue() == 1 ) {
     				dateNextFreqStartDate = DateUtils.getNextMonday(new Date());
     			}else if(customerHabbitDto.getFreqType().intValue() == 2) {
     				dateNextFreqStartDate = DateUtils.addTime(DateUtils.getSpecialDate(new Date(), 3), 2,1);
     			}else if(customerHabbitDto.getFreqType().intValue() == 3) {
     				dateNextFreqStartDate = DateUtils.getNextYearFirst();
     			} 
     			String strNextFreqStartDate = DateUtils.DatetoString(dateNextFreqStartDate, "yyyy-MM-dd");
     			//如果最新规则日期是下一周期的，则直接更新
     			if(strCurFreqStartDate.equals(strNextFreqStartDate)) {
     				customerHabbitSetting.setLastEditTime(new Date());
     				customerHabbitSetting.setFreqValue(customerHabbitDto.getFreqValue());
     				this.customerHabbitSettingService.saveOrUpdate(customerHabbitSetting);
     				response.setSuccess("修改习惯成功，打卡目标将在下一周期生效");
     			}
     			//如果下一个周期还没有设置规则，则新增下一个周期规则
     			else if(strNextFreqStartDate.compareTo(strCurFreqStartDate)>0){  
     				customerHabbitSetting = new CustomerHabbitSetting();  
     				customerHabbitSetting.setCreateDate(new Date());
     				customerHabbitSetting.setCustomerHabbitId(customerHabbit.getId());
     				customerHabbitSetting.setFreqStartDate(dateNextFreqStartDate);
     				customerHabbitSetting.setFreqType(customerHabbitDto.getFreqType());
     				customerHabbitSetting.setFreqValue(customerHabbitDto.getFreqValue());
     				customerHabbitSetting.setStatus(0);
     				this.customerHabbitSettingService.save(customerHabbitSetting);
     				response.setSuccess("修改习惯成功，打卡目标将在下一周期生效");
     			}else {
     				response.setErrorMsg("编辑习惯出现异常");
     				return response;
     			} 
        	}
         } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
         }
         return response;
     }
    

 
}
