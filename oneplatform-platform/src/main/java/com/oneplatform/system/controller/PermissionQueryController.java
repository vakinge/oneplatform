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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.annotation.ApiPermOptions;
import com.oneplatform.base.constants.PermissionType;
import com.oneplatform.base.model.PageResult;
import com.oneplatform.base.model.SelectOptGroup;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.system.constants.PermissionResourceType;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.PermissionGroupEntity;
import com.oneplatform.system.dao.entity.PermissionResourceEntity;
import com.oneplatform.system.dao.entity.UserGroupEntity;
import com.oneplatform.system.dto.param.QueryPermissionCheckedParam;
import com.oneplatform.system.dto.param.QueryResourceParam;
import com.oneplatform.system.dto.param.QueryUserGroupParam;
import com.oneplatform.system.service.ModuleService;
import com.oneplatform.system.service.PermissionService;

import io.swagger.annotations.ApiOperation;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月24日
 */
@Controller
@RequestMapping(value = "/perm")
@ApiPermOptions(perms = PermissionType.Logined)
public class PermissionQueryController {

	private @Autowired ModuleService moduleService;
	private @Autowired PermissionService permissionService;

	@ApiOperation(value = "查询用户组选项列表")
	@RequestMapping(value = "usergroup/options", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> findUserGroups(@RequestParam("platformType") String platformType) {
		QueryUserGroupParam param = new QueryUserGroupParam();
		param.setPlatformType(platformType);
		param.setEnabled(true);
		List<SelectOption> list = permissionService.findUserGroups(param)
				                  .stream()
                                  .map(e -> {
               	                      return new SelectOption(e.getId().toString(), e.getName());
                                   }).collect(Collectors.toList());
		return new WrapperResponse<>(list);
	}
	
	@ApiOperation(value = "查询权限组选项列表")
	@RequestMapping(value = "permgroup/options", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> findPermGroups(@RequestParam("platformType") String platformType) {
		List<SelectOption> list = permissionService.findPermissionGroupList(platformType)
				                  .stream()
				                  .filter(e -> e.getEnabled())
                                  .map(e -> {
               	                      return new SelectOption(e.getId().toString(), e.getName());
                                   }).collect(Collectors.toList());
		return new WrapperResponse<>(list);
	}
	
	@ApiOperation(value = "查询菜单选项列表")
	@RequestMapping(value = "menu/options", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOptGroup>> findMenuOptions(@RequestParam("platformType") String platformType) {

		List<PermissionResourceEntity> resources = findMenuResources(platformType);
		Map<Integer, SelectOptGroup> parentMap = new HashMap<>();
        for (PermissionResourceEntity resource : resources) {
			if(resource.getParentId() != null && resource.getParentId() > 0){
				parentMap.get(resource.getParentId()).getOptions().add(new SelectOption(resource.getId().toString(), resource.getName()));
			}else{
				SelectOptGroup group = new SelectOptGroup(resource.getName());
				parentMap.put(resource.getId(), group);
			}
		}
        return new WrapperResponse<>(new ArrayList<>(parentMap.values()));
	}
	
	@ApiOperation(value = "查询菜单下选项列表")
	@RequestMapping(value = "/menu/apis", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> findPermissionResourceByMenuId(@RequestParam("menuId") Integer menuId) {
		return new WrapperResponse<>(buildMenuApiOptions(menuId));
	}
	
	@ApiOperation(value = "查询资源列表树")
	@RequestMapping(value = "resource/tree", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getResourceTree(@RequestParam("type") String type
    		,@RequestParam(value="platformType",required=false) String platformType
    		,@RequestParam(value="moduleId",required=false) Integer moduleId) {
		
		if("module".equals(type)){
			List<TreeModel> treeModels = moduleService.findActiveModules().stream().map(e -> {
				return new TreeModel(e.getId(), e.getName(), e.getServiceId(), null, null, false);
			}).collect(Collectors.toList());
			return new WrapperResponse<>(TreeModel.build(treeModels).getChildren());
		}
		
		QueryResourceParam param = new QueryResourceParam();
		param.setType(type);
		param.setPlatformType(platformType);
		param.setEnabled(true);
		//只查询需要授权的接口
		if(PermissionResourceType.api.name().equals(type)){
			param.setGrantType(PermissionType.Authorized.name());	
			param.setModuleId(moduleId);
		}
		List<PermissionResourceEntity> resources = permissionService.findPermissionResources(param);
		
		//如果是查询全部模块的接口，模块作为虚拟父节点
		if(PermissionResourceType.api.name().equals(param.getType()) && moduleId == null){
			Map<Integer, PermissionResourceEntity> virtualParents = new HashMap<>();
			Map<Integer, ModuleEntity> modoules = moduleService.getAllModules();
			PermissionResourceEntity virtualParent;
			for (PermissionResourceEntity entity : resources) {
				virtualParent = virtualParents.get(entity.getModuleId());
				if(virtualParent == null){
					ModuleEntity serviceMetadata = modoules.get(entity.getModuleId());
					virtualParent = new PermissionResourceEntity();
					if(serviceMetadata != null){
						virtualParent.setId(0 - serviceMetadata.getId().intValue());
						virtualParent.setType(PermissionResourceType.api.name());
						virtualParent.setName(serviceMetadata.getName());
					}
					virtualParents.put(entity.getModuleId(), virtualParent);
				}
				entity.setParentId(virtualParent.getId());
			}
			
			resources.addAll(virtualParents.values());
		}
		
		return new WrapperResponse<>(PermissionResourceEntity.buildTree(resources).getChildren());
	}
	
	@ApiOperation(value = "查询权限列表树")
	@RequestMapping(value = "permission/tree", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<List<TreeModel>> getPermissionGroups(@RequestBody QueryPermissionCheckedParam param) {
		List<TreeModel> list = permissionService.buildPermissionTreeWithCheckStatus(param);
		return new WrapperResponse<>(list);
	}
		
	@ApiOperation(value = "分页API列表")
	@RequestMapping(value = "/resources/apiList", method = RequestMethod.POST)
    public @ResponseBody PageResult<PermissionResourceEntity> pageApis(@ModelAttribute  QueryResourceParam pageParam) {
		pageParam.setType(PermissionResourceType.api.name());
		Page<PermissionResourceEntity> page = permissionService.pageQueryResourceList(pageParam, pageParam);
		return new PageResult<>(pageParam.getPageNo(), pageParam.getPageSize(), page.getTotal(), page.getData());
	}
	
	@ApiOperation(value = "分页权限组列表")
	@RequestMapping(value = "/permgroup/list", method = RequestMethod.POST)
    public @ResponseBody PageResult<PermissionGroupEntity> pagePermGroups(@RequestParam("pageNo") int pageNo,
    		@RequestParam("pageSize") int pageSize,
    		@RequestParam(value="platformType",required=false) String platformType) {
		if(StringUtils.isBlank(platformType))platformType = "admin";
		Page<PermissionGroupEntity> page = permissionService.pageQueryPermissionGroupList(new PageParams(pageNo, pageSize), platformType);
		return new PageResult<>(pageNo, pageSize, page.getTotal(), page.getData());
	}
	
	@ApiOperation(value = "权限组详情")
	@RequestMapping(value = "/permgroup/details/{id}", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<PermissionGroupEntity> getPermissionGroup(@PathVariable("id") Integer id){
		PermissionGroupEntity group = permissionService.findPermissionGroup(id);
		return new WrapperResponse<>(group);
	}
	
	@ApiOperation(value = "用户组列表")
	@RequestMapping(value = "/usergroup/list", method = RequestMethod.POST)
	public @ResponseBody PageResult<UserGroupEntity> pageUserGroups(@RequestParam("pageNo") int pageNo,
    		@RequestParam("pageSize") int pageSize,
    		@RequestParam(value="platformType",required=false) String platformType){
		if(StringUtils.isBlank(platformType))platformType = "admin";
		QueryUserGroupParam param = new QueryUserGroupParam();
		param.setPlatformType(platformType);
		
		Page<UserGroupEntity> page = permissionService.pagequeryUserGroups(new PageParams(pageNo, pageSize),param);
		
		return new PageResult<>(pageNo, pageSize, page.getTotal(), page.getData());
	}
	
	@ApiOperation(value = "用户组详情")
	@RequestMapping(value = "/usergroup/details/{id}", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<UserGroupEntity> getUserGroup(@PathVariable("id") Integer id){
		UserGroupEntity userGroup = permissionService.findUserGroup(id);
		return new WrapperResponse<>(userGroup);
	}
	
	@ApiOperation(value = "权限资源详情")
	@RequestMapping(value = "/resource/details/{id}", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<PermissionResourceEntity> getPermResoure(@PathVariable("id") Integer id){
		PermissionResourceEntity entity = permissionService.findPermissionResource(id);
		return new WrapperResponse<>(entity);
	}
	

	private List<PermissionResourceEntity> findMenuResources(String platformType){
		QueryResourceParam param = new QueryResourceParam();
		param.setPlatformType(platformType);
		param.setType(PermissionResourceType.menu.name());
		param.setEnabled(true);
		return permissionService.findPermissionResources(param);
	}
	
	private List<SelectOption> buildMenuApiOptions(Integer menuId){
		return permissionService.findMenuRelateApiResource(menuId)
                .stream()
                .filter(e -> e.getEnabled())
                .map(e -> {
               	 return new SelectOption(e.getId().toString(), e.getName());
                }).collect(Collectors.toList());
	}
}
