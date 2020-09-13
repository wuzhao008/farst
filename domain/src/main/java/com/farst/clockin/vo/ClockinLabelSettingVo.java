package com.farst.clockin.vo;

import java.io.Serializable; 
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.entity.ClockinSetting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "标签以及标签设置VO")
public class ClockinLabelSettingVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "标签信息", required = true)
	private ClockinLabel clockinLabel;
	
	@ApiModelProperty(value = "子标签信息", required = true)
	private ClockinSetting clockinSetting;
	
}
