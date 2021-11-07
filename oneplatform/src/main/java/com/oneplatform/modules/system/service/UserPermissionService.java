package com.oneplatform.modules.system.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.model.NameValuePair;
import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.core.base.StandardBaseEntity;
import com.oneplatform.modules.system.constants.GrantSourceType;
import com.oneplatform.modules.system.constants.GrantTargetType;
import com.oneplatform.modules.system.dao.entity.ApiResourceEntity;
import com.oneplatform.modules.system.dao.entity.FunctionResourceEntity;
import com.oneplatform.modules.system.dao.entity.UserGroupEntity;
import com.oneplatform.modules.system.dao.mapper.ApiResourceEntityMapper;
import com.oneplatform.modules.system.dao.mapper.FunctionResourceEntityMapper;
import com.oneplatform.modules.system.dao.mapper.UserGroupEntityMapper;
import com.oneplatform.modules.system.dto.ApiResource;
import com.oneplatform.modules.system.dto.ResourceItem;
import com.oneplatform.modules.system.dto.UserGroup;

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
    
    
    public List<ResourceItem> findUserGroupResources(Integer userGroupId) {
        Map<String, String> idMapping = relationInternalService.findGrantRelationIdMappings(GrantSourceType.function, GrantTargetType.userGroup, userGroupId.toString());
        if (idMapping.isEmpty()) return new ArrayList<>(0);
        List<FunctionResourceEntity> entities = functionResourceMapper.selectByPrimaryKeys(idMapping.keySet().stream().map(Integer::parseInt).collect(Collectors.toList()));
        //查询父级（由于一些情况数据库只保存了子级菜单，要显示树形结构还需要）
        recursionQueryParentResources(entities);
        return BeanUtils.copy(entities, ResourceItem.class);
    }

    public List<UserGroup> findUserAssignRoles(String tenantId, String userType, String userId) {
        List<UserGroupEntity> entities = findUserGroupEntities( tenantId,userType, userId);
        return BeanUtils.copy(entities, UserGroup.class);
    }


    public List<ApiResource> findUserGrantApis(String tenantId, String userId) {
    	
        List<Integer> roleIds = findUserGroupEntities( tenantId,null, userId).stream().map(StandardBaseEntity::getId).collect(Collectors.toList());
        List<ApiResourceEntity> entities = apiResourceMapper.findByUserGrantRelations( userId);
        if (!roleIds.isEmpty()) {
            List<ApiResourceEntity> roleGrantApis = apiResourceMapper.findByRolesGrantRelations( roleIds);
            if (!roleGrantApis.isEmpty()) entities.addAll(roleGrantApis);
        }
        //TODO 菜单按钮关联接口
        
        return BeanUtils.copy(entities, ApiResource.class);
    }

    public List<ResourceItem> findUserGrantMenus(String clientType,String tenantId,String userType, String userId) {

        List<Integer> roleIds = findUserGroupEntities( tenantId, userType, userId).stream().map(StandardBaseEntity::getId).collect(Collectors.toList());
        List<FunctionResourceEntity> entities = functionResourceMapper.findUserGrantedResources(clientType, userId);
        if (!roleIds.isEmpty()) {
            List<FunctionResourceEntity> roleGrantApis = functionResourceMapper.findRoleGrantedRelations(clientType, roleIds);
            if (!roleGrantApis.isEmpty()) entities.addAll(roleGrantApis);
        }
        //
        recursionQueryParentResources( entities);
        
        return BeanUtils.copy(entities, ResourceItem.class);
    }

    public List<NameValuePair> findUserGroupGrantButtons(Integer userGroupId) {
        return null;
    }

    public List<NameValuePair> findUserGrantButtons(String clientType,String tenantId, String userId) {  	
    	
        return null;
    }

    private List<UserGroupEntity> findUserGroupEntities(String tenantId,String userType, String userId) {
        // TODO 查询用户部门
        String departmentId = null;
        return userGroupMapper.findGrantedUserGroups( tenantId, departmentId, userType,userId);
    }

    /**
     * 递归查询父级菜单资源并合并结果
     *
     * @param entities
     */
    private void recursionQueryParentResources(List<FunctionResourceEntity> entities) {

        Map<Integer, FunctionResourceEntity> parentMap = functionResourceMapper.findParents()
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
