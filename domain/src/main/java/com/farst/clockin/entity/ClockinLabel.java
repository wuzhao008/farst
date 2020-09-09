package com.farst.clockin.entity;

import java.time.LocalDateTime;
import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 打卡标签
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ClockinLabel对象", description="打卡标签")
public class ClockinLabel extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "上级标签id")
    private Integer pid;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "标签图片地址")
    private String labelPicUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "最近修改时间")
    private LocalDateTime lastEditTime;


}
