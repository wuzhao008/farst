package com.farst.clockin.vo;

import java.io.Serializable; 
import com.farst.clockin.entity.ClockinReview;
import com.farst.customer.entity.CustomerInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "打卡评论内容VO")
public class ClockinReviewVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "评论人", required = true)
	private CustomerInfo customerInfo;
	
	@ApiModelProperty(value = "打卡评论", required = true)
	private ClockinReview clockinReview;
	
	@ApiModelProperty(value = "评论点赞数", required = true)
	private Long upCount;
	
    @ApiModelProperty(value = "当前用户是否点赞")
    private Boolean isUp;
	
}
