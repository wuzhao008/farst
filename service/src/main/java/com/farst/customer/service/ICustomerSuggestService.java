package com.farst.customer.service;
 
import com.farst.customer.entity.CustomerSuggest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.farst.common.service.IBasicService;

/**
 * <p>
 * 用户建议 服务类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-26
 */
public interface ICustomerSuggestService extends IBasicService<CustomerSuggest> {

	/**
	 * 得到用户建议分页列表
	 * 
	 * @param customerInfoId
	 * @return
	 */
	IPage<CustomerSuggest> getPageMySuggest(IPage<CustomerSuggest> page,Integer customerInfoId);
	
	/**
	 * 删除对应id的用户建议信息
	 * 
	 * @param id
	 */
	void deleteCustomerSuggest(Integer id);
}
