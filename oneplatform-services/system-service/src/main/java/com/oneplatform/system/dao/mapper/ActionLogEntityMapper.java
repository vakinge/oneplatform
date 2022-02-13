package com.oneplatform.system.dao.mapper;

import java.util.List;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.system.dao.entity.ActionLogEntity;
import com.oneplatform.system.dto.param.LogQueryParam;

public interface ActionLogEntityMapper extends BaseMapper<ActionLogEntity, String> {
	
	List<ActionLogEntity> findListByQueryParam(LogQueryParam param);
}