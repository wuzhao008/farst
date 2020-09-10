package com.farst.clockin.vo;

import java.io.Serializable;

import com.farst.clockin.entity.ClockinLabel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "子级标签Vo")
public class ChildClockinLabelVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前用户是否已选", required = true)
	private boolean hasSelected;
	
	@ApiModelProperty(value = "标签信息", required = true)
	private ClockinLabel clockinLabel;
	
}
