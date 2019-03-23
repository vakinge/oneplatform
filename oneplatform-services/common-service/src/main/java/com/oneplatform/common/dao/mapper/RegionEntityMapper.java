package com.oneplatform.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.common.dao.entity.RegionEntity;

public interface RegionEntityMapper extends BaseMapper<RegionEntity,Integer> {
	
	
	@Select("select * from static_region where parent_id = #{pid}")
	@ResultMap("BaseResultMap")
	List<RegionEntity> findByParentId(int pid);
}