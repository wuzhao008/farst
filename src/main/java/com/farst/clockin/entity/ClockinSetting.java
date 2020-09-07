package com.farst.clockin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @ApiModelProperty(value = "用户ID")
    private Integer customerInfoId;

    @ApiModelProperty(value = "标签ID")
    private Integer clockinLabelId;

    @ApiModelProperty(value = "频率类型（1固定、2每周、3每月、4每年）")
    private Integer freqType;

    @ApiModelProperty(value = "固定为0,1,5代表周格式的值，其他为每周每月每年时对应的天数")
    private String freqValue;

    @ApiModelProperty(value = "是否自动弹出写日志")
    private Integer popupLog;

    @ApiModelProperty(value = "设置的对应月份，当没有记录时设置为当前月份，有当前月份记录时设置下月月份，有下月记录时，则不能设置")
    private LocalDate month;

    @ApiModelProperty(value = "正常删除状态(0正常,1为删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "最近修改时间")
    private LocalDateTime lastEditTime;


}
