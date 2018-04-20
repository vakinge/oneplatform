package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.RoleEntity;

public interface RoleEntityMapper extends CustomBaseMapper<RoleEntity> {
	
	List<RoleEntity> findUserRoles(int accountId);
	
	@Select("select * from sys_role where name = #{name} limit 1")
	@ResultMap("BaseResultMap")
	RoleEntity findByName(String name);
	
	@Select("select role_id from sys_account_roles where account_id = #{accountId}")
	@ResultType(Integer.class)
	List<Integer> findAccountRoleIds(Integer accountId);
	
	@Select("select count(1) from sys_account_roles where role_id = #{roleId}")
	@ResultType(Integer.class)
	Integer countRoleBindAccount(Integer roleId);
	
	int deleteAccountRoles(@Param("accountId") Integer accountId,@Param("roleIds") Integer[] roleIds);
	
	int addAccountRoles(@Param("accountId") Integer accountId,@Param("roleIds") Integer[] roleIds);
}