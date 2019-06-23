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
package com.oneplatform.system.dao.entity;

import com.oneplatform.system.constants.SubRelationType;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月22日
 */
public class SubordinateRelationEntity {

	private Integer parentId;
	private Integer childId;
	private Integer relationType;
	private String platformType;
	
	public SubordinateRelationEntity() {}
	
	public SubordinateRelationEntity(Integer parentId, Integer childId, SubRelationType relationType) {
		this.parentId = parentId;
		this.childId = childId;
		this.relationType = relationType.getCode();
	}

	public SubordinateRelationEntity(Integer parentId, Integer childId, SubRelationType relationType,String platformType) {
		super();
		this.parentId = parentId;
		this.childId = childId;
		this.relationType = relationType.getCode();
		this.platformType = platformType;
	}


	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childId == null) ? 0 : childId.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((relationType == null) ? 0 : relationType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubordinateRelationEntity other = (SubordinateRelationEntity) obj;
		if (childId == null) {
			if (other.childId != null)
				return false;
		} else if (!childId.equals(other.childId))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (relationType == null) {
			if (other.relationType != null)
				return false;
		} else if (!relationType.equals(other.relationType))
			return false;
		return true;
	}
	
	
}
