package com.oneplatform.permission.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.springweb.model.ResourceScopeQueryParam;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.constants.GrantSourceType;
import com.oneplatform.permission.constants.GrantTargetType;
import com.oneplatform.permission.dao.StandardBaseEntity;
import com.oneplatform.permission.dao.entity.ApiResourceEntity;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.entity.UserGroupEntity;
import com.oneplatform.permission.dao.mapper.ApiResourceEntityMapper;
import com.oneplatform.permission.dao.mapper.FunctionResourceEntityMapper;
import com.oneplatform.permission.dao.mapper.UserGroupEntityMapper;

/**
 * <br>
 * Class Name : UserPermissionService
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年1月2日
 */
@Service
public class UserPermissionService {

    @Autowired
    private UserGroupEntityMapper userGroupMapper;
    @Autowired
    private ApiResourceEntityMapper apiResourceMapper;
    @Autowired
    private FunctionResourceEntityMapper functionResourceMapper;
    @Autowired
    private RelationInternalService relationInternalService;
    
 
    public List<UserGroupEntity> findUserAssignRoles(String tenantId,String userId) {
    	// TODO 查询用户部门
        String departmentId = null;
        return userGroupMapper.findGrantedUserGroups( tenantId, departmentId,userId);
    }

    public List<Integer> findGrantedUserGroupIds(String tenantId,String userId){
    	List<UserGroupEntity> roles = findUserAssignRoles(tenantId, userId);
    	List<Integer> roleIds = roles.stream().map(StandardBaseEntity::getId).collect(Collectors.toList());
    	return roleIds;
    }

    public List<ApiResourceEntity> findUserGrantApis(ResourceScopeQueryParam param) {
    	
    	List<Integer> groupIds = findGrantedUserGroupIds(param.getTenantId(), param.getUserId());
        
        List<ApiResourceEntity> entities = apiResourceMapper.findByUserGrantRelations(param.getUserId());
        if (!groupIds.isEmpty()) {
            List<ApiResourceEntity> roleGrantApis = apiResourceMapper.findByRolesGrantRelations(groupIds);
            if (!roleGrantApis.isEmpty()) entities.addAll(roleGrantApis);
        }
        //TODO 菜单按钮关联接口
        
        return entities;
    }

    
    public List<FunctionResourceEntity> findUserGroupGrantResources(FunctionResourceType resourceType,String clientType,Integer userGroupId) {
        Map<String, String> idMapping = relationInternalService.findGrantRelationIdMappings(GrantSourceType.function, GrantTargetType.userGroup, userGroupId.toString());
        if (idMapping.isEmpty()) return new ArrayList<>(0);
        List<FunctionResourceEntity> entities = functionResourceMapper.selectByPrimaryKeys(idMapping.keySet().stream().map(Integer::parseInt).collect(Collectors.toList()));
        //查询父级（由于一些情况数据库只保存了子级菜单，要显示树形结构还需要）
        recursionQueryParentResources(clientType,entities);
        return entities;
    }

    public List<FunctionResourceEntity> findUserGrantResources(ResourceScopeQueryParam param) {

    	List<Integer> groupIds = findGrantedUserGroupIds(param.getTenantId(), param.getUserId());
        List<FunctionResourceEntity> entities = functionResourceMapper.findUserGrantedResources(param.getClientType(), param.getUserId());
        if (!groupIds.isEmpty()) {
            List<FunctionResourceEntity> roleGrantApis = functionResourceMapper.findRoleGrantedRelations(param.getClientType(), groupIds);
            if (!roleGrantApis.isEmpty()) entities.addAll(roleGrantApis);
        }
        //
        recursionQueryParentResources(param.getClientType(),entities);
        
        return entities;
    }

    /**
     * 递归查询父级菜单资源并合并结果
     *
     * @param entities
     */
    private void recursionQueryParentResources(String clientType,List<FunctionResourceEntity> entities) {

    	if(entities.isEmpty())return;
        Map<Integer, FunctionResourceEntity> parentMap = functionResourceMapper.findParentMenus(clientType)
                .stream()
                .collect(Collectors.toMap(FunctionResourceEntity::getId, o -> o));
        //如果数据表已经保存了父级菜单
        for (FunctionResourceEntity entity : entities) {
        	parentMap.remove(entity.getId());
        }
        if(parentMap.isEmpty())return;
        
        Map<Integer, FunctionResourceEntity> tmpParentMap = new HashMap<>();
        for (FunctionResourceEntity entity : entities) {
            recursionAllRelateParentMenus(parentMap, tmpParentMap, entity);
        }
        if (!tmpParentMap.isEmpty()) entities.addAll(tmpParentMap.values());
        entities.sort(Comparator.comparing(StandardBaseEntity::getId));
    }

    private void recursionAllRelateParentMenus(Map<Integer, FunctionResourceEntity> parentMap, Map<Integer, FunctionResourceEntity> result, FunctionResourceEntity entity) {
        if (entity.getParentId() == null || entity.getParentId() == 0) return;
        //当前已经是父级菜单
        if(parentMap.containsKey(entity.getId()))return;
        if (parentMap.containsKey(entity.getParentId())) {
            FunctionResourceEntity tmpParent = parentMap.get(entity.getParentId());
            if (result.containsKey(tmpParent.getId())) return;
            result.put(tmpParent.getId(), tmpParent);
            recursionAllRelateParentMenus(parentMap, result, tmpParent);
        }
    }


}
