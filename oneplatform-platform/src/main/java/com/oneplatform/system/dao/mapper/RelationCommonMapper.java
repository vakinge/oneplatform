/*
 * Copyright 2016-2018 www.jeesuite.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oneplatform.system.dao.entity.GrantRelationEntity;
import com.oneplatform.system.dao.entity.SubordinateRelationEntity;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月22日
 */
public interface RelationCommonMapper {

	int insertSubordinateRelation(SubordinateRelationEntity relation);
	int insertSubordinateRelations(	List<SubordinateRelationEntity> relations);
	
	int insertGrantRelation(GrantRelationEntity relation);
	int insertGrantRelations(List<GrantRelationEntity> relations);
	
	void deleteSubordinateRelation(SubordinateRelationEntity entity);
	void deleteGrantRelation(GrantRelationEntity entity);

	List<SubordinateRelationEntity> findSubRelationsByParentId(@Param("platformType") String platformType,@Param("relationType") Integer relationType,@Param("parentId") Integer parentId);

	List<SubordinateRelationEntity> findSubRelationsByParentIds(@Param("platformType") String platformType,@Param("relationType") Integer relationType,@Param("parentIds") List<Integer> parentIds);

	List<SubordinateRelationEntity> findSubRelationsByChildId(@Param("platformType") String platformType,@Param("relationType") Integer relationType,@Param("childId") Integer childId);

	List<GrantRelationEntity> findGrantRelationsByTargetId(@Param("platformType") String platformType,@Param("relationType") Integer relationType,@Param("targetId") Integer targetId);

    List<String> findUserRelatePlatform(Integer userId);
}
