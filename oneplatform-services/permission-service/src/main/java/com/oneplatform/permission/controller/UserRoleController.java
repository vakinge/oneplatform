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
import com.jeesuite.common.model.TreeModel;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.constants.GrantSourceType;
import com.oneplatform.permission.constants.GrantTargetType;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.mapper.FunctionResourceEntityMapper;
import com.oneplatform.permission.dto.ResourceTreeModel;
import com.oneplatform.permission.dto.UserRole;
import com.oneplatform.permission.dto.param.FunctionResourceQueryParam;
import com.oneplatform.permission.dto.param.GrantPermItem;
import com.oneplatform.permission.dto.param.GrantRelationParam;
import com.oneplatform.permission.dto.param.GrantUserRolePermParam;
import com.oneplatform.permission.dto.param.UserRoleParam;
import com.oneplatform.permission.dto.param.UserRoleQueryParam;
import com.oneplatform.permission.service.FunctionResourceService;
import com.oneplatform.permission.service.UserPermissionService;
import com.oneplatform.permission.service.UserRoleService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/role")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired 
    private UserPermissionService userPermissionService;
	@Autowired
	private FunctionResourceService functionResourceService;
	@Autowired
    private FunctionResourceEntityMapper functionResourceMapper;
 

	
	@ApiOperation(value = "新增角色", notes = "### 新增角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("add")
	public IdParam<Integer> add(@RequestBody UserRoleParam param) {
		String tenantId = CurrentRuntimeContext.getTenantId(false);
		param.setTenantId(tenantId);
		return userRoleService.addUserRole(param);
	}

	
	@ApiOperation(value = "删除角色", notes = "### 删除角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("delete")
	public void delete(@RequestBody IdParam<Integer> param) {
		userRoleService.deleteUserRole(param.getId());
	}

	
	@ApiOperation(value = "更新角色", notes = "### 更新角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("update")
	public void update(@RequestBody UserRoleParam param) {
		userRoleService.updateUserRole(param);
	}

	
	@ApiOperation(value = "启用|禁用角色", notes = "### 启用|禁用角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("toggle")
	public void toggleUserGroup(@RequestBody IdParam<Integer> param) {
		userRoleService.toggleUserRole(param.getId());
	}

	
	@ApiOperation(value = "根据id查询角色", notes = "### 根据id查询角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@GetMapping("{id}")
	public UserRole get(@PathVariable("id") Integer id) {
		return userRoleService.getUserRole(id);
	}

	
	@ApiOperation(value = "分页查询角色", notes = "### 分页查询角色 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@PostMapping("list")
	public Page<UserRole> pageQry(@RequestBody PageQueryRequest<UserRoleQueryParam> queryParam) {
		if(queryParam.getExample() == null)queryParam.setExample(new UserRoleQueryParam());
		PageParams pageParams = new PageParams(queryParam.getPageNo(), queryParam.getPageSize());
		return userRoleService.pageQryUserRole(pageParams,queryParam.getExample());
	}

	
	@ApiOperation(value = "角色下拉列表", notes = "### 角色下拉列表 \n -xxx")
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@GetMapping("options")
	public List<SelectOption> options() {
		UserRoleQueryParam param = new UserRoleQueryParam();
		return userRoleService.listByQueryParam(param).stream().map( o -> {
			return new SelectOption(o.getId().toString(), o.getName());
		}).collect(Collectors.toList());
	}


	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@ApiOperation(value = "分配角色权限")
	@PostMapping(value = "/grant")
	@ResponseBody
	public void grantPermissions(@RequestBody GrantUserRolePermParam param) {

		UserRole group = userRoleService.getUserRole(param.getUserGroupId());
		
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
			relationParam.setTargetType(GrantTargetType.role);
			relationParam.setTargetId(param.getUserGroupId().toString());
			relationParam.setSourceType(GrantSourceType.valueOf(k));
			relationParam.setSourceIdList(v);
			paramList.add(relationParam);
		});
		userRoleService.updateGrantedPermissions(paramList);

	}
	
	
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @ApiOperation(value = "查询角色按钮权限")
    @GetMapping(value = "buttons")
    @ResponseBody
    public List<NameValuePair> findRoleGrantedButtons(@RequestParam("roleId")Integer roleId) {
		String clientType = CurrentRuntimeContext.getClientType();
		List<FunctionResourceEntity> resources = userPermissionService.findRoleGrantedResources(FunctionResourceType.button, clientType, roleId);
		return resources.stream().map(o -> {
			return new NameValuePair(o.getName(), o.getCode());
		}).collect(Collectors.toList());
    }
	
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @ApiOperation(value = "查询角色关联功能权限树")
    @GetMapping(value = "resourceTree")
    @ResponseBody
	public List<TreeModel> getRoleFunctionResourceTree(@RequestParam("roleId") Integer roleId
            , @RequestParam(value = "includeButton", required = false, defaultValue = "true") boolean includeButton) {

		UserRole role = userRoleService.getUserRole(roleId);

        FunctionResourceQueryParam queryParam = new FunctionResourceQueryParam();
        if (!includeButton) queryParam.setType(FunctionResourceType.menu.name());
        queryParam.setEnabled(true);
        List<FunctionResourceEntity> entities = functionResourceService.findByQueryParam(queryParam);
        //当前系统所有功能资源
        List<ResourceTreeModel> allResorces = FunctionResourceEntity.toTreeModels(entities, null);

        //当前角色的功能资源
        List<FunctionResourceEntity> roleRresorces;
        if (includeButton) {
        	roleRresorces = userPermissionService.findRoleGrantedResources(null, null, roleId);
        }else {
        	roleRresorces = userPermissionService.findRoleGrantedResources(FunctionResourceType.menu, null, roleId);
        }
        if (!roleRresorces.isEmpty()) {
            List<String> roleRresorceIds = roleRresorces.stream().map(o -> o.getId().toString()).collect(Collectors.toList());
            //设置选中状态
            for (ResourceTreeModel resource : allResorces) {
                setRoleResourceChecked(resource, roleRresorceIds, false);
            }
        }

        return TreeModel.build(allResorces).getChildren();
    }
	
	private void setRoleResourceChecked(ResourceTreeModel resource, List<String> roleRresorceIds, boolean onlyLeaf) {
        if (roleRresorceIds.contains(resource.getId())) {
            if (!onlyLeaf || (onlyLeaf && resource.getChildren() == null)) {
                resource.setChecked(true);
            }
        }
        if (resource.getChildren() != null) {
            List<TreeModel> children = resource.getChildren();
            for (TreeModel child : children) {
                setRoleResourceChecked((ResourceTreeModel) child, roleRresorceIds, onlyLeaf);
            }
        }
    }
}
