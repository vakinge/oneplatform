package com.oneplatform.modules.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.modules.system.dao.entity.SubordinateRelationEntity;
import com.oneplatform.modules.system.dto.param.SubordinateRelationQueryParam;

public interface SubordinateRelationEntityMapper extends BaseMapper<SubordinateRelationEntity, Integer> {

	@Select("SELECT * FROM subordinate_relations WHERE relation_type = #{relationType} AND child_id = #{childId}")
	@ResultMap("BaseResultMap")
	List<SubordinateRelationEntity> findParentRelations(@Param("relationType")String relationType,@Param("childId")String childId);

	/**
	 * 查找所有从属关系
     * @param queryParam 查询参数
	 * @return
	 */
	List<SubordinateRelationEntity> findSubRelationByQueryParam(SubordinateRelationQueryParam queryParam);

    /**
     * 根据parentId删除所有关联关系
     * @param parentId
     */
    void deleteByParentId(@Param("parentId") String parentId);

    /**
     * 根据childId删除所有关联关系
     * @param childId
     */
    void deleteByChildId(@Param("childId") String childId);

	@Delete("delete from subordinate_relations where child_id = #{userId} and parent_id = #{roleId} and relation_type = 'userToGroup'")
	void deleteUserRoleRelation(@Param("userId") String userId, @Param("roleId") String roleId);

}