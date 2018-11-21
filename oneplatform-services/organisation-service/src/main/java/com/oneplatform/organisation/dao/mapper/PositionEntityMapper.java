package com.oneplatform.organisation.dao.mapper;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.organisation.dao.entity.PositionEntity;

public interface PositionEntityMapper extends CustomBaseMapper<PositionEntity> {
	
	@Select("select * from positions where name=#{name} limit 1")
	@ResultMap("BaseResultMap")
	PositionEntity findByName(String name);
}