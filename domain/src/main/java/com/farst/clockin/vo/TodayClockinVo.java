package com.farst.clockin.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "当日打卡VO")
public class TodayClockinVo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "打卡ID-如果当天没有打卡则为空", required = false)
	private Integer id;

	@ApiModelProperty(value = "习惯ID", required = true)
	private Integer habbitId;

	@ApiModelProperty(value = "习惯名称", required = true)
	private String habbitName;
	
	@ApiModelProperty(value = "标签名称", required = true)
	private String labelName;
	
	@ApiModelProperty(value = "标签图片", required = true)
	private String labelPicUrl;
	
	@ApiModelProperty(value = "是否自动弹出日志", required = true)
	private Integer PopupLog;
	
	@ApiModelProperty(value = "统计类型（1按周、2按月、3按年）", required = true)
	private Integer statisticsType;

	@ApiModelProperty(value = "统计天数-周期内目标天数", required = true)
	private Integer statisticsDays;
	
	@ApiModelProperty(value = "统计天数-周期内打卡天数", required = true)
	private Integer statisticsClockinDays;
	
	@ApiModelProperty(value = "完成比例-周期内完成百分比", required = true)
	private Integer statisticsRate;
	
	
}
