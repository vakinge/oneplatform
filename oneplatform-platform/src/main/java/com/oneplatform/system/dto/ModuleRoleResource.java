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

import java.util.ArrayList;
import java.util.List;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月4日
 */
public class ModuleRoleResource {

	private Integer moduleId;
	private String moduleName;
	
	private List<RoleResource> roleResources = new ArrayList<>();
	
	public ModuleRoleResource() {}
	
	public ModuleRoleResource(Integer moduleId, String moduleName) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
	}


	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<RoleResource> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(List<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}
	
	
}
