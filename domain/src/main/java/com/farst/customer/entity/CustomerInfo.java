package com.farst.customer.entity;
 
import java.util.Date;

import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CustomerInfo对象", description="用户信息")
public class CustomerInfo extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "年代")
    private String decade;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "最近修改时间")
    private Date lastEditTime;

    @ApiModelProperty(value = "最近登陆IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最近登陆时间")
    private Date lastLoginTime;


}
