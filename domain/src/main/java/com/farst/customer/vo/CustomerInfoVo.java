package com.farst.customer.vo;

import java.io.Serializable; 
import java.util.List;

import com.farst.clockin.entity.ClockinLabel;
import com.farst.customer.entity.CustomerInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "客户Vo")
public class CustomerInfoVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "客户信息")
	private CustomerInfo customerInfo;

	@ApiModelProperty(value = "标签信息")
	private List<ClockinLabel> listClockinLabel;
	
	@ApiModelProperty(value = "统计信息")
	private CustomerStatisticsVo statisticsVo;
	
}
