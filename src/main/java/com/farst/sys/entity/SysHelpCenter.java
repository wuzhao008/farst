package com.farst.sys.entity;

import java.time.LocalDateTime;
import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 帮助中心
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysHelpCenter对象", description="帮助中心")
public class SysHelpCenter extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "回答内容")
    private String content;
    
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "最近修改时间")
    private LocalDateTime lastEditTime;


}
