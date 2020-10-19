package com.farst.customer.vo;

import java.io.Serializable;
import com.farst.customer.entity.CustomerInfo;
import com.farst.customer.entity.CustomerMessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "客户消息Vo")
public class CustomerMessageVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "来源客户信息")
	private CustomerInfo sourceCustomerInfo;

	@ApiModelProperty(value = "客户消息")
	private CustomerMessage customerMessage;
	
}
