package com.farst.customer.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Token用户封装对象Vo")
public class TokenCustVo implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "客户ID", required = true)
	private Integer custId;

	@ApiModelProperty(value = "电话号码", required = true)
	private String phoneNumber;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append(", custId=").append(custId);
		sb.append(", phoneNumber=").append(phoneNumber); 
		sb.append("]");
		return sb.toString();
	}
}