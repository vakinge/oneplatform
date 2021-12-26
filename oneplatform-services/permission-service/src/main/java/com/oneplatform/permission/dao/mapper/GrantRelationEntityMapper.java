package com.oneplatform.permission.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.permission.dao.entity.GrantRelationEntity;

public interface GrantRelationEntityMapper extends BaseMapper<GrantRelationEntity, Integer> {

	@Select("SELECT * FROM grant_relations WHERE target_type = #{targetType} AND target_id = #{targetId} AND source_type = #{sourceType}")
	@ResultMap("BaseResultMap")
    List<GrantRelationEntity> findSourceGrantRelations(@Param("targetType")String targetType,
                                                 @Param("targetId")String targetId,@Param("sourceType")String sourceType);

	/**
	 *
	 * @param targetId
	 * @return
	 */
	@Delete("DELETE FROM grant_relations WHERE target_id = #{targetId} AND target_type = #{targetType}")
	int deleteByTargetIdAndTargetType(@Param("targetId") Integer targetId, @Param("targetType") String targetType);
}