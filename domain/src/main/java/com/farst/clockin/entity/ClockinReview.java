package com.farst.clockin.entity;
import java.util.Date;
import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 打卡内容评论
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ClockinReview对象", description="打卡内容评论")
public class ClockinReview extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer customerInfoId;

    @ApiModelProperty(value = "内容ID")
    private Integer clockinContentId;

    @ApiModelProperty(value = "评论内容")
    private String content;
    
    @ApiModelProperty(value = "审核状态(0未审核,1为审核通过,2为审核失败)")
    private Integer checkStatus;
    
    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "最近修改时间")
    private Date lastEditTime;

}
