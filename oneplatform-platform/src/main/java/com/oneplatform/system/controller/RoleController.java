package com.oneplatform.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.model.SwitchParam;
import com.oneplatform.platform.auth.AuthPermHelper;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.entity.RoleEntity;
import com.oneplatform.system.dao.mapper.RoleEntityMapper;
import com.oneplatform.system.dto.param.AssignResourceParam;
import com.oneplatform.system.dto.param.RoleParam;
import com.oneplatform.system.service.ResourcesService;
import com.oneplatform.system.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/role")
@Api("Role Management API")
public class RoleController {

	private @Autowired RoleEntityMapper roleMapper;
	private @Autowired RoleService roleService;
	private @Autowired ResourcesService resourcesService;
	
	@ApiOperation(value = "查询所有角色列表")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<RoleEntity>> pageQueryRoles() {
		List<RoleEntity> roles = roleService.findAllRoles();
		return new WrapperResponse<>(roles);
	}
	
	@ApiOperation(value = "按id查询")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<RoleEntity> getById(@PathVariable("id") int id) {
		RoleEntity entity = roleMapper.selectByPrimaryKey(id);
		return new WrapperResponse<>(entity);
	}
	
	@ApiOperation(value = "新增角色")
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> addRole(@RequestBody RoleParam param) {
		roleService.addRole(LoginContext.getLoginUserId(), param);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "更新角色")
	@RequestMapping(value = "update", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> updateRole(@RequestBody RoleParam param) {
		AssertUtil.notInitData(param.getId());
		roleService.updateRole(LoginContext.getLoginUserId(), param);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> deleteRole(@PathVariable("id") int id) {
		AssertUtil.notInitData(id);
		roleService.deleteRole(LoginContext.getLoginUserId(), id);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "启用/停止角色")
	@RequestMapping(value = "switch", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> switchRole(@RequestBody SwitchParam param) {
		roleService.switchRole(LoginContext.getLoginUserId(), param.getId(), param.getValue());
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "分配资源")
	@RequestMapping(value = "assignment", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> assignmentRoles(@RequestBody AssignResourceParam param) {		
		roleService.assignmentResources(LoginContext.getLoginUserId(), param);
		//
		AuthPermHelper.refreshPermData();
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "所有分配资源列表")
	@RequestMapping(value = "resources/{roleId}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<ResourceEntity>> getAssignResources(@PathVariable("roleId") int roleId) {
		resourcesService.findResourceByRole(roleId);
		return new WrapperResponse<>();
	}
	
}
