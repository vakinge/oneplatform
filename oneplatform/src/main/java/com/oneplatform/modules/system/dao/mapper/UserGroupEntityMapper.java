package com.oneplatform.modules.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.modules.system.dao.entity.UserGroupEntity;
import com.oneplatform.modules.system.dto.param.UserGroupQueryParam;

public interface UserGroupEntityMapper extends BaseMapper<UserGroupEntity, Integer> {

    List<UserGroupEntity> findByQueryParam(UserGroupQueryParam queryParam);

    List<UserGroupEntity> findGrantedUserGroups(@Param("tenantId")String tenantId, @Param("departmentId")String departmentId,@Param("userType")String userType, @Param("userId")String userId);
}