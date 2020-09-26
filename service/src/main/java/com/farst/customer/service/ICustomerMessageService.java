package com.farst.customer.service;
 
import com.farst.customer.entity.CustomerMessage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户消息 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
public interface ICustomerMessageService extends IBasicService<CustomerMessage> {

	/**
	 * 得到用户未读消息总数
	 * 
	 * @param customerInfoId
	 * @return
	 */
	Integer getUnReadMessageCount(Integer customerInfoId);
	
	/**
	 * 得到用户信息分页列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	IPage<CustomerMessage> getPageMyMessage(IPage<CustomerMessage> page,Integer customerInfoId);
	
	/**
	 * 将用户所有的消息设置为已读
	 * 
	 * @param customerInfoId
	 */
	void updateAllMessageToRead(Integer customerInfoId);
}
