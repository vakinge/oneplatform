package com.oneplatform.organisation.dao.mapper;

import java.util.List;
import java.util.Map;

import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.organisation.dao.entity.EmployeeEntity;

public interface EmployeeEntityMapper extends CustomBaseMapper<EmployeeEntity> {
	
	@Pageable
	List<EmployeeEntity> findByParam(Map<String, Object> param);
}