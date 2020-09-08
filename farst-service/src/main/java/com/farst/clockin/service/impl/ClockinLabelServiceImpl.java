package com.farst.clockin.service.impl;

import com.farst.clockin.entity.ClockinLabel;
import com.farst.clockin.mapper.ClockinLabelMapper;
import com.farst.clockin.service.IClockinLabelService;
import com.farst.common.service.impl.BasicServiceImpl;
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
public class ClockinLabelServiceImpl extends BasicServiceImpl<ClockinLabelMapper, ClockinLabel> implements IClockinLabelService {

}
