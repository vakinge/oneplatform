package com.oneplatform.permission.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.NameValuePair;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.springweb.model.ResourceScopeQueryParam;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.constants.SubRelationType;
import com.oneplatform.permission.dao.entity.ApiResourceEntity;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.entity.UserGroupEntity;
import com.oneplatform.permission.dao.mapper.UserGroupEntityMapper;
import com.oneplatform.permission.dto.ApiResource;
import com.oneplatform.permission.dto.ResourceItem;
import com.oneplatform.permission.dto.UserGroup;
import com.oneplatform.permission.dto.param.AddUserRoleRelationParam;
import com.oneplatform.permission.service.RelationInternalService;
import com.oneplatform.permission.service.UserPermissionService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/")
public class UserPermissionController {

    @Autowired
    private RelationInternalService relationInternalService;
    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired 
    private UserGroupEntityMapper userGroupMapper;
    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired)
    @ApiOperation(value = "给用户分配角色")
    @PostMapping("user/assign_roles")
    public void updateUserRoleRelation(AddUserRoleRelationParam param) {
    	if(param.getRoleIds() == null || param.getRoleIds().isEmpty()) {
    		throw new JeesuiteBaseException("至少选择一个角色");
    	}
    	UserGroupEntity userGroup = userGroupMapper.selectByPrimaryKey(param.getRoleIds().get(0));
    	if(userGroup == null)throw new JeesuiteBaseException("角色不存在");
    	//
    	List<String> strRoleIds = param.getRoleIds().stream().map( o -> {return o.toString();}).collect(Collectors.toList());
    	
        relationInternalService.updateChildSubRelations(SubRelationType.userToGroup, param.getUserId(), param.getUserName(), strRoleIds);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的角色")
    @GetMapping(value = "/user/roles")
    public List<UserGroup> getUserRoles() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        List<UserGroupEntity> entities = userPermissionService.findUserAssignRoles(current.getTenantId(),current.getUserId());
        return BeanUtils.copy(entities, UserGroup.class);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的接口权限")
    @GetMapping(value = "/user/apis")
    public List<ApiResource> getUserApiPermissions() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
        List<ApiResourceEntity> entities = userPermissionService.findUserGrantApis(current);
        return BeanUtils.copy(entities, ApiResource.class);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户按钮权限")
    @GetMapping(value = "/user/buttons")
    public List<NameValuePair> getUserButtonPermissions() {
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
    	current.setResourceType(FunctionResourceType.button.name());
    	List<FunctionResourceEntity> entities = userPermissionService.findUserGrantResources(current);
    	return entities.stream().map(o -> {
			return new NameValuePair(o.getName(), o.getCode());
		}).collect(Collectors.toList());
    }
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取用户菜单树")
    @GetMapping(value = "/user/menus")
    public List<ResourceItem> getUserMenuPermissions(){
    	
    	ResourceScopeQueryParam current = ResourceScopeQueryParam.current();
    	current.setResourceType(FunctionResourceType.menu.name());
    	List<FunctionResourceEntity> entities = userPermissionService.findUserGrantResources(current);
    	List<ResourceItem> menus = BeanUtils.copy(entities, ResourceItem.class);
    	
    	return ResourceItem.buildTree(menus);
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
   
}
