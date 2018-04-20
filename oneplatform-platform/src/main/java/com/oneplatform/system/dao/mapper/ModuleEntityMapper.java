package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.ModuleEntity;

public interface ModuleEntityMapper extends CustomBaseMapper<ModuleEntity> {
	
	@Cache
	@Select("SELECT * FROM sys_module where service_id=#{serviceId} limit 1")
	@ResultMap("BaseResultMap")
	ModuleEntity findByServiceId(@Param("serviceId") String serviceId);
	
	@Cache
	@Select("SELECT * FROM sys_module where route_name=#{routeName} limit 1")
	@ResultMap("BaseResultMap")
	ModuleEntity findByRouteName(@Param("routeName") String routeName);
	
	List<ModuleEntity> findAllEnabled();
	
	List<ModuleEntity> findAll();
}