package com.oneplatform.modules.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.modules.system.dao.entity.ApiResourceEntity;
import com.oneplatform.modules.system.dto.param.ApiResourceQueryParam;

public interface ApiResourceEntityMapper extends BaseMapper<ApiResourceEntity, Integer> {
	
	@Select("SELECT * FROM api_resource WHERE app_id=#{appId} AND uri=#{uri} AND http_method=#{httpMethod} LIMIT 1")
	@ResultMap("BaseResultMap")
	ApiResourceEntity findByRequestUri(@Param("appId") String appId,@Param("uri") String uri,@Param("httpMethod") String httpMethod);
    /**
     * 根据查询参数获取api列表(模糊查询）
     * @param queryParam
     * @return
     */
    List<ApiResourceEntity> findByQueryParam(ApiResourceQueryParam queryParam);

    /**
     * 根据id列表批量查询
     * @param ids id列表
     * @return
     */
    List<ApiResourceEntity> findByIds(List<Integer> ids);
    
    List<ApiResourceEntity> findByUserGrantRelations(@Param("userId") String userId);
    
    List<ApiResourceEntity> findByRolesGrantRelations(@Param("roleIds") List<Integer> roleIds);
}