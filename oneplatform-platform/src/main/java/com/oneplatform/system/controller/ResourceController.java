package com.oneplatform.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.model.SwitchParam;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.system.constants.ResourceType;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dto.ModuleRoleResource;
import com.oneplatform.system.dto.param.ResourceParam;
import com.oneplatform.system.service.ResourcesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/resource")
@Api("Resource Resource API")
public class ResourceController {

	private @Autowired ResourcesService resourcesService;
	
	@ApiOperation(value = "查询菜单列表")
	@RequestMapping(value = "menus", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getAllMenus(@RequestParam(name="containLeaf",required=false,defaultValue="true") boolean containLeaf) {
		List<TreeModel> list = resourcesService.findAllResourceTreeByType(ResourceType.menu.name(),containLeaf);
		return new WrapperResponse<>(list);
	}
	
	@ApiOperation(value = "查询权限列表")
	@RequestMapping(value = "permissions", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getAllPermissions() {
		List<TreeModel> list = resourcesService.findAllPermissions();
		return new WrapperResponse<>(list);
	}
	
	@ApiOperation(value = "查询所有(可分配/已分配)的角色资源列表")
	@RequestMapping(value = "role_relations", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<ModuleRoleResource>> getAllResourcesWithRole(@RequestParam("type") String type,@RequestParam("roleId") int roleId) {
		List<ModuleRoleResource> list = resourcesService.findAllModuleRoleResources(ResourceType.valueOf(type),roleId);
		return new WrapperResponse<>(list);
	}
	
	@ApiOperation(value = "按id查询")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<ResourceEntity> getById(@PathVariable("id") int id) {
		ResourceEntity entity = resourcesService.findById(id);
		return new WrapperResponse<>(entity);
	}
	
	@ApiOperation(value = "新增资源")
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> addResource(@RequestBody ResourceParam param) {
		resourcesService.addResource(LoginContext.getLoginUserId(), param);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "更新资源")
	@RequestMapping(value = "update", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> updateResource(@RequestBody ResourceParam param) {
		AssertUtil.notInitData(param.getId());
		resourcesService.updateResource(LoginContext.getLoginUserId(), param);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "删除资源")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> deleteResource(@PathVariable("id") int id) {
		AssertUtil.notInitData(id);
		resourcesService.deleteResource(LoginContext.getLoginUserId(), id);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "启用/停止资源")
	@RequestMapping(value = "switch", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> switchResource(@RequestBody SwitchParam param) {
		resourcesService.switchResource(LoginContext.getLoginUserId(), param.getId(), param.getValue());
		return new WrapperResponse<>();
	}
    
}
