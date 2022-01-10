package com.oneplatform.organization.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.oneplatform.organization.dao.entity.StaffEntity;
import com.oneplatform.organization.dto.StaffDetails;
import com.oneplatform.organization.dto.param.StaffQueryParam;

public interface StaffEntityMapper extends BaseMapper<StaffEntity, String> {
	
	List<StaffEntity> findListByParam(StaffQueryParam param);
	
	List<StaffDetails> findDetailsListByParam(StaffQueryParam param);
	
	@Cache
	@Select("SELECT * FROM staff WHERE mobile=#{mobile} AND enabled=1 LIMIT 1")
	@ResultMap("BaseResultMap")
	StaffEntity findByMobile(String mobile);
}