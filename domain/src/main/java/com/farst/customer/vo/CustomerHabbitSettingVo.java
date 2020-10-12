package com.farst.customer.vo;

import java.io.Serializable; 
import com.farst.clockin.entity.ClockinLabel;
import com.farst.customer.entity.CustomerHabbitSetting;
import com.farst.customer.entity.CustomerHabbit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "标签以及标签设置VO")
public class CustomerHabbitSettingVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "习惯信息", required = true)
	private CustomerHabbit customerHabbit;
	
	@ApiModelProperty(value = "标签信息", required = true)
	private ClockinLabel clockinLabel;
	
	@ApiModelProperty(value = "习惯设置信息", required = true)
	private CustomerHabbitSetting customerHabbitSetting;
	
}
