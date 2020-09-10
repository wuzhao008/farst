package com.farst.customer.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Token信息Vo")
public class TokenInfoVo implements Serializable {

	/**
	 * CustAccessLog
	 */
	private static final long serialVersionUID = 1L;  

	@ApiModelProperty(value = "tokenId", required = true)
	private String tokenId;

	@ApiModelProperty(value = "失效时间", required = true)
	private Long expire;
	
	@ApiModelProperty(value = "客户信息", required = true)
	private Integer customerId; 
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", serialVersionUID=").append(serialVersionUID);  
		sb.append(", tokenid=").append(tokenId);
		sb.append(", expire=").append(expire);		
		sb.append(", customerId=").append(customerId);	
		sb.append("]");
		return sb.toString();
	}
}