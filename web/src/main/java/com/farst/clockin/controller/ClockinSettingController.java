package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.farst.clockin.service.IClockinSettingService;
import com.farst.clockin.dto.ClockinSettingDto;
import com.farst.clockin.entity.ClockinSetting;
import com.farst.common.web.response.RestResponse;
import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerLabelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.farst.common.utils.DateUtils;
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
    
    @Autowired
    private ICustomerLabelService customerLabelService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
 	  
    /**
     * 修改
     */
    @ApiOperation(value = "设置打卡规则")
    @PostMapping(value = "/setClockinRule")
    public RestResponse<String> setClockinRule(@RequestHeader("tokenid") String tokenid,@RequestBody ClockinSettingDto clockinSettingDto){
         RestResponse<String> response = new RestResponse<>();
         try {  
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	CustomerLabel cl = this.customerLabelService.getById(clockinSettingDto.getCustomerLabelId());
        	
        	if(cl==null || cl.getStatus() != 0 || cl.getCustomerInfoId() != custId) {
        		response.setErrorMsg("非法请求");
        		return response;
        	}
        	 
     		ClockinSetting cs = this.clockinSettingService.getLatestClockingSettingBy(clockinSettingDto.getCustomerLabelId());
     		//当前没有设置规则，则设置当前周期及以后规则；
     		if(cs == null) {
     			cs = new ClockinSetting();
     			Date curFreqDateStart = null;
     			if(clockinSettingDto.getFreqType().intValue() == 1 ) {
     				curFreqDateStart = DateUtils.getWeekStart();
     			}else if(clockinSettingDto.getFreqType().intValue() == 2) {
     				curFreqDateStart = DateUtils.getSpecialDate(new Date(), 3);
     			}else if(clockinSettingDto.getFreqType().intValue() == 3) {
     				curFreqDateStart = DateUtils.getCurrYearFirst();
     			}
     			BeanUtils.copyProperties(clockinSettingDto, cs);
     			cs.setCreateDate(new Date());
     			cs.setCustomerLabelId(clockinSettingDto.getCustomerLabelId());
     			cs.setFreqStartDate(curFreqDateStart);
     			cs.setStatus(0);
     			this.clockinSettingService.save(cs);
                response.setSuccess("习惯规则设置成功");
     		}else {
     			//如果修改内容没有变，则直接返回
     			if(cs.getFreqType().equals(clockinSettingDto.getFreqType())
     				&&cs.getFreqValue().equals(clockinSettingDto.getFreqValue())
     				&&cs.getPopupLog().equals(clockinSettingDto.getPopupLog())) {
     				response.setSuccess("设置成功");
     				return response;
     			}
     			
     			String strCurFreqStartDate = DateUtils.DatetoString(cs.getFreqStartDate(), "yyyy-MM-dd");
     			Date dateNextFreqStartDate = null;
     			if(cs.getFreqType().intValue() == 1 ) {
     				dateNextFreqStartDate = DateUtils.getNextMonday(new Date());
     			}else if(cs.getFreqType().intValue() == 2) {
     				dateNextFreqStartDate = DateUtils.addTime(DateUtils.getSpecialDate(new Date(), 3), 2,1);
     			}else if(cs.getFreqType().intValue() == 3) {
     				dateNextFreqStartDate = DateUtils.getNextYearFirst();
     			} 
     			String strNextFreqStartDate = DateUtils.DatetoString(dateNextFreqStartDate, "yyyy-MM-dd");
     			//如果最新规则日期是下一月的，则直接更新
     			if(strCurFreqStartDate.equals(strNextFreqStartDate)) {
     				if(clockinSettingDto.getFreqType().equals(cs.getFreqType()) == false) {
     					response.setErrorMsg("习惯规则类型不能更改，如想修改，请先删除后重新维护");
     					return response;
     				}
     				//弹出方式最新规则同步修改
     				if(cs.getPopupLog().equals(clockinSettingDto.getPopupLog()) == false) {
     					this.clockinSettingService.updateLatestPopupLog(clockinSettingDto.getCustomerLabelId(), clockinSettingDto.getPopupLog());
     				}
     				BeanUtils.copyProperties(clockinSettingDto, cs);
     				this.clockinSettingService.saveOrUpdate(cs);
     				response.setSuccess("新规则设置成功，将在下一个周期生效");
     			}
     			//如果下一个周期还没有设置规则，则新增下一个周期规则
     			else if(strNextFreqStartDate.compareTo(strCurFreqStartDate)>0){
     				if(clockinSettingDto.getFreqType().equals(cs.getFreqType()) == false) {
     					response.setErrorMsg("习惯规则类型不能更改，如想修改，请先删除后重新维护");
     					return response;
     				}
     				//弹出方式最新的修改
     				if(cs.getPopupLog().equals(clockinSettingDto.getPopupLog()) == false) {
     					this.clockinSettingService.updateLatestPopupLog(clockinSettingDto.getCustomerLabelId(), clockinSettingDto.getPopupLog());
     				}
     				cs = new ClockinSetting(); 
         			BeanUtils.copyProperties(clockinSettingDto, cs);
         			cs.setCreateDate(new Date());
         			cs.setCustomerLabelId(clockinSettingDto.getCustomerLabelId());
         			cs.setFreqStartDate(dateNextFreqStartDate);
         			cs.setStatus(0);
         			this.clockinSettingService.save(cs);
     				response.setSuccess("新规则设置成功，将在下一个周期生效");
     			}else {
     				response.setErrorMsg("设置习惯规则出现异常");
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
