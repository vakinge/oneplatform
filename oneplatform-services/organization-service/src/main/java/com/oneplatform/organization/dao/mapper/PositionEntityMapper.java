package com.oneplatform.organization.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.organization.dao.entity.PositionEntity;
import com.oneplatform.organization.dto.param.PositionQueryParam;

public interface PositionEntityMapper extends BaseMapper<PositionEntity, String> {
	
	@Select("SELECT * FROM position WHERE department_id=#{departmentId} AND enabled=1")
	@ResultMap("BaseResultMap")
	List<PositionEntity> findByDepartmentId(String departmentId);

	List<PositionEntity> findListByParam(PositionQueryParam param);
}