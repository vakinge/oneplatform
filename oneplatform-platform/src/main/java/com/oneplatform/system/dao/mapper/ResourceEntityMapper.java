package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.ResourceEntity;

public interface ResourceEntityMapper extends CustomBaseMapper<ResourceEntity> {
	
	@Select("select * from sys_resources where module_id = #{moduleId} and code = #{code} limit 1")
	@ResultMap("BaseResultMap")
	ResourceEntity findByModuleAndCode(@Param("moduleId") int moduleId,@Param("code") String code);
	
	@Select("select * from sys_resources where module_id = #{moduleId} and resource = #{resource} limit 1")
	@ResultMap("BaseResultMap")
	ResourceEntity findByModuleAndResource(@Param("moduleId") int moduleId,@Param("resource") String resource);
	
	@Select("select * from sys_resources where module_id = #{moduleId} and parent_id=0 limit 1")
	@ResultMap("BaseResultMap")
	ResourceEntity findModuleParent(int moduleId);
	
	@Select("select resource from sys_resources where module_id = #{moduleId} and type=#{type}")
	@ResultType(String.class)
	List<String> findResourceFieldByModule(@Param("moduleId") int moduleId,@Param("type") String type);
	
	List<ResourceEntity> findAllResources();
	
	@Select("select * from sys_resources where is_default = 1 and parent_id > 0 order by sort")
	@ResultMap("BaseResultMap")
	List<ResourceEntity> findDefaultResources(String type);
	
	List<ResourceEntity> findUserResources(@Param("accountId") int accountId,@Param("type") String type);
	
	List<ResourceEntity> findRoleResources(@Param("roleId") int roleId,@Param("type") String type);
	
	List<ResourceEntity> findNotLeafResources(@Param("type") String type);
	
	List<ResourceEntity> findLeafResources(@Param("type") String type);
	
	List<ResourceEntity> findResources(@Param("type") String type);
	
	List<ResourceEntity> findAllNotMenuResources();
	
	@Select("select resource_id from sys_role_resources rr join sys_resources r  ON r.id = rr.resource_id WHERE rr.role_id=#{roleId} and r.type=#{type}")
	@ResultType(Integer.class)
	List<Integer> findRoleResourceIds(@Param("type") String type,@Param("roleId") Integer roleId);
	
	int deleteAllRoleResources(Integer roleId);
	
	int deleteResourceRalations(Integer resourceId);
	
	int deleteRoleResources(@Param("roleId") Integer roleId,@Param("resourceIds") Integer[] resourceIds);
	
	int addRoleResources(@Param("roleId") Integer roleId,@Param("resourceIds") Integer[] resourceIds);
	
}