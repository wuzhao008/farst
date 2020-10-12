package com.farst.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.mapper.ClockinLabelMapper;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.clockin.vo.AllClockinLabelVo;
import com.farst.common.service.impl.BasicServiceImpl;

import java.util.ArrayList;
import java.util.List; 
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡标签 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinLabelServiceImpl extends BasicServiceImpl<ClockinLabelMapper, ClockinLabel>
		implements IClockinLabelService {

	@Override
	public List<ClockinLabel> getListClockinLabelByPid(Integer pid) {
		QueryWrapper<ClockinLabel> queryWrapper = new QueryWrapper<ClockinLabel>();
		if (pid == null) {
			queryWrapper.eq("status", 0).isNull("pid").orderByAsc("sort");
		} else {
			queryWrapper.eq("status", 0).eq("pid", pid).orderByAsc("sort");
		}
		return this.list(queryWrapper);
	}

	@Override
	public List<ClockinLabel> getListClockinLabelByListId(List<Integer> listLabelId) {
		QueryWrapper<ClockinLabel> queryWrapper = new QueryWrapper<ClockinLabel>();
		queryWrapper.eq("status", 0).in("id", listLabelId);
		return this.list(queryWrapper);
	}

	@Override
	public List<AllClockinLabelVo> getListAllClockinLabelVo() {

		List<AllClockinLabelVo> listAllClockinLabelVo = new ArrayList<AllClockinLabelVo>(); 
		// 先获取第一级标签
		List<ClockinLabel> listClockinLabel = this.getListClockinLabelByPid(null);
		for (ClockinLabel clockinLabel : listClockinLabel) {
			AllClockinLabelVo allClockinLabelVo = new AllClockinLabelVo();
			allClockinLabelVo.setClockinLabel(clockinLabel);
			//获取对应的二级标签
			List<ClockinLabel> listChildClockinLabel = this.getListClockinLabelByPid(clockinLabel.getId());
			allClockinLabelVo.setChildClockinLabels(listChildClockinLabel);
			listAllClockinLabelVo.add(allClockinLabelVo);
		}
		return listAllClockinLabelVo;
	
	}

}
