package com.farst.customer.entity;
 
import java.util.Date;

import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户打卡标签
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CustomerLabel对象", description="用户打卡标签")
public class CustomerLabel extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer customerInfoId;

    @ApiModelProperty(value = "标签ID")
    private Integer clockinLabelId;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "最近修改时间")
    private Date lastEditTime;


}
