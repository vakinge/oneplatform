package com.oneplatform.organisation.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.organisation.dao.entity.CompanyEntity;

public interface CompanyEntityMapper extends CustomBaseMapper<CompanyEntity> {
	
	@Pageable
	List<CompanyEntity> findByParam(Map<String, Object> param);
	
	@Select("select * from company where is_branch=0 limit 1")
	@ResultMap("BaseResultMap")
	CompanyEntity findHeadCompany();
	
	@Select("select * from company where name=#{name} limit 1")
	@ResultMap("BaseResultMap")
	CompanyEntity findByName(String name);
	
	@Select("select * from company where in_active=1 order by id")
	@ResultMap("BaseResultMap")
	List<CompanyEntity> findAllActive();
}