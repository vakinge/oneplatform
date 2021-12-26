package com.oneplatform.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.CurrentRuntimeContext;
import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.NameValuePair;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.model.SelectOption;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.constants.GrantSourceType;
import com.oneplatform.permission.constants.GrantTargetType;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.mapper.FunctionResourceEntityMapper;
import com.oneplatform.permission.dto.UserGroup;
import com.oneplatform.permission.dto.param.GrantPermItem;
import com.oneplatform.permission.dto.param.GrantRelationParam;
import com.oneplatform.permission.dto.param.GrantUserGroupPermParam;
import com.oneplatform.permission.dto.param.UserGroupParam;
import com.oneplatform.permission.dto.param.UserGroupQueryParam;
import com.oneplatform.permission.service.UserGroupService;
import com.oneplatform.permission.service.UserPermissionService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;
	@Autowired 
    private UserPermissionService userPermissionService;
	@Autowired
    private FunctionResourceEntityMapper functionResourceMapper;
 

	
	@ApiOperation(value = "新增角色", notes = "### 新增角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("add")
	public IdParam<Integer> add(UserGroupParam param) {
		String tenantId = CurrentRuntimeContext.getTenantId(false);
		param.setTenantId(tenantId);
		return userGroupService.addUserGroup(param);
	}

	
	@ApiOperation(value = "删除角色", notes = "### 删除角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("delete")
	public void delete(IdParam<Integer> param) {
		userGroupService.deleteUserGroup(param.getId());
	}

	
	@ApiOperation(value = "更新角色", notes = "### 更新角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("update")
	public void update(UserGroupParam param) {
		userGroupService.updateUserGroup(param);
	}

	
	@ApiOperation(value = "启用|禁用角色", notes = "### 启用|禁用角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("toggle")
	public void toggleUserGroup(IdParam<Integer> param) {
		userGroupService.switchUserGroup(param.getId());
	}

	
	@ApiOperation(value = "根据id查询角色", notes = "### 根据id查询角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@GetMapping("{id}")
	public UserGroup get(@PathVariable("id") Integer id) {
		return userGroupService.getUserGroup(id);
	}

	
	@ApiOperation(value = "分页查询角色", notes = "### 分页查询角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("list")
	public Page<UserGroup> pageQry(PageQueryRequest<UserGroupQueryParam> queryParam) {
		String tenantId = CurrentRuntimeContext.getTenantId(false);
		if(queryParam.getExample() == null)queryParam.setExample(new UserGroupQueryParam());
		queryParam.getExample().setTenantId(tenantId);
		return userGroupService.pageQryUserGroup(new PageParams(queryParam.getPageNo(), queryParam.getPageSize()),
				queryParam.getExample());
	}

	
	@ApiOperation(value = "角色下拉列表", notes = "### 角色下拉列表 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@GetMapping("options")
	public List<SelectOption> options() {
		String tenantId = CurrentRuntimeContext.getTenantId(false);
		UserGroupQueryParam param = new UserGroupQueryParam();
		param.setTenantId(tenantId);
		return userGroupService.listByQueryParam(param).stream().map( o -> {
			return new SelectOption(o.getId().toString(), o.getName());
		}).collect(Collectors.toList());
	}


	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@ApiOperation(value = "分配用户组权限")
	@PostMapping(value = "/grantPermissions")
	@ResponseBody
	public void grantPermissions(@RequestBody GrantUserGroupPermParam param) {

		UserGroup group = userGroupService.getUserGroup(param.getUserGroupId());
		
		Map<String, List<String>> typeGroupMap = new HashMap<>(3);
		
		List<String> tmpList;
		for (GrantPermItem item : param.getPermItems()) {
			tmpList = typeGroupMap.get(item.getType());
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				typeGroupMap.put(item.getType(), tmpList);
			}
			if(!tmpList.contains(item.getId().toString())) {				
				tmpList.add(item.getId().toString());
			}
		}
		
		//查询按钮归属的菜单
		if(typeGroupMap.containsKey(FunctionResourceType.button.name())){
			List<Integer> buttonIds = typeGroupMap.get(FunctionResourceType.button.name()).stream().map(o -> {
				return Integer.parseInt(o);
		    }).collect(Collectors.toList());
			List<FunctionResourceEntity> buttons = functionResourceMapper.selectByPrimaryKeys(buttonIds);
			for (FunctionResourceEntity button : buttons) {
				tmpList = typeGroupMap.get(FunctionResourceType.button.name());
				if (tmpList == null) {
					tmpList = new ArrayList<>();
					typeGroupMap.put(FunctionResourceType.menu.name(), tmpList);
				}
				if(!tmpList.contains(button.getParentId().toString())){					
					tmpList.add(button.getParentId().toString());
				}
			}
		}

		final List<GrantRelationParam> paramList = new ArrayList<>();
		typeGroupMap.forEach((k, v) -> {
			GrantRelationParam relationParam = new GrantRelationParam();
			relationParam.setTargetType(GrantTargetType.userGroup);
			relationParam.setTargetId(param.getUserGroupId().toString());
			relationParam.setSourceType(GrantSourceType.valueOf(k));
			relationParam.setSourceIdList(v);
			paramList.add(relationParam);
		});
		userGroupService.updateGrantedPermissions(paramList);

	}
	
	
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @ApiOperation(value = "查询用户组按钮权限",notes = "### 查询用户组按钮权限 \n -xxx")
    @GetMapping(value = "buttons")
    @ResponseBody
    public List<NameValuePair> listUserGroupButtons(@RequestParam("groupId")Integer userGroupId) {
		String clientType = CurrentRuntimeContext.getClientType();
		List<FunctionResourceEntity> resources = userPermissionService.findUserGroupGrantResources(FunctionResourceType.button, clientType, userGroupId);
		return resources.stream().map(o -> {
			return new NameValuePair(o.getName(), o.getCode());
		}).collect(Collectors.toList());
    }
}
