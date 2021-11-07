package com.oneplatform.modules.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.modules.system.dao.entity.FunctionResourceEntity;
import com.oneplatform.modules.system.dto.param.FunctionResourceQueryParam;

public interface FunctionResourceEntityMapper extends BaseMapper<FunctionResourceEntity, Integer> {
	
	@Select("SELECT * FROM function_resource WHERE uri=#{uri} LIMIT 1")
	@ResultMap("BaseResultMap")
	FunctionResourceEntity findByUri(@Param("uri")String uri);
    /**
     * 根据查询参数获取菜单资源列表(模糊查询）
     * @param queryParam
     * @return
     */
    List<FunctionResourceEntity> findByQueryParam(FunctionResourceQueryParam queryParam);

    @Select("SELECT * FROM function_resource WHERE enabled = 1 AND (uri IS NULL OR uri = '') ORDER BY id")
	@ResultMap("BaseResultMap")
    List<FunctionResourceEntity> findParents();
    
    List<FunctionResourceEntity> findUserGrantedResources(@Param("clientType") String clientType,@Param("userId") String userId);
    
    List<FunctionResourceEntity> findRoleGrantedRelations(@Param("clientType") String clientType,@Param("roleIds") List<Integer> roleIds);

}