package com.farst.common.service.impl;
  
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.farst.common.mybatis.mapper.BasicMapper;
import com.farst.common.service.IBasicService;


public class BasicServiceImpl<M extends BasicMapper<T>, T> extends ServiceImpl<BasicMapper<T>, T> implements IBasicService<T> {
	
}
