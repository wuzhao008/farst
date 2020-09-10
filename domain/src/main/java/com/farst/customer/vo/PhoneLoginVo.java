package com.farst.customer.vo;

import java.io.Serializable; 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "电话登录成功返回Vo")
public class PhoneLoginVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "是否有昵称", required = true)
	private boolean hasNickName;
	
	@ApiModelProperty(value = "是否选择性别", required = true)
	private boolean hasSex;

	@ApiModelProperty(value = "是否设置标签", required = true)
	private boolean hasLabel;
	
	@ApiModelProperty(value = "token信息", required = true)
	private TokenInfoVo tokenInfoVo;
}
