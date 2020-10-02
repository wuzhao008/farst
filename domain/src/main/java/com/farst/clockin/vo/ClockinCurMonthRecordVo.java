package com.farst.clockin.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.farst.clockin.entity.ClockinLabel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "打卡本月记录vo")
public class ClockinCurMonthRecordVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "习惯信息", required = true)
	private ClockinLabel clockinLabel;

	@ApiModelProperty(value = "打卡天数", required = true)
	private int clockinDays;

	@ApiModelProperty(value = "打卡记录", required = true)
	private List<Map<String, Object>> listClockinContent;

}
