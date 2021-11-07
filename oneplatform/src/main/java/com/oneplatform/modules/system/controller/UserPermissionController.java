package com.oneplatform.modules.system.controller;

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
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.NameValuePair;
import com.jeesuite.springweb.annotation.ApiMetadata;
import com.jeesuite.springweb.model.CurrentContextParam;
import com.oneplatform.modules.system.constants.SubRelationType;
import com.oneplatform.modules.system.dao.entity.UserGroupEntity;
import com.oneplatform.modules.system.dao.mapper.UserGroupEntityMapper;
import com.oneplatform.modules.system.dto.ApiResource;
import com.oneplatform.modules.system.dto.ResourceItem;
import com.oneplatform.modules.system.dto.UserGroup;
import com.oneplatform.modules.system.dto.param.AddUserRoleRelationParam;
import com.oneplatform.modules.system.service.RelationInternalService;
import com.oneplatform.modules.system.service.UserPermissionService;

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
    	CurrentContextParam current = CurrentContextParam.current();
        return userPermissionService.findUserAssignRoles(current.getTenantId(),current.getUserType(),current.getUserId());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户的接口权限")
    @GetMapping(value = "/user/apis")
    public List<ApiResource> getUserApiPermissions() {
    	CurrentContextParam current = CurrentContextParam.current();
        return userPermissionService.findUserGrantApis(current.getTenantId(), current.getUserId());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "查询用户按钮权限")
    @GetMapping(value = "/user/buttons")
    public List<NameValuePair> getUserButtonPermissions() {
    	CurrentContextParam current = CurrentContextParam.current();
    	List<NameValuePair> buttons = userPermissionService.findUserGrantButtons(current.getClientType(),current.getTenantId(),current.getUserId());
		return buttons;
    }
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取用户菜单树")
    @GetMapping(value = "/user/menus")
    public List<ResourceItem> getUserMenuPermissions(){
    	
    	CurrentContextParam current = CurrentContextParam.current();
    	List<ResourceItem> menus = userPermissionService.findUserGrantMenus(current.getClientType(),current.getTenantId(),current.getUserType(),current.getUserId());
    	
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
