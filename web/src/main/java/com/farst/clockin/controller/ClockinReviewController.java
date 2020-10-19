package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.clockin.service.IClockinReviewUpService; 
import com.farst.clockin.vo.ClockinReviewVo; 
import com.farst.clockin.dto.ClockinReviewDto;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinReview;
import com.farst.common.web.response.RestResponse;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerMessage;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farst.common.utils.StringUtils;
import com.farst.common.web.controller.BasicController;
 
/**
 * <p>
    * 打卡内容评论 前端控制器
    * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 * @version v1.0
 */
@Api(tags = {"打卡内容评论"})
@RestController
@RequestMapping("/clockin/clockinReview")
public class ClockinReviewController extends BasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IClockinReviewService clockinReviewService;
    
    @Autowired
    private IClockinReviewUpService clockinReviewUpService;
 	
    @Autowired
    private IClockinContentService clockinContentService;
    
    @Autowired
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private ICustomerMessageService customerMessageService;
    
    /**
     * 查询日志内容对应的分页评论信息
     */
    @ApiOperation(value = "查询日志内容对应的分页评论信息")
    @GetMapping(value = "/getPageReviewByContentId")
    public RestResponse<IPage<ClockinReviewVo>> getPageReviewByContentId(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinContentId") Integer clockinContentId,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinReviewVo>> response = new RestResponse<>(); 
    	try { 
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		IPage<ClockinReview> pageCr = new Page<ClockinReview>(pageNum, pageSize);
     		IPage<ClockinReviewVo> pageCrVo = new Page<ClockinReviewVo>(pageNum, pageSize);
     		pageCr = this.clockinReviewService.getPageClockinReviewByContentId(pageCr, clockinContentId);
     		pageCrVo.setPages(pageCr.getPages());
     		pageCrVo.setTotal(pageCr.getTotal());
      		List<ClockinReview> listCr = pageCr.getRecords();
      		List<ClockinReviewVo> listCrVo = new ArrayList<ClockinReviewVo>();
      		
      		//为了减少数据库循环请求，一次性从数据库中根据列表内容ID获取对应的数据
      		List<Integer> listCustId = new ArrayList<Integer>();
      		List<Integer> listReviewId = new ArrayList<Integer>();
      		
      		List<Map<String,Object>> listUpCnt = null;
      		List<CustomerInfo> listCust = null;
      		List<Integer> listCrupId = null;
      		
      		if(CollectionUtils.isNotEmpty(listCr)) {
	  			listCr.forEach(cr->{ 
	  				listCustId.add(cr.getCustomerInfoId()); 
	  				listReviewId.add(cr.getId());
	  			});
	      		
	  			listCust = this.customerInfoService.getListCustomerInfoBy(listCustId);
	  			listUpCnt = this.clockinReviewUpService.getMapReviewUpsByListReviewId(listReviewId);
          		listCrupId = this.clockinReviewUpService.getMyUpReviewIds(listReviewId, custId);
	     		
	  			for(ClockinReview cr : listCr) {
	  				ClockinReviewVo crVo = new ClockinReviewVo();
	  				crVo.setUpCount((long)0);
	  				
	  				crVo.setClockinReview(cr);
	  				 
	  				//构造用户信息
	  				if(CollectionUtils.isNotEmpty(listCust)) {
	  					for(CustomerInfo cust : listCust) {
	  						if(cust.getId().equals(cr.getCustomerInfoId())) {
	  							crVo.setCustomerInfo(cust);
	  							break;
	  						}
	  					}
	  				}
	
	  				//构造置顶数
	  				if(CollectionUtils.isNotEmpty(listUpCnt)) {
	  					for(Map<String, Object> mapCnt : listUpCnt) {
	  						if(mapCnt.get("id").equals(cr.getId())) {
	  							crVo.setUpCount((Long)mapCnt.get("cnt"));
	  							break;
	  						}
	  					}
	  				} 
	  				crVo.setIsUp(false);
	  				//构造当前用户是否顶过该日志
	  				if(CollectionUtils.isNotEmpty(listCrupId)) {
	  					if(listCrupId.contains(cr.getId())) {
	  						crVo.setIsUp(true);
	  					}
	  				} 
	  				
	  				listCrVo.add(crVo);
	  				
	  			}
      		}
      		pageCrVo.setRecords(listCrVo);
      		response.setSuccess(pageCrVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
 	
    @ApiOperation(value = "评论打卡日志")
    @PostMapping(value = "/reviewContent")
    public RestResponse<String> reviewContent(@RequestHeader("tokenid") String tokenid,@RequestBody ClockinReviewDto clockinReviewDto){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinContent cc = this.clockinContentService.getById(clockinReviewDto.getClockinContentId());
        	if(cc == null || cc.getStatus() != 0 || StringUtils.isEmpty(cc.getContent())) {
        		response.setErrorMsg("非法日志内容");
        		return response;
        	}
        	
        	ClockinReview cr = new ClockinReview();
        	cr.setClockinContentId(clockinReviewDto.getClockinContentId());
        	cr.setContent(clockinReviewDto.getReviewContent());
        	cr.setStatus(0);
        	cr.setCheckStatus(1);
        	cr.setCustomerInfoId(custId);
        	cr.setCreateDate(new Date());
        	this.clockinReviewService.save(cr);
        	
        	CustomerMessage cm = new CustomerMessage();
        	cm.setContent(cr.getContent());
        	cm.setCreateDate(new Date());
        	cm.setCustomerInfoId(custId);
        	cm.setMessageType(2);
        	cm.setObjectContent(cc.getContent());
        	cm.setObjectId(cr.getId());
        	cm.setReadStatus(0);
        	cm.setStatus(0);
        	this.customerMessageService.save(cm);
        	
     		response.setSuccess("评论成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    

    @ApiOperation(value = "审核拒绝日志评论-【暂替换后台审核功能做的接口】")
    @PostMapping(value = "/checkRejectReview")
    public RestResponse<String> checkRejectClockinContent(@RequestHeader("tokenid") String tokenid,@RequestParam("reviewIds") String reviewIds){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		if(custId != 0) {
     			response.setErrorMsg("系统管理员才能操作该功能接口");
     			return response;
     		}
     		String[] arrStrReviewId = reviewIds.split(",");
     		for(int i=0;i<arrStrReviewId.length;i++) {
     			Integer reviewId = Integer.valueOf(arrStrReviewId[i]);
     			this.clockinReviewService.checkRejectClockinReview(reviewId);
     		}
     		response.setSuccess("操作成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    
    @ApiOperation(value = "删除某条我的评论")
    @PostMapping(value = "/removeReview")
    public RestResponse<String> removeReview(@RequestHeader("tokenid") String tokenid,@RequestParam(name="id") Integer id){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
        	Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
        	ClockinReview cr = this.clockinReviewService.getById(id);
        	if(cr == null || cr.getStatus() != 0 || cr.getCustomerInfoId() != custId) {
        		response.setErrorMsg("非法请求");
        	}
        	cr.setStatus(1);
        	cr.setLastEditTime(new Date());
        	this.clockinReviewService.saveOrUpdate(cr);
     		response.setSuccess("删除评论成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    
    
}
