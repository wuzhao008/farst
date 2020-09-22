package com.farst.customer.service;

import com.farst.customer.entity.CustomerLabel;

import java.util.List;

import com.farst.clockin.vo.AllClockinLabelVo;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户打卡标签 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerLabelService extends IBasicService<CustomerLabel> {

	/**
	 * 根据客户ID获取用户标签列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public List<CustomerLabel> getListCustomerLabel(Integer customerInfoId);
	
	/**
	 * 根据用户ID获取对应已经选择的标签ID
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public List<Integer> getListClockLabelId(Integer customerInfoId);
	
	/**
	 * 查询用户ID对应是否有用户标签
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public boolean hasCustomerLabel(Integer customerInfoId);
	
	/**
	 * 根据用户ID获取需要选择的标签列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	public List<AllClockinLabelVo> getListSelectClockinLabelVo(Integer customerInfoId);
	
	
	
	/**
	 * 增加用户新标签
	 * 
	 * @param customerInfoId
	 * @param listLabelId
	 */
	public void addCustomerLabel(Integer customerInfoId,List<Integer> listLabelId);
	
	/**
	 * 获取用户标签-不论状态
	 * 
	 * @param customerInfoId
	 * @param labelId
	 * @return
	 */
	public CustomerLabel getCustomerLabelRecord(Integer customerInfoId,Integer labelId);
	
	/**
	 * 获取用户标签
	 * @param customerInfoId
	 * @param labelId
	 * @return
	 */
	public CustomerLabel getCustomerLabel(Integer customerInfoId,Integer labelId);
}
