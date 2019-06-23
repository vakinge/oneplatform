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
package com.oneplatform.system.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oneplatform.system.constants.GrantRelationType;
import com.oneplatform.system.constants.SubRelationType;
import com.oneplatform.system.dao.entity.GrantRelationEntity;
import com.oneplatform.system.dao.entity.SubordinateRelationEntity;
import com.oneplatform.system.dao.mapper.RelationCommonMapper;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月24日
 */
@Component
public class PermissionInternalService {

	private @Autowired RelationCommonMapper relationMapper;

	public void updateSubordinateRelationsByChild(Integer childId, List<Integer> parentIds, String platformType,
			SubRelationType relationType) {
		if (parentIds == null)parentIds = new ArrayList<>(0);
		List<Integer> originParentIds = relationMapper.findSubRelationsByChildId(platformType, relationType.getCode(), childId)
				                           .stream()
				                           .map(e -> e.getParentId()).collect(Collectors.toList());

		List<Integer> addIdList;
		List<Integer> removeIdList = null;
		if (!originParentIds.isEmpty()) {
			addIdList = new ArrayList<>(parentIds);
			addIdList.removeAll(originParentIds);
			removeIdList = new ArrayList<>(originParentIds);
			removeIdList.removeAll(parentIds);
		} else {
			addIdList = parentIds;
		}
		// add new
		if (addIdList != null && !addIdList.isEmpty()) {
			List<SubordinateRelationEntity> addList = addIdList.stream().map(parentId -> {
				return new SubordinateRelationEntity(parentId, childId, relationType,platformType);
			}).collect(Collectors.toList());
			relationMapper.insertSubordinateRelations(addList);
		}
		// remove his
		if (removeIdList != null && !removeIdList.isEmpty()) {
			for (Integer userGroupId : removeIdList) {
				relationMapper.deleteSubordinateRelation(new SubordinateRelationEntity(userGroupId, childId,
						relationType, platformType));
			}
		}
	}
	
	public void updateSubordinateRelationsByParent(Integer parentId, List<Integer> childIds, String platformType,
			SubRelationType relationType) {
		if (childIds == null)childIds = new ArrayList<>(0);
		List<Integer> originChildIds = relationMapper.findSubRelationsByParentId(platformType, relationType.getCode(), parentId)
				                           .stream()
				                           .map(e -> e.getChildId()).collect(Collectors.toList());

		List<Integer> addIdList;
		List<Integer> removeIdList = null;
		if (!originChildIds.isEmpty()) {
			addIdList = new ArrayList<>(childIds);
			addIdList.removeAll(originChildIds);
			removeIdList = new ArrayList<>(originChildIds);
			removeIdList.removeAll(childIds);
		} else {
			addIdList = childIds;
		}
		// add new
		if (addIdList != null && !addIdList.isEmpty()) {
			List<SubordinateRelationEntity> addList = addIdList.stream().map(childId -> {
				return new SubordinateRelationEntity(parentId, childId, relationType,platformType);
			}).collect(Collectors.toList());
			relationMapper.insertSubordinateRelations(addList);
		}
		// remove his
		if (removeIdList != null && !removeIdList.isEmpty()) {
			for (Integer childId : removeIdList) {
				relationMapper.deleteSubordinateRelation(new SubordinateRelationEntity(parentId, childId,
						relationType, platformType));
			}
		}
	}

	public void updateGrantRelations(Integer targetId, List<Integer> sourceIds, String platformType,
			GrantRelationType relationType) {
		if (sourceIds == null)sourceIds = new ArrayList<>(0);
		List<Integer> originSourceIds = relationMapper.findGrantRelationsByTargetId(platformType, relationType.getCode(), targetId)
				                        .stream()
				                        .map(e -> e.getSourceId()).collect(Collectors.toList());
		List<Integer> addIdList;
		List<Integer> removeIdList = null;
		if (!originSourceIds.isEmpty()) {
			addIdList = new ArrayList<>(sourceIds);
			addIdList.removeAll(originSourceIds);
			removeIdList = new ArrayList<>(originSourceIds);
			removeIdList.removeAll(sourceIds);
		} else {
			addIdList = sourceIds;
		}

		if (addIdList != null && !addIdList.isEmpty()) {
			List<GrantRelationEntity> addList = addIdList.stream().map(sourceId -> {
				return new GrantRelationEntity(sourceId, targetId, relationType, platformType);
			}).collect(Collectors.toList());
			relationMapper.insertGrantRelations(addList);
		}

		if (removeIdList != null && !removeIdList.isEmpty()) {
			for (Integer sourceId : removeIdList) {
				relationMapper.deleteGrantRelation(new GrantRelationEntity(sourceId, targetId, relationType, platformType));
			}
		}
	}
}
