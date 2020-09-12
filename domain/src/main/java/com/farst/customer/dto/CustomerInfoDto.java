package com.farst.customer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息Dto
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data 
@ApiModel(value="CustomerInfo对象dto", description="用户信息dto")
public class CustomerInfoDto {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl; 

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "口号")
    private String slogan;


}
