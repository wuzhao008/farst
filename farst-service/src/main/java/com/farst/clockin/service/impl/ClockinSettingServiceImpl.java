package com.farst.clockin.service.impl;

import com.farst.clockin.entity.ClockinSetting;
import com.farst.clockin.mapper.ClockinSettingMapper;
import com.farst.clockin.service.IClockinSettingService;
import com.farst.common.service.impl.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打卡设置 服务实现类
 * </p>
 *
 * @author MichaelWoo
 * @since 2020-09-07
 */
@Service
public class ClockinSettingServiceImpl extends BasicServiceImpl<ClockinSettingMapper, ClockinSetting> implements IClockinSettingService {

}
