package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.entity.RoleEntity;

public interface ResourceEntityMapper extends CustomBaseMapper<ResourceEntity> {
	
	@Select("select * from sys_resources where module_id = #{moduleId} and code = #{code} limit 1")
	@ResultMap("BaseResultMap")
	ResourceEntity findByModuleAndCode(@Param("moduleId") int moduleId,@Param("code") String code);
	
	List<ResourceEntity> findAllResources();
	
	List<ResourceEntity> findUserResources(@Param("accountId") int accountId,@Param("type") String type);
	
	List<ResourceEntity> findRoleResources(@Param("roleId") int roleId,@Param("type") String type);
	
	List<ResourceEntity> findNotLeafResources(@Param("type") String type);
	
	List<ResourceEntity> findLeafResources(@Param("type") String type);
	
	List<ResourceEntity> findResources(@Param("type") String type);
	
	List<ResourceEntity> findAllNotMenuResources();
	
	@Select("select resource_id from sys_role_resources where role_id = #{roleId}")
	@ResultType(Integer.class)
	List<Integer> findRoleResourceIds(Integer roleId);
	
	int deleteAllRoleResources(Integer roleId);
	
	int deleteResourceRalations(Integer resourceId);
	
	int deleteRoleResources(@Param("roleId") Integer roleId,@Param("resourceIds") Integer[] resourceIds);
	
	int addRoleResources(@Param("roleId") Integer roleId,@Param("resourceIds") Integer[] resourceIds);
	
}