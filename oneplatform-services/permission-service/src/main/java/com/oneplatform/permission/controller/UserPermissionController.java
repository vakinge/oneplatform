package com.oneplatform.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.CurrentRuntimeContext;
import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.AuthUser;
import com.jeesuite.common.model.DataPermItem;
import com.jeesuite.common.model.TreeModel;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.JsonUtils;
import com.jeesuite.springweb.model.ResourceScopeQueryParam;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.constants.RoleType;
import com.oneplatform.permission.constants.SubRelationType;
import com.oneplatform.permission.dao.entity.ApiResourceEntity;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.entity.UserRoleEntity;
import com.oneplatform.permission.dao.mapper.UserRoleEntityMapper;
import com.oneplatform.permission.dto.ApiResource;
import com.oneplatform.permission.dto.ResourceTreeModel;
import com.oneplatform.permission.dto.UserRole;
import com.oneplatform.permission.dto.param.AddUserRoleRelationParam;
import com.oneplatform.permission.dto.param.FunctionResourceQueryParam;
import com.oneplatform.permission.service.FunctionResourceService;
import com.oneplatform.permission.service.InternalRelationService;
import com.oneplatform.permission.service.UserPermissionService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/")
public class UserPermissionController {

    @Autowired
    private InternalRelationService relationService;
    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired
    private FunctionResourceService functionResourceService;
    @Autowired 
    private UserRoleEntityMapper userGroupMapper;
    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired)
    @ApiOperation(value = "给用户分配角色")
    @PostMapping("user/assign_roles")
    public void updateUserRoleRelation(@RequestBody AddUserRoleRelationParam param) {
    	if(param.getRoleIds() == null || param.getRoleIds().isEmpty()) {
    		throw new JeesuiteBaseException("至少选择一个角色");
    	}
    	UserRoleEntity userGroup = userGroupMapper.selectByPrimaryKey(param.getRoleIds().get(0));
    	if(userGroup == null)throw new JeesuiteBaseException("角色不存在");
    	//
    	List<String> strRoleIds = param.getRoleIds().stream().map( o -> {return o.toString();}).collect(Collectors.toList());
    	
        relationService.updateChildSubRelations(SubRelationType.userToRole, param.getUserId(), param.getUserName(), strRoleIds);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的角色")
    @GetMapping(value = "/user/roles")
    public List<UserRole> getUserRoles() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        List<UserRoleEntity> entities = userPermissionService.findUserAssignRoles(RoleType.function, current.getUserId(), current.getDepartmentId());
        return BeanUtils.copy(entities, UserRole.class);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的接口权限")
    @GetMapping(value = "/user/apis")
    public List<ApiResource> getUserApiPermissions() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        List<ApiResourceEntity> entities = userPermissionService.findUserGrantedApis(current);
        return BeanUtils.copy(entities, ApiResource.class);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户按钮权限")
    @GetMapping(value = "/user/buttons")
    public List<String> getUserButtonPermissions() {
    	
    	AuthUser currentUser = CurrentRuntimeContext.getAndValidateCurrentUser();
    	List<FunctionResourceEntity> entities;
    	if(currentUser.isAdmin()) {
    		//TODO 查找默认管理员 判断多租户
    		FunctionResourceQueryParam queryParam = new FunctionResourceQueryParam();
    		queryParam.setType(FunctionResourceType.button.name());
    		queryParam.setClientType(CurrentRuntimeContext.getClientType());
    		queryParam.setEnabled(true);
    		entities = functionResourceService.findByQueryParam(queryParam);
    	}else {
    		ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        	current.setType(FunctionResourceType.button.name());
        	entities = userPermissionService.findUserGrantedResources(current);
    	}
    	return entities.stream().map(o -> o.getCode()).collect(Collectors.toList());
    }
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取用户菜单树")
    @GetMapping(value = "/user/menus")
    public List<TreeModel> getUserMenuPermissions(){
    	
    	String clientType = CurrentRuntimeContext.getClientType();
    	AuthUser currentUser = CurrentRuntimeContext.getAndValidateCurrentUser();
    	List<FunctionResourceEntity> entities;
    	if(currentUser.isAdmin()) {
    		//TODO 查找默认管理员 判断多租户
    		FunctionResourceQueryParam queryParam = new FunctionResourceQueryParam();
    		queryParam.setExcludeButton(true);
			queryParam.setClientType(clientType);
    		queryParam.setEnabled(true);
    		entities = functionResourceService.findByQueryParam(queryParam);
    	}else {
    		ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        	current.setType(FunctionResourceType.menu.name());
        	entities = userPermissionService.findUserGrantedResources(current);
    	}
    	
    	List<ResourceTreeModel> menus = new ArrayList<>(entities.size());
    	ResourceTreeModel model;
        for (FunctionResourceEntity entity : entities) {
        	model = BeanUtils.copy(entity, ResourceTreeModel.class);
        	menus.add(model);
		}
    	
    	return TreeModel.build(menus).getChildren();
    }

    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取用户菜单树")
    @GetMapping(value = "/user/permissions")
    public Map<String, Object> getUserPermissions(){
    	Map<String, Object> result = new HashMap<>(2);
    	result.put("menus", getUserMenuPermissions());
    	result.put("buttons", getUserButtonPermissions());
    	return result;
    }
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的数据角色")
    @GetMapping(value = "/user/data_roles")
    public List<UserRole> getUserDataRoles() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        List<UserRoleEntity> entities = userPermissionService.findUserAssignRoles(RoleType.data, current.getUserId(), current.getDepartmentId());
        return BeanUtils.copy(entities, UserRole.class);
    }
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的数据权限")
    @GetMapping(value = "/user/data_permissions")
    public Map<String, DataPermItem> getUserDataPerms() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
    	Map<String, DataPermItem> result = new HashMap<>();
		
        List<UserRoleEntity> entities = userPermissionService.findUserAssignRoles(RoleType.data, current.getUserId(), current.getDepartmentId());
        
        List<DataPermItem> items;
        for (UserRoleEntity entity : entities) {
        	items = JsonUtils.toList(entity.getPermissions(), DataPermItem.class);
        	for (DataPermItem item : items) {
				if(!result.containsKey(item.getKey())) {
					result.put(item.getKey(), item);
				}else if(!item.getValues().isEmpty()){
					for (String val : item.getValues()) {
						if(!result.get(item.getKey()).getValues().contains(val)) {
							result.get(item.getKey()).getValues().add(val);
						}
					}
				}
			}
		}
        
        return result;
    }
   
}
