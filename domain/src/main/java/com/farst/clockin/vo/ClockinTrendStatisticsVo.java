package com.farst.clockin.vo;

import java.io.Serializable; 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "打卡趋势统计vo")
public class ClockinTrendStatisticsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "周期", required = true)
	private String periodName;

	@ApiModelProperty(value = "打卡天数", required = true)
	private Integer clockinDays;

	@ApiModelProperty(value = "计划天数", required = true)
	private Integer planDays;
	
	@ApiModelProperty(value = "打卡百分率", required = true)
	private Integer clockinRate;

}
