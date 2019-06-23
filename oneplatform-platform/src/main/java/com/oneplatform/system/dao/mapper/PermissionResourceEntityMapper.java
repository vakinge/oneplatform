package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.PermissionResourceEntity;
import com.oneplatform.system.dto.param.QueryResourceParam;

public interface PermissionResourceEntityMapper extends CustomBaseMapper<PermissionResourceEntity> {
	
	@Pageable
	List<PermissionResourceEntity> findListByParam(QueryResourceParam param);
	
	@Select("select * from sys_permission_resource where enabled = 1 order by id")
	@ResultMap("BaseResultMap")
	List<PermissionResourceEntity> findAllEnabled();
	
	List<PermissionResourceEntity> findRelateMenuByUserGroupIds(List<Integer> userGroupIds);
	
	List<PermissionResourceEntity> findRelateApiByUserGroupIds(List<Integer> userGroupIds);
	
	List<PermissionResourceEntity> findByIds(@Param("type") String type,@Param("ids") List<Integer> ids);
	
	List<PermissionResourceEntity> findByPermGroupId(@Param("type") String type,@Param("permGroupId") int permGroupId);
	
	
	@Select("select * from sys_permission_resource where type='menu' and enabled = 1 and (uri='' or uri is null) order by id")
	@ResultMap("BaseResultMap")
	List<PermissionResourceEntity> findNotLeafMenus();
	
	@Select("select * from sys_permission_resource where platform_type=#{platformType} and type=#{type} and name=#{name} limit 1")
	@ResultMap("BaseResultMap")
	PermissionResourceEntity findByTypeAndName(@Param("platformType") String platformType,@Param("type") String type,@Param("name") String name);

	@Select("select * from sys_permission_resource where type='api' and module_id=#{moduleId} and uri=#{uri} limit 1")
	@ResultMap("BaseResultMap")
	PermissionResourceEntity findApiResourceByUri(@Param("moduleId") Integer moduleId,@Param("uri") String uri);

	@Select("select * from sys_permission_resource where type='menu' and module_id=#{moduleId} and parent_id = 0 limit 1")
	@ResultMap("BaseResultMap")
	PermissionResourceEntity findModuleTopMenu(@Param("moduleId") Integer moduleId);

	

}