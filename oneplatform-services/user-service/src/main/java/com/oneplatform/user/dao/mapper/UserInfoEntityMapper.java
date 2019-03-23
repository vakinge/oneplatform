package com.oneplatform.user.dao.mapper;

import java.util.List;
import java.util.Map;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.user.dao.entity.UserInfoEntity;

public interface UserInfoEntityMapper extends BaseMapper<UserInfoEntity, Integer> {
	
	List<UserInfoEntity> findByParam(Map<String, Object> param);
}