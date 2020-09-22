package com.farst.clockin.vo;

import java.io.Serializable;
import java.util.List;

import com.farst.clockin.entity.ClockinContent;
import com.farst.clockin.entity.ClockinPicture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; 

@Data
@ApiModel(value = "打卡日志内容VO")
public class ClockinContentVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "打卡内容", required = true)
	private ClockinContent clockinContent;
	
	@ApiModelProperty(value = "打卡图片")
	private List<ClockinPicture> clockinPictures;
	
}
