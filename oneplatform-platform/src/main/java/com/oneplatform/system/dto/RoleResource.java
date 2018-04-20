/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.system.dto;

import com.oneplatform.system.constants.ResourceType;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月4日
 */
public class RoleResource {

	private Integer id;
	private Integer moduleId;
	private String name;
	private String type;
	private boolean assigned;
	
	
	public RoleResource() {}
	public RoleResource(Integer id, Integer moduleId, String name, String type) {
		this.id = id;
		this.moduleId = moduleId;
		this.name = name;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAssigned() {
		return assigned;
	}
	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	
	public String getTypeName() {
		return ResourceType.valueOf(type).typeName;
	}
}
