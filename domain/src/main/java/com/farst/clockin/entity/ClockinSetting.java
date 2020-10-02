package com.farst.clockin.entity;
 
import java.util.Date;

import com.farst.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 打卡设置
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ClockinSetting对象", description="打卡设置")
public class ClockinSetting extends BasicEntity {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(value = "用户标签ID")
    private Integer customerLabelId;

    @ApiModelProperty(value = "频率类型（1每周、2每月、3每年）")
    private Integer freqType;

    @ApiModelProperty(value = "每周每月每年时对应的天数")
    private String freqValue;

    @ApiModelProperty(value = "是否自动弹出写日志")
    private Integer popupLog;

    @ApiModelProperty(value = "设置的对应周期开始日期")
    private Date freqStartDate;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "最近修改时间")
    private Date lastEditTime;


}
