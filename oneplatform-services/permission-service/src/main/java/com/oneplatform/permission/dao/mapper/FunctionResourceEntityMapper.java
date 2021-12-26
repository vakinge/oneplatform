package com.oneplatform.permission.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dto.param.FunctionResourceQueryParam;

public interface FunctionResourceEntityMapper extends BaseMapper<FunctionResourceEntity, Integer> {
	
    /**
     * 根据查询参数获取菜单资源列表(模糊查询）
     * @param queryParam
     * @return
     */
    List<FunctionResourceEntity> findByQueryParam(FunctionResourceQueryParam queryParam);

    List<FunctionResourceEntity> findParentMenus(@Param("clientType") String clientType);
    
    List<FunctionResourceEntity> findUserGrantedResources(@Param("clientType") String clientType,@Param("userId") String userId);
    
    List<FunctionResourceEntity> findRoleGrantedRelations(@Param("clientType") String clientType,@Param("roleIds") List<Integer> roleIds);

}