package com.farst.common.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "返回对象")
public class RestResponse<T> {
	/** 默认的成功标志:0 */
	private final String CODE_SUCCESS = "0";
	/** 默认的错误标志:10000 */
	private final String CODE_ERROR = "10000";
	
	/** 返回代码 */
	@ApiModelProperty(value = "返回代码")
	private String code = "";
	/** 返回信息 */
	@ApiModelProperty(value = "返回信息")
	private String msg = "";	
	/** 返回对象 */
	@ApiModelProperty(value = "返回对象")
	private T data; 

	public RestResponse() {
	}

	public void setSuccess(T data) {
		this.code = CODE_SUCCESS;
		this.data = data;
	}
	
	/**
	 * 批量执行中 存在不满足条件的，返回正确的执行结果 和 执行失败的 提示
	 * @param data
	 * @param msg
	 */
	public void setSuccess(T data,String msg) {
		this.code = CODE_SUCCESS;
		this.data = data;
		this.msg = msg;
	}

	public void setErrorMsg(String msg) {
		this.code = CODE_ERROR;
		this.msg = msg;
	}
	
	public void setCodeMsg(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
