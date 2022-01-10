package com.oneplatform.organization.dao.mapper;

import java.util.List;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.organization.dao.entity.DepartmentEntity;
import com.oneplatform.organization.dto.param.DepartmentQueryParam;

public interface DepartmentEntityMapper extends BaseMapper<DepartmentEntity, String> {
	
	List<DepartmentEntity> findListByQueryParam(DepartmentQueryParam param);
}