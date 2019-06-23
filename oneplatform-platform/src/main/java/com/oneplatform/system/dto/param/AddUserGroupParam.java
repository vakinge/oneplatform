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
package com.oneplatform.system.dto.param;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月16日
 */
public class AddUserGroupParam {

	private String name;

    @ApiModelProperty("平台类型")
    private String platformType;
       
    private List<Integer> permGroupIds;
    
    private List<Integer> extrMenuIds;
	private List<Integer> extrApiIds;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public List<Integer> getPermGroupIds() {
		return permGroupIds;
	}
	public void setPermGroupIds(List<Integer> permGroupIds) {
		this.permGroupIds = permGroupIds;
	}
	public List<Integer> getExtrMenuIds() {
		return extrMenuIds;
	}
	public void setExtrMenuIds(List<Integer> extrMenuIds) {
		this.extrMenuIds = extrMenuIds;
	}
	public List<Integer> getExtrApiIds() {
		return extrApiIds;
	}
	public void setExtrApiIds(List<Integer> extrApiIds) {
		this.extrApiIds = extrApiIds;
	}

	
}
