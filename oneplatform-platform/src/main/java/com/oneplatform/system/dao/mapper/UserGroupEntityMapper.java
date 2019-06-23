package com.oneplatform.system.dao.mapper;

import java.util.List;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.UserGroupEntity;
import com.oneplatform.system.dto.param.QueryUserGroupParam;

public interface UserGroupEntityMapper extends CustomBaseMapper<UserGroupEntity> {

	List<UserGroupEntity> findListByParam(QueryUserGroupParam param);
}