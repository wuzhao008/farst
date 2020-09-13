package com.farst.clockin.dto;
 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data 
@ApiModel(value="打开规则dto", description="打卡规则dto")
public class ClockinSettingDto {
	
    @ApiModelProperty(value = "标签ID")
    private Integer clockinLabelId;

    @ApiModelProperty(value = "频率类型（1固定、2每周、3每月、4每年）")
    private Integer freqType;

    @ApiModelProperty(value = "固定为0,1,5代表周格式的值，其他为每周每月每年时对应的天数")
    private String freqValue;

    @ApiModelProperty(value = "是否自动弹出写日志")
    private Integer popupLog;
}
