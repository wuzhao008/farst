package com.farst.clockin.dto;
 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data 
@ApiModel(value="打卡日志dto", description="打卡日志dto")
public class ClockinContentDto {
	
    @ApiModelProperty(value = "标签ID")
    private Integer labelId;

    @ApiModelProperty(value = "日志内容")
    private String content;

    @ApiModelProperty(value = "图片URL，格式如111,222,333")
    private String picUrl;

    @ApiModelProperty(value = "是否自动弹出写日志")
    private Integer popupLog;
    
    @ApiModelProperty(value = "是否公开日志")
    private Integer isPublic;
}
