package com.oneplatform.permission.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.permission.dao.entity.UserRoleEntity;
import com.oneplatform.permission.dto.param.UserRoleQueryParam;

public interface UserRoleEntityMapper extends BaseMapper<UserRoleEntity, Integer> {

    List<UserRoleEntity> findByQueryParam(UserRoleQueryParam queryParam);

    List<UserRoleEntity> findGrantedUserRoles(@Param("roleType")String roleType,@Param("userId")String userId, @Param("departmentId")String departmentId);

    List<UserRoleEntity> findByTags(List<String> tags);
}