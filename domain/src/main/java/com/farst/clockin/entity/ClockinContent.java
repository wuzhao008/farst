package com.farst.clockin.entity; 
import java.util.Date; 

import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 打卡文字内容
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ClockinContent对象", description="打卡文字内容")
public class ClockinContent extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer customerInfoId;

    @ApiModelProperty(value = "标签ID")
    private Integer clockinLabelId;

    @ApiModelProperty(value = "是否公开")
    private Integer isPublic;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "审核状态(0未审核,1为已审核，2为审核不通过)")
    private Integer checkStatus;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间") 
    private Date createDate;

    @ApiModelProperty(value = "最近修改时间")  
    private Date lastEditTime;


}
