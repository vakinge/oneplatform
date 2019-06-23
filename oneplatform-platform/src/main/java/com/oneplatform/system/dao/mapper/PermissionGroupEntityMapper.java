package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.PermissionGroupEntity;

public interface PermissionGroupEntityMapper extends CustomBaseMapper<PermissionGroupEntity> {
	
    List<PermissionGroupEntity> findListByPlatform(String platformType);
	
	@Select("select * from sys_permission_group where menu_id=#{menuId} and enabled=1")
	@ResultMap("BaseResultMap")
	List<PermissionGroupEntity> findListByMenuId(Integer menuId);
}