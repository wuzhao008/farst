package com.farst.customer.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "客户级别统计Vo")
public class CustomerStatisticsVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	 
	@ApiModelProperty(value = "日志总数") 
	private Integer logCount;

	@ApiModelProperty(value = "关注总数") 
	private Integer followCount;

	@ApiModelProperty(value = "粉丝总数") 
	private Integer fansCount;

	@ApiModelProperty(value = "被赞总数") 
	private Integer upCount; 
	 
}
