package com.farst.clockin.vo;

import java.io.Serializable;
import java.util.List;
import com.farst.clockin.entity.ClockinLabel; 
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "选标签Vo")
public class SelectClockinLabelVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "父标签信息", required = true)
	private ClockinLabel clockinLabel;
	
	@ApiModelProperty(value = "子标签信息", required = true)
	private List<ChildClockinLabelVo> childClockinLabelVos;
	
}
