package com.oneplatform.system.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.SysLogEntity;

public interface SysLogEntityMapper extends CustomBaseMapper<SysLogEntity> {
	
	Page<SysLogEntity> pageQuery(@Param("pageParam") PageParams page ,@Param("params") Map<String, Object> params);
}