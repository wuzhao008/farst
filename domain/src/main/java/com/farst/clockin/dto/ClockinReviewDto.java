package com.farst.clockin.dto;
 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data 
@ApiModel(value="打卡评论dto", description="打卡评论dto")
public class ClockinReviewDto {
	
    @ApiModelProperty(value = "打卡日志ID")
    private Integer clockinContentId;

    @ApiModelProperty(value = "评论内容")
    private String reviewContent; 
}
