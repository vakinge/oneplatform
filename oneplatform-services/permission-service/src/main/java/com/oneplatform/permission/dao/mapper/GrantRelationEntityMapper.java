package com.oneplatform.permission.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.permission.dao.entity.GrantRelationEntity;

public interface GrantRelationEntityMapper extends BaseMapper<GrantRelationEntity, Integer> {


    List<GrantRelationEntity> findGrantRelations(
    		@Param("sourceType")String sourceType
    		,@Param("targetType")String targetType
    		,@Param("targetId")String targetId
    		);

	/**
	 *
	 * @param targetId
	 * @return
	 */
	@Delete("DELETE FROM grant_relations WHERE target_id = #{targetId} AND target_type = #{targetType}")
	@ResultType(Integer.class)
	int deleteByTargetIdAndTargetType(@Param("targetId") Integer targetId, @Param("targetType") String targetType);
}