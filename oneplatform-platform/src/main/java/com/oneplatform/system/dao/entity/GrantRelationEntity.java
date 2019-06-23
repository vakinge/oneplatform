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

import com.oneplatform.system.constants.GrantRelationType;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月22日
 */
public class GrantRelationEntity {

	private Integer sourceId;
	private Integer targetId;
	private Integer relationType;
	private String platformType;
	
	public GrantRelationEntity() {}
	
	public GrantRelationEntity(Integer sourceId, Integer targetId, GrantRelationType relationType) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.relationType = relationType.getCode();
	}

	public GrantRelationEntity(Integer sourceId, Integer targetId, GrantRelationType relationType, String platformType) {
		super();
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.relationType = relationType.getCode();
		this.platformType = platformType;
	}


	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
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
	
	
}
