package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.mapper.CustomerLabelMapper;
import com.farst.customer.service.ICustomerLabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.service.IClockinLabelService; 
import com.farst.clockin.vo.AllClockinLabelVo;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户打卡标签 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class CustomerLabelServiceImpl extends BasicServiceImpl<CustomerLabelMapper, CustomerLabel>
		implements ICustomerLabelService {

	@Autowired
	private IClockinLabelService clockinLabelService;

	@Override
	public List<CustomerLabel> getListCustomerLabel(Integer customerInfoId) {
		QueryWrapper<CustomerLabel> queryWrapper = new QueryWrapper<CustomerLabel>();
		queryWrapper.eq("status", 0).eq("customer_info_id", customerInfoId).orderByAsc("id");
		return this.list(queryWrapper);
	}

	@Override
	public boolean hasCustomerLabel(Integer customerInfoId) {
		List<CustomerLabel> listCustomerLabel = this.getListCustomerLabel(customerInfoId);
		return CollectionUtils.isEmpty(listCustomerLabel) ? false : true;
	}

	@Override
	public List<AllClockinLabelVo> getListSelectClockinLabelVo(Integer customerInfoId) {
		List<AllClockinLabelVo> listSelectClockinLabelVo = new ArrayList<AllClockinLabelVo>();
		List<Integer> listCustClockinLabelId = this.getListClockLabelId(customerInfoId);
		// 先获取第一级标签
		List<ClockinLabel> listClockinLabel = this.clockinLabelService.getListClockinLabelByPid(null);
		for (ClockinLabel clockinLabel : listClockinLabel) {
			AllClockinLabelVo selectClockinLabelVo = new AllClockinLabelVo();
			selectClockinLabelVo.setClockinLabel(clockinLabel);
			//获取对应的二级标签
			List<ClockinLabel> listChildClockinLabel = this.clockinLabelService
					.getListClockinLabelByPid(clockinLabel.getId());
			List<ClockinLabel> listChildClockinLabel_ = new ArrayList<ClockinLabel>();
			for(ClockinLabel childClockinLabel : listChildClockinLabel) {
				//判断当前用户是否选中了此标签,没有选中的标签才让用户选
				boolean hasSelected = listCustClockinLabelId.contains(childClockinLabel.getId()) ? true : false;
				if(hasSelected == false) {
					listChildClockinLabel_.add(childClockinLabel);
				}
			}
			if(CollectionUtils.isNotEmpty(listChildClockinLabel_)) {
				selectClockinLabelVo.setChildClockinLabels(listChildClockinLabel_);
				listSelectClockinLabelVo.add(selectClockinLabelVo);
			}
		}
		return listSelectClockinLabelVo;
	}

	@Override
	public List<Integer> getListClockLabelId(Integer customerInfoId) {
		List<CustomerLabel> listCustomerLabel = this.getListCustomerLabel(customerInfoId);
		List<Integer> listClockinLabelId = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(listCustomerLabel)) {
			listCustomerLabel.forEach(customerLabel -> {
				listClockinLabelId.add(customerLabel.getClockinLabelId());
			});
		}
		return listClockinLabelId;
	}

	@Override
	public CustomerLabel getCustomerLabelRecord(Integer customerInfoId, Integer labelId) {
		QueryWrapper<CustomerLabel> queryWrapper = new QueryWrapper<CustomerLabel>();
		queryWrapper.eq("customer_info_id", customerInfoId).eq("clockin_label_id", labelId);
		return this.getOne(queryWrapper);
	}

	@Override
	public void addCustomerLabel(Integer customerInfoId, List<Integer> listLabelId) {
		//循环新标签ID
		if(CollectionUtils.isNotEmpty(listLabelId)) {
			for(Integer labelId : listLabelId) {
				CustomerLabel customerLabel = new CustomerLabel();
				customerLabel.setCustomerInfoId(customerInfoId);
				customerLabel.setClockinLabelId(labelId);
				customerLabel.setStatus(0);
				customerLabel.setCreateDate(new Date());
				this.save(customerLabel); 
			}
		}
	}

}
