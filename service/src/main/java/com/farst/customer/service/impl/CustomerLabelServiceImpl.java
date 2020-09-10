package com.farst.customer.service.impl;

import com.farst.customer.entity.CustomerLabel;
import com.farst.customer.mapper.CustomerLabelMapper;
import com.farst.customer.service.ICustomerLabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.vo.ChildClockinLabelVo;
import com.farst.clockin.vo.SelectClockinLabelVo;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.ArrayList;
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
		queryWrapper.eq("status", 0).eq("customer_info_id", customerInfoId);
		return this.list(queryWrapper);
	}

	@Override
	public boolean hasCustomerLabel(Integer customerInfoId) {
		List<CustomerLabel> listCustomerLabel = this.getListCustomerLabel(customerInfoId);
		return CollectionUtils.isEmpty(listCustomerLabel) ? false : true;
	}

	@Override
	public List<SelectClockinLabelVo> getListSelectClockinLabelVo(Integer customerInfoId) {
		
		List<SelectClockinLabelVo> listSelectClockinLabelVo = new ArrayList<SelectClockinLabelVo>();
		
		List<Integer> listCustClockinLabelId = this.getListClockLabelId(customerInfoId);
		// 先获取第一级标签
		List<ClockinLabel> listClockinLabel = this.clockinLabelService.getListClockinLabelByPid(null);
		for (ClockinLabel clockinLabel : listClockinLabel) {
			SelectClockinLabelVo selectClockinLabelVo = new SelectClockinLabelVo();
			selectClockinLabelVo.setClockinLabel(clockinLabel);
			
			//获取对应的二级标签
			List<ChildClockinLabelVo> listChildClockinLabelVo = new ArrayList<ChildClockinLabelVo>();
			List<ClockinLabel> listChildClockinLabel = this.clockinLabelService
					.getListClockinLabelByPid(clockinLabel.getId());
			for(ClockinLabel childClockinLabel : listChildClockinLabel) {
				ChildClockinLabelVo childClockinLabelVo = new ChildClockinLabelVo();
				childClockinLabelVo.setClockinLabel(childClockinLabel);
				//判断当前用户是否选中了此标签
				boolean hasSelected = listCustClockinLabelId.contains(childClockinLabel.getId()) ? true : false;
				childClockinLabelVo.setHasSelected(hasSelected);
				listChildClockinLabelVo.add(childClockinLabelVo);
			}
			selectClockinLabelVo.setChildClockinLabelVos(listChildClockinLabelVo);
			listSelectClockinLabelVo.add(selectClockinLabelVo);
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

}
