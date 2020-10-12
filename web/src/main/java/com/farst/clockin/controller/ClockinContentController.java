package com.farst.clockin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.farst.clockin.dto.ClockinContentDto;
import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.entity.ClockinPicture;
import com.farst.clockin.service.IClockinContentService;
import com.farst.clockin.service.IClockinContentUpService;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.service.IClockinPictureService;
import com.farst.clockin.service.IClockinReviewService;
import com.farst.clockin.vo.ClockinContentVo;
import com.farst.clockin.vo.ClockinLogVo;
import com.farst.clockin.vo.TodayClockinVo; 
import com.farst.common.web.response.RestResponse;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerHabbit;
import com.farst.customer.service.ICustomerInfoService;
import com.farst.customer.service.ICustomerHabbitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private ICustomerInfoService customerInfoService;
    
    @Autowired
    private IClockinContentService clockinContentService;
    
    @Autowired
    private IClockinPictureService clockinPictureService;
    
    @Autowired
    private IClockinContentUpService clockinContentUpService;
    
    @Autowired
    private IClockinReviewService clockinReviewService;
    
    @Autowired
    private ICustomerHabbitService customerHabbitService;
    
    @Autowired
    private IClockinLabelService clockinLabelService;
 	
    /**
     * 查询今日打卡列表信息
     */
    @ApiOperation(value = "查询今日打卡列表信息")
    @GetMapping(value = "/pageTodayClockin")
    public RestResponse<IPage<TodayClockinVo>> pageTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<TodayClockinVo>> response = new RestResponse<>();
    	IPage<TodayClockinVo> page = new Page<TodayClockinVo>(pageNum, pageSize); 
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
	    	page = this.clockinContentService.getPageTodayClockinVo(page, custId);
	    	response.setSuccess(page);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    /**
     * 查询今日打卡详细信息
     */
    @ApiOperation(value = "查询今日打卡详细信息")
    @GetMapping(value = "/detailTodayClockin")
    public RestResponse<ClockinContentVo> detailTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "habbitId") Integer habbitId){
        RestResponse<ClockinContentVo> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		ClockinContentVo ccVo = this.clockinContentService.getTodayClockinContentVo(custId, habbitId);
	    	response.setSuccess(ccVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
 
    @ApiOperation(value = "今日打卡")
    @PostMapping(value = "/todayClockin")
    public RestResponse<String> todayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "habbitId") int habbitId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		CustomerHabbit cl = this.customerHabbitService.getById(habbitId);
     		if(cl == null) {
     			response.setErrorMsg("非法习惯");
     			return response;
     		}
    		this.clockinContentService.todayClockin(custId, habbitId);
    		response.setSuccess("打卡成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    @ApiOperation(value = "撤销今日打卡")
    @PostMapping(value = "/reverseTodayClockin")
    public RestResponse<String> reverseTodayClockin(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "habbitId") int habbitId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
    		this.clockinContentService.reverseTodayClockin(custId, habbitId);
    		response.setSuccess("撤销打卡成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    @ApiOperation(value = "发布打卡日志")
    @PostMapping(value = "/publishTodayClockinContent")
    public RestResponse<String> publishTodayClockinContent(@RequestHeader("tokenid") String tokenid,@RequestBody ClockinContentDto clockinContentDto){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		ClockinContent cc = this.clockinContentService.getTodayClockinContent(custId, clockinContentDto.getHabbitId());
     		if(cc == null) {
     			response.setErrorMsg("打卡后才能发布日志");
     			return response;
     		}
     		
     		List<String> listPicUrl = new ArrayList<String>();
     		if(StringUtils.isNoneEmpty(clockinContentDto.getPicUrl())) {
     			listPicUrl = Arrays.asList(clockinContentDto.getPicUrl().split(","));
     		}
     		this.clockinContentService.publishTodayClockinContent(custId, clockinContentDto.getHabbitId(), clockinContentDto.getContent(), listPicUrl, clockinContentDto.getIsPublic());
     		response.setSuccess("发布打卡日志成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    


    @ApiOperation(value = "删除掉我的某条打卡日志")
    @PostMapping(value = "/removeClockinContent")
    public RestResponse<String> removeClockinContent(@RequestHeader("tokenid") String tokenid,@RequestParam(name="clockinContentId") Integer clockinContentId){
    	RestResponse<String> response = new RestResponse<String>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		ClockinContent cc = this.clockinContentService.getById(clockinContentId);
     		if(cc == null || cc.getStatus() != 0 || !cc.getCustomerInfoId().equals(custId)) {
     			response.setErrorMsg("非法请求");
     			return response;
     		} 
     		this.clockinContentService.updateContent(cc.getId(), null);
     		response.setSuccess("删除打卡日志成功");
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    /**
     * 查询同道中人的日志信息
     */
    @ApiOperation(value = "查询同道中人的日志信息")
    @GetMapping(value = "/getPageSimilarClockinLog")
    public RestResponse<IPage<ClockinLogVo>> getPageSimilarClockinLog(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinLogVo>> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		IPage<ClockinContent> pageCc = new Page<ClockinContent>(pageNum, pageSize);
     		IPage<ClockinLogVo> pageClVo = new Page<ClockinLogVo>(pageNum, pageSize);
     		pageCc = this.clockinContentService.getPageSimilarClockinContent(pageCc, custId);
     		pageClVo.setPages(pageCc.getPages());
     		pageClVo.setTotal(pageCc.getTotal());
      		List<ClockinContent> listCc = pageCc.getRecords();
      		List<ClockinLogVo> listClVo = new ArrayList<ClockinLogVo>();
      		
      		//为了减少数据库循环请求，一次性从数据库中根据列表内容ID获取对应的数据
      		List<Integer> listCcId = new ArrayList<Integer>();
      		List<Integer> listCustId = new ArrayList<Integer>();
      		List<Integer> listLabelId = new ArrayList<Integer>();
      		List<Integer> listHabbitId = new ArrayList<Integer>();
      		List<ClockinPicture> listCp = null;
      		List<CustomerInfo> listCust = null;
      		List<ClockinLabel> listLabel = null;
      		List<CustomerHabbit> listHabbit = null;
      		List<Map<String,Object>> listReviewCnt = null;
      		List<Map<String,Object>> listUpCnt = null;
      		List<Integer> listCcupId = null;
      		
      		if(CollectionUtils.isNotEmpty(listCc)) {
      			listCc.forEach(cc->{
      				listCcId.add(cc.getId());
      				listCustId.add(cc.getCustomerInfoId());
      				listHabbitId.add(cc.getCustomerHabbitId());
      			});

          		listHabbit = this.customerHabbitService.getListCustomerHabbitByListId(listHabbitId);
          		listHabbit.forEach(habbit->{
          			listLabelId.add(habbit.getClockinLabelId());
          		});
          		
      			listCp = this.clockinPictureService.getAllClockinPictureByListContentId(listCcId);
          		listCust = this.customerInfoService.getListCustomerInfoBy(listCustId);
          		listLabel = this.clockinLabelService.getListClockinLabelByListId(listLabelId);
          		listReviewCnt = this.clockinReviewService.getMapReviewCountsByListContentId(listCcId);
          		listUpCnt = this.clockinContentUpService.getMapContentUpsByListContentId(listCcId);
          		listCcupId = this.clockinContentUpService.getMyUpContentIds(listCcId, custId);
          		
      			for(ClockinContent cc : listCc) {
      				ClockinLogVo clVo = new ClockinLogVo();
      				clVo.setReviewCount((long)0);
      				clVo.setUpCount((long)0);
      				
      				//构造contentVo
      				ClockinContentVo ccVo = new ClockinContentVo();
      				ccVo.setClockinContent(cc);
      				List<ClockinPicture> listCp_ = new ArrayList<ClockinPicture>();
      				if(CollectionUtils.isNotEmpty(listCp)) {
      					listCp.forEach(cp->{
      						if(cp.getClockinContentId().equals(cc.getId())) {
      							listCp_.add(cp);
      						}
      					});
      				}
      				ccVo.setClockinPictures(listCp_);
      				clVo.setClockinContentVo(ccVo);
      				
      				//构造用户信息
      				if(CollectionUtils.isNotEmpty(listCust)) {
      					for(CustomerInfo cust : listCust) {
      						if(cust.getId().equals(cc.getCustomerInfoId())) {
      							clVo.setCustomerInfo(cust);
      							break;
      						}
      					}
      				}

      				//构造习惯信息和标签信息
      				if(CollectionUtils.isNotEmpty(listHabbit)) {
      					for(CustomerHabbit habbit : listHabbit) {
      						if(habbit.getId().equals(cc.getCustomerHabbitId())) {
      							clVo.setCustomerHabbit(habbit);
      							for(ClockinLabel label : listLabel) {
      								if(label.getId().equals(habbit.getClockinLabelId())) {
      									clVo.setClockinLabel(label);
      									break;
      								}
      							}
      							break;
      						}
      					}
      				}
      				
      				//构造评论数
      				if(CollectionUtils.isNotEmpty(listReviewCnt)) {
      					for(Map<String, Object> mapCnt : listReviewCnt) {
      						if(mapCnt.get("id").equals(cc.getId())) {
      							clVo.setReviewCount((Long)mapCnt.get("cnt"));
      							break;
      						}
      					}
      				}
      				
      				//构造置顶数
      				if(CollectionUtils.isNotEmpty(listUpCnt)) {
      					for(Map<String, Object> mapCnt : listUpCnt) {
      						if(mapCnt.get("id").equals(cc.getId())) {
      							clVo.setUpCount((Long)mapCnt.get("cnt"));
      							break;
      						}
      					}
      				} 
      				clVo.setIsUp(false);
      				//构造当前用户是否顶过该日志
      				if(CollectionUtils.isNotEmpty(listCcupId)) {
      					if(listCcupId.contains(cc.getId())) {
      						clVo.setIsUp(true);
      					}
      				} 
      				
      				listClVo.add(clVo);
      			}
      		}
      		pageClVo.setRecords(listClVo);
      		response.setSuccess(pageClVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    

    /**
     * 查询我的日志列表信息
     */
    @ApiOperation(value = "查询我的日志列表信息")
    @GetMapping(value = "/getPageMyClockinLog")
    public RestResponse<IPage<ClockinLogVo>> getPageMyClockinLog(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        RestResponse<IPage<ClockinLogVo>> response = new RestResponse<>();
    	try {
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		CustomerInfo cust = this.customerInfoService.getById(custId);
     		IPage<ClockinContent> pageCc = new Page<ClockinContent>(pageNum, pageSize);
     		IPage<ClockinLogVo> pageClVo = new Page<ClockinLogVo>(pageNum, pageSize);
     		pageCc = this.clockinContentService.getPageMyClockinContent(pageCc, custId);
     		pageClVo.setPages(pageCc.getPages());
     		pageClVo.setTotal(pageCc.getTotal());
      		List<ClockinContent> listCc = pageCc.getRecords();
      		List<ClockinLogVo> listClVo = new ArrayList<ClockinLogVo>();
      		
      		//为了减少数据库循环请求，一次性从数据库中根据列表内容ID获取对应的数据
      		List<Integer> listCcId = new ArrayList<Integer>(); 
      		List<Integer> listLabelId = new ArrayList<Integer>();
      		List<Integer> listHabbitId = new ArrayList<Integer>();
      		List<ClockinPicture> listCp = null; 
      		List<ClockinLabel> listLabel = null;
      		List<CustomerHabbit> listHabbit = null;
      		List<Map<String,Object>> listReviewCnt = null;
      		List<Map<String,Object>> listUpCnt = null;
      		List<Integer> listCcupId = null;
      		
      		if(CollectionUtils.isNotEmpty(listCc)) {
      			listCc.forEach(cc->{
      				listCcId.add(cc.getId()); 
      				listHabbitId.add(cc.getCustomerHabbitId());
      			});
          		listHabbit = this.customerHabbitService.getListCustomerHabbitByListId(listHabbitId);
          		listHabbit.forEach(habbit->{
          			listLabelId.add(habbit.getClockinLabelId());
          		});
          		
      			listCp = this.clockinPictureService.getAllClockinPictureByListContentId(listCcId); 
          		listLabel = this.clockinLabelService.getListClockinLabelByListId(listLabelId);
          		listReviewCnt = this.clockinReviewService.getMapReviewCountsByListContentId(listCcId);
          		listUpCnt = this.clockinContentUpService.getMapContentUpsByListContentId(listCcId);
          		listCcupId = this.clockinContentUpService.getMyUpContentIds(listCcId, custId);
          		
          		
      			for(ClockinContent cc : listCc) {
      				ClockinLogVo clVo = new ClockinLogVo();
      				clVo.setReviewCount((long)0);
      				clVo.setUpCount((long)0);
      				
      				//构造contentVo
      				ClockinContentVo ccVo = new ClockinContentVo();
      				ccVo.setClockinContent(cc);
      				List<ClockinPicture> listCp_ = new ArrayList<ClockinPicture>();
      				if(CollectionUtils.isNotEmpty(listCp)) {
      					listCp.forEach(cp->{
      						if(cp.getClockinContentId().equals(cc.getId())) {
      							listCp_.add(cp);
      						}
      					});
      				}
      				ccVo.setClockinPictures(listCp_);
      				clVo.setClockinContentVo(ccVo);
      				
      				//构造用户信息
      				clVo.setCustomerInfo(cust);

      				//构造习惯信息和标签信息
      				if(CollectionUtils.isNotEmpty(listHabbit)) {
      					for(CustomerHabbit habbit : listHabbit) {
      						if(habbit.getId().equals(cc.getCustomerHabbitId())) {
      							clVo.setCustomerHabbit(habbit);
      							for(ClockinLabel label : listLabel) {
      								if(label.getId().equals(habbit.getClockinLabelId())) {
      									clVo.setClockinLabel(label);
      									break;
      								}
      							}
      							break;
      						}
      					}
      				}
      				
      				//构造评论数
      				if(CollectionUtils.isNotEmpty(listReviewCnt)) {
      					for(Map<String, Object> mapCnt : listReviewCnt) {
      						if(mapCnt.get("id").equals(cc.getId())) {
      							clVo.setReviewCount((Long)mapCnt.get("cnt"));
      							break;
      						}
      					}
      				}
      				
      				//构造置顶数
      				if(CollectionUtils.isNotEmpty(listUpCnt)) {
      					for(Map<String, Object> mapCnt : listUpCnt) {
      						if(mapCnt.get("id").equals(cc.getId())) {
      							clVo.setUpCount((Long)mapCnt.get("cnt"));
      							break;
      						}
      					}
      				} 
      				clVo.setIsUp(false);
      				//构造当前用户是否顶过该日志
      				if(CollectionUtils.isNotEmpty(listCcupId)) {
      					if(listCcupId.contains(cc.getId())) {
      						clVo.setIsUp(true);
      					}
      				}
      				
      				listClVo.add(clVo);
      			}
      		}
      		pageClVo.setRecords(listClVo);
      		response.setSuccess(pageClVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
    
    /**
     * 根据日志内容ID查询日志内容
     */
    @ApiOperation(value = "根据日志内容ID查询日志内容")
    @GetMapping(value = "/getClockinLogById")
    public RestResponse<ClockinLogVo> getClockinLogById(@RequestHeader("tokenid") String tokenid,@RequestParam(name = "contentId") Integer contentId){
        RestResponse<ClockinLogVo> response = new RestResponse<>();
    	try {
     		ClockinLogVo clVo = new ClockinLogVo();
     		Integer custId = this.customerInfoService.getTokenCustVo(tokenid).getCustId();
     		CustomerInfo cust = this.customerInfoService.getById(custId);
     		clVo.setCustomerInfo(cust);
     		
     		ClockinContentVo ccVo = new ClockinContentVo();
     		ClockinContent cc = this.clockinContentService.getById(contentId);
     		if((cc == null || cc.getStatus() !=0 )) {
     			response.setErrorMsg("非法请求");
     			return response;
     		}
     		List<ClockinPicture> listCp = this.clockinPictureService.getAllClockinPictureByContentId(cc.getId());
     		ccVo.setClockinContent(cc);
     		ccVo.setClockinPictures(listCp);
     		clVo.setClockinContentVo(ccVo);
     		
     		CustomerHabbit customerHabbit = this.customerHabbitService.getById(cc.getCustomerHabbitId());
     		clVo.setCustomerHabbit(customerHabbit);
     		
     		ClockinLabel cl = this.clockinLabelService.getById(customerHabbit.getClockinLabelId());
     		clVo.setClockinLabel(cl);
     		
     		Long upCnt = this.clockinContentUpService.getUpCountByContentId(cc.getId());
     		clVo.setUpCount(upCnt);
     		
     		Long reviewCnt = this.clockinReviewService.getReviewCountByContentId(cc.getId());
     		clVo.setReviewCount(reviewCnt);
     		
     		boolean isUp = this.clockinContentUpService.hasUpClockinContent(contentId, custId);
     		clVo.setIsUp(isUp);
     		
     		response.setSuccess(clVo);
    	}catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setErrorMsg(e.getMessage());
        }
    	return response;
    }
    
}
