package com.farst.clockin.vo;

import java.io.Serializable; 
import com.farst.clockin.entity.ClockinLabel; 
import com.farst.customer.entity.CustomerInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "打卡日志vo")
public class ClockinLogVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "客户信息", required = true)
	private CustomerInfo customerInfo;
	
	@ApiModelProperty(value = "习惯信息", required = true)
	private ClockinLabel clockinLabel;
	
	@ApiModelProperty(value = "打卡内容Vo", required = true)
	private ClockinContentVo clockinContentVo;
	
	@ApiModelProperty(value = "顶数量", required = true)
	private Long upCount;

	@ApiModelProperty(value = "评论数量", required = true)
	private Long reviewCount;
	
    @ApiModelProperty(value = "当前用户是否点赞")
    private Boolean isUp;
	
}
