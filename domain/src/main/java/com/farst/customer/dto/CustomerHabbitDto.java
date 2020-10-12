package com.farst.customer.dto;
 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data 
@ApiModel(value="用户习惯规则dto", description="用户习惯规则dto")
public class CustomerHabbitDto {

    @ApiModelProperty(value = "习惯id")
    private Integer id;
	
    @ApiModelProperty(value = "习惯名")
    private String habbitName;
    
    @ApiModelProperty(value = "标签ID")
    private Integer clockinLabelId;

    @ApiModelProperty(value = "频率类型（1每周、2每月、3每年）")
    private Integer freqType;

    @ApiModelProperty(value = "固定为0,1,5代表周格式的值，其他为每周每月每年时对应的天数")
    private String freqValue;

    @ApiModelProperty(value = "是否自动弹出写日志")
    private Integer popupLog;
}
