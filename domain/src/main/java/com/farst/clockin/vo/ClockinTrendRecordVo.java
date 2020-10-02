package com.farst.clockin.vo;

import java.io.Serializable;
import java.util.List; 
import com.farst.clockin.entity.ClockinLabel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "打卡趋势记录vo")
public class ClockinTrendRecordVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "习惯信息", required = true)
	private ClockinLabel clockinLabel;

	@ApiModelProperty(value = "打卡周期统计", required = true)
	private List<ClockinTrendStatisticsVo> listClockinTrendStatisticsVo; 

}
