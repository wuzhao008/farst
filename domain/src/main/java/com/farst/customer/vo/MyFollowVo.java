package com.farst.customer.vo;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "我的关注用户Vo")
public class MyFollowVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	 
	@ApiModelProperty(value = "电话号码")
    private String phoneNumber; 

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "口号")
    private String slogan;
}
