package com.farst.customer.entity;

import java.time.LocalDateTime;
import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户消息
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CustomerMessage对象", description="用户消息")
public class CustomerMessage extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer customerInfoId;

    @ApiModelProperty(value = "消息类型（0系统发布、1建议回复、2日志评论 3点赞日志 4点赞评论 9其他)")
    private Integer messageType;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "对应业务id——0为空、1为建议id、2为日志内容id、3为日志内容id、4为评论id")
    private Integer objectId;

    @ApiModelProperty(value = "业务内容-冗余字段")
    private String objectContent;

    @ApiModelProperty(value = "消息读状态（0未读、1已读）")
    private Integer readStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "最近修改时间")
    private LocalDateTime lastEditTime;


}
