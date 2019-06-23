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
package com.oneplatform.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.security.client.LoginContext;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.annotation.ApiPermOptions;
import com.oneplatform.base.constants.PermissionType;
import com.oneplatform.base.model.IdParam;
import com.oneplatform.system.dto.param.AddPermGroupParam;
import com.oneplatform.system.dto.param.AddResourceParam;
import com.oneplatform.system.dto.param.AddUserGroupParam;
import com.oneplatform.system.dto.param.BatchCreateResourceParam;
import com.oneplatform.system.dto.param.BatchCreateResourceParam.ApiDefine;
import com.oneplatform.system.dto.param.GrantPermissionParam;
import com.oneplatform.system.dto.param.SetBindingParam;
import com.oneplatform.system.dto.param.UpdatePermGroupParam;
import com.oneplatform.system.dto.param.UpdateResourceParam;
import com.oneplatform.system.dto.param.UpdateUserGroupParam;
import com.oneplatform.system.service.PermissionService;

import io.swagger.annotations.ApiOperation;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月16日
 */
@Controller
@RequestMapping(value = "/perm")
@ApiPermOptions(perms = PermissionType.Authorized)
public class PermissionController {

	private @Autowired PermissionService permissionService;
	
	@RequestMapping(value = "usergroup/add", method = RequestMethod.POST)
	@ApiOperation(value = "新增用户组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> addUserGroup(@RequestBody AddUserGroupParam param){
		permissionService.addUserGroup(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "usergroup/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> updateUserGroup(@RequestBody UpdateUserGroupParam param){
		permissionService.updateUserGroup(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "usergroup/delete", method = RequestMethod.POST)
	@ApiOperation(value = "删除用户组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> deleteUserGroup(@RequestBody IdParam param){
		permissionService.deleteUserGroup(LoginContext.getIntFormatUserId(), param.getId());
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "permgroup/add", method = RequestMethod.POST)
	@ApiOperation(value = "新增权限组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> addPermGroup(@RequestBody AddPermGroupParam param){
		param.validate();
		permissionService.addPermGroup(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "permgroup/delete", method = RequestMethod.POST)
	@ApiOperation(value = "删除权限组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> deletePermGroup(@RequestBody IdParam param){
		permissionService.deletePermGroup(LoginContext.getIntFormatUserId(), param.getId());
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "permgroup/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新权限组", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> updatePermGroup(@RequestBody UpdatePermGroupParam param){
		if(param.getApiIds() == null || param.getApiIds().isEmpty())throw new JeesuiteBaseException(400,"关联接口[apiIds]必填");
		permissionService.updatePermGroup(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "新增权限资源")
	@RequestMapping(value = "resource/add", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> addResource(@RequestBody AddResourceParam param) {
		permissionService.addResource(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "resource/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新权限资源", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> updateResource(@RequestBody UpdateResourceParam param){
		permissionService.updateResource(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	
	@RequestMapping(value = "status/switch", method = RequestMethod.POST)
	@ApiOperation(value = "设置启用/禁用状态", httpMethod = "POST")
	public @ResponseBody WrapperResponse<String> setPermObjectStatus(@RequestParam("id") Integer id,@RequestParam("type") String type){
		permissionService.setPermObjectStatus(id, type);
		return new WrapperResponse<>();
	}

	@ApiOperation(value = "分配用户权限")
	@RequestMapping(value = "permission/grant", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> grantPermission(@RequestBody GrantPermissionParam param) {
		permissionService.grantPermission(LoginContext.getIntFormatUserId(), param);
		return new WrapperResponse<>();
	}
	
	//
	@ApiOperation(value = "分配菜单关联接口")
	@RequestMapping(value = "menu/api_settings", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> ss(@RequestBody SetBindingParam param) {
		permissionService.setMenuRelateApis(param.getTargetId(), param.getSoureIds());
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "perm/batch_create", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> batchCreatePermResources(HttpServletRequest request,@RequestBody BatchCreateResourceParam param){
		String[] menuNames = StringUtils.splitByWholeSeparator(param.getMenuName(), ">");
		if(param.getApis() == null)throw new JeesuiteBaseException(400,"请填写关联接口");
		for (int i = 0; i < param.getApis().size(); i++) {
			ApiDefine apiDefine = param.getApis().get(i);
			if(StringUtils.isAnyBlank(apiDefine.getMethod(),apiDefine.getName(),apiDefine.getUri())){
				param.getApis().remove(i);
				i--;
			}
		}
		if(param.getApis().isEmpty())throw new JeesuiteBaseException(400,"请完整填写关联接口");
		
		permissionService.batchCreatePermResources(param.getPlatformType(), menuNames, param.getMenuUri(), param.getApis());
		
		boolean jsonSubmit = Boolean.parseBoolean(request.getParameter("jsonSubmit"));
		return new WrapperResponse<>(jsonSubmit == false ? JsonUtils.toPrettyJson(param) : null);
	}
	
	
}
