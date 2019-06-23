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
package com.oneplatform.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesuite.common.util.AssertUtil;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor.PageDataLoader;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.jeesuite.smartapi.core.constants.HttpMethodType;
import com.oneplatform.base.model.IdNamePair;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.system.constants.GrantRelationType;
import com.oneplatform.system.constants.PermissionResourceType;
import com.oneplatform.system.constants.SubRelationType;
import com.oneplatform.system.dao.entity.GrantRelationEntity;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.PermissionGroupEntity;
import com.oneplatform.system.dao.entity.PermissionResourceEntity;
import com.oneplatform.system.dao.entity.SubordinateRelationEntity;
import com.oneplatform.system.dao.entity.UserGroupEntity;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
import com.oneplatform.system.dao.mapper.PermissionGroupEntityMapper;
import com.oneplatform.system.dao.mapper.PermissionResourceEntityMapper;
import com.oneplatform.system.dao.mapper.RelationCommonMapper;
import com.oneplatform.system.dao.mapper.UserGroupEntityMapper;
import com.oneplatform.system.dto.param.AddPermGroupParam;
import com.oneplatform.system.dto.param.AddResourceParam;
import com.oneplatform.system.dto.param.AddUserGroupParam;
import com.oneplatform.system.dto.param.BatchCreateResourceParam.ApiDefine;
import com.oneplatform.system.dto.param.GrantPermissionParam;
import com.oneplatform.system.dto.param.QueryPermissionCheckedParam;
import com.oneplatform.system.dto.param.QueryResourceParam;
import com.oneplatform.system.dto.param.QueryUserGroupParam;
import com.oneplatform.system.dto.param.UpdatePermGroupParam;
import com.oneplatform.system.dto.param.UpdateResourceParam;
import com.oneplatform.system.dto.param.UpdateUserGroupParam;
import com.oneplatform.system.service.internal.PermissionInternalService;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月16日
 */
@Service
public class PermissionService {

	private final static Logger logger = LoggerFactory.getLogger("com.ayg.passport.service");

	private @Autowired PermissionGroupEntityMapper permissionGroupMapper;
	private @Autowired PermissionResourceEntityMapper permissionResourceMapper;
	private @Autowired UserGroupEntityMapper userGroupMapper;
	private @Autowired RelationCommonMapper relationMapper;
	private @Autowired ModuleEntityMapper moduleMapper;
	private @Autowired PermissionInternalService permissionInternalService;

	/**
	 * 新增用户组
	 * @param currentUser
	 * @param param
	 */
	@Transactional
	public void addUserGroup(Integer currentUserId, AddUserGroupParam param) {
		//TODO 判重(平台、公司、部门维度)
		UserGroupEntity entity = BeanUtils.copy(param, UserGroupEntity.class);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(currentUserId);
		entity.setIsDefault(false);
		userGroupMapper.insertSelective(entity);
		//add 授权关系
		List<GrantRelationEntity> relations = new ArrayList<>();
		//权限组
		for (Integer groupId : param.getPermGroupIds()) {
			relations.add(new GrantRelationEntity(groupId,entity.getId(), GrantRelationType.PermGroupToUserGroup,param.getPlatformType()));
		}
		//额外菜单和接口权限
		if(param.getExtrMenuIds() != null){
			for (Integer id : param.getExtrMenuIds()) {
				relations.add(new GrantRelationEntity(id,entity.getId(),  GrantRelationType.PermToUserGroup,param.getPlatformType()));
			}
			for (Integer id : param.getExtrApiIds()) {
				relations.add(new GrantRelationEntity(id,entity.getId(),  GrantRelationType.PermToUserGroup,param.getPlatformType()));
			}
		}

		relationMapper.insertGrantRelations(relations);
		
	}
	
	@Transactional
	public void  updateUserGroup(Integer currentUserId, UpdateUserGroupParam param) {
		//TODO 判重(平台、公司、部门维度)
		UserGroupEntity entity = userGroupMapper.selectByPrimaryKey(param.getId());
		AssertUtil.notNull(entity);
		BeanUtils.copy(param, entity);
		entity.setUpdatedBy(currentUserId);
		entity.setUpdatedAt(new Date());
		userGroupMapper.updateByPrimaryKeySelective(entity);
		//权限组
		permissionInternalService.updateGrantRelations(param.getId(), param.getPermGroupIds(), param.getPlatformType(), GrantRelationType.PermGroupToUserGroup);
		//额外权限
		List<Integer> extSourceIds = new ArrayList<>();
		if(param.getExtrMenuIds() != null)extSourceIds.addAll(param.getExtrMenuIds());
		if(param.getExtrApiIds() != null)extSourceIds.addAll(param.getExtrApiIds());
		permissionInternalService.updateGrantRelations(param.getId(), param.getPermGroupIds(), param.getPlatformType(), GrantRelationType.PermToUserGroup);

	}

	@Transactional
	public void deleteUserGroup(Integer currentUserId, Integer id) {
		//删除用户组关联
		relationMapper.deleteSubordinateRelation(new SubordinateRelationEntity(id, null, SubRelationType.UserToUserGroup));
		//删除授权关系
		relationMapper.deleteGrantRelation(new GrantRelationEntity(null, id, GrantRelationType.PermGroupToUserGroup));
		relationMapper.deleteGrantRelation(new GrantRelationEntity(null, id, GrantRelationType.PermToUserGroup));
		//
		userGroupMapper.deleteByPrimaryKey(id);
	}
	
	public List<UserGroupEntity> findUserGroups(QueryUserGroupParam param){
		return userGroupMapper.findListByParam(param);
	}
	
	public Page<UserGroupEntity> pagequeryUserGroups(PageParams pageParam,QueryUserGroupParam param){
		Page<UserGroupEntity> page = PageExecutor.pagination(pageParam, new PageDataLoader<UserGroupEntity>() {
			@Override
			public List<UserGroupEntity> load() {
				return userGroupMapper.findListByParam(param);
			}
		});
		return page;
	}
	
	public UserGroupEntity findUserGroup(Integer id){
		UserGroupEntity entity = userGroupMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(entity);
		
		List<GrantRelationEntity> relations = relationMapper.findGrantRelationsByTargetId(entity.getPlatformType(), GrantRelationType.PermGroupToUserGroup.getCode(), entity.getId());
		entity.setBindPermGroupIds(relations.stream().map(e -> e.getSourceId()).collect(Collectors.toList()));
		return entity;
	}
	
	public PermissionGroupEntity findPermissionGroup(Integer id){
		PermissionGroupEntity entity = permissionGroupMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(entity);
		
		PermissionResourceEntity menuEntity = permissionResourceMapper.selectByPrimaryKey(entity.getMenuId());
		entity.setMenuName(menuEntity.getName());
		entity.setMenuUri(menuEntity.getUri());
		List<IdNamePair> apis = permissionResourceMapper.findByPermGroupId(PermissionResourceType.api.name(), entity.getId())
                .stream().map(e -> {
                	return new IdNamePair(e.getId(), e.getName());
                }).collect(Collectors.toList());
        entity.setApis(apis);

		return entity;
	}
	
	@Transactional
	public void addResource(Integer currentUserId, AddResourceParam param) {
		//TODO 判重
        PermissionResourceEntity entity = BeanUtils.copy(param, PermissionResourceEntity.class);
        ModuleEntity module = moduleMapper.selectByPrimaryKey(entity.getId());
        entity.buildPermssionCode(module);
        entity.setCreatedAt(new Date());
		entity.setCreatedBy(currentUserId);
        permissionResourceMapper.insertSelective(entity);
        //
        if(param.getApiIds() != null && !param.getApiIds().isEmpty()){
        	permissionInternalService.updateSubordinateRelationsByParent(entity.getId(), param.getApiIds(), param.getPlatformType(), SubRelationType.ApiToMenu);
        }
	}

	@Transactional
	public void updateResource(Integer currentUserId, UpdateResourceParam param) {
		//TODO 判重
		PermissionResourceEntity entity = permissionResourceMapper.selectByPrimaryKey(param.getId());
		AssertUtil.notNull(entity);
		BeanUtils.copy(param, entity);
		
		ModuleEntity module = moduleMapper.selectByPrimaryKey(entity.getId());
        entity.buildPermssionCode(module);
        
		entity.setUpdatedBy(currentUserId);
		entity.setUpdatedAt(new Date());
		
		permissionResourceMapper.updateByPrimaryKey(entity);
		//
        if(param.getApiIds() != null && !param.getApiIds().isEmpty()){
        	permissionInternalService.updateSubordinateRelationsByParent(entity.getId(), param.getApiIds(), entity.getPlatformType(), SubRelationType.ApiToMenu);
        }
	}

	/**
	 * 新增权限组
	 * @param currentUser
	 * @param param
	 */
	@Transactional
	public void addPermGroup(Integer currentUserId, AddPermGroupParam param) {
		//TODO 判重
		PermissionGroupEntity entity = new PermissionGroupEntity();
		entity.setName(param.getName());
		entity.setMenuId(param.getMenuId());
		entity.setPlatformType(param.getPlatformType());
		entity.setMemo(param.getMemo());
		entity.setIsDefault(false);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(currentUserId);
		permissionGroupMapper.insertSelective(entity);
		//接口关联关系
		List<SubordinateRelationEntity> relations = new ArrayList<>(param.getApiIds().size());
		for (Integer apiId : param.getApiIds()) {
			relations.add(new SubordinateRelationEntity(entity.getId(), apiId, SubRelationType.PermToPermGroup,param.getPlatformType()));
		}
		relationMapper.insertSubordinateRelations(relations);
		
	}
	
	@Transactional
	public void updatePermGroup(Integer currentUserId, UpdatePermGroupParam param){
		PermissionGroupEntity entity = permissionGroupMapper.selectByPrimaryKey(param.getId());
		AssertUtil.notNull(entity);
		BeanUtils.copy(param, entity);
		entity.setUpdatedBy(currentUserId);
		entity.setUpdatedAt(new Date());
		permissionGroupMapper.updateByPrimaryKeySelective(entity);
		//
		permissionInternalService.updateSubordinateRelationsByParent(param.getId(), param.getApiIds(), param.getPlatformType(), SubRelationType.PermToPermGroup);

	}

	public void deletePermGroup(Integer currentUserId, Integer id) {

	}
	
	/**
	 * 设置权限对象(用户组/权限组/权限资源)启用/停用状态
	 * @param id
	 * @param type
	 */
	public void setPermObjectStatus(int id,String type){
		if("permgroup".equals(type)){
			PermissionGroupEntity entity = permissionGroupMapper.selectByPrimaryKey(id);
			AssertUtil.notNull(entity);
			entity.setEnabled(!entity.getEnabled());
			permissionGroupMapper.updateByPrimaryKeySelective(entity);

		}else if("usergroup".equals(type)){
			UserGroupEntity entity = userGroupMapper.selectByPrimaryKey(id);
			AssertUtil.notNull(entity);
			entity.setEnabled(!entity.getEnabled());
			userGroupMapper.updateByPrimaryKeySelective(entity);

		}else if("resource".equals(type)){
			PermissionResourceEntity entity = permissionResourceMapper.selectByPrimaryKey(id);
			AssertUtil.notNull(entity);
			entity.setEnabled(!entity.getEnabled());
			permissionResourceMapper.updateByPrimaryKeySelective(entity);
		}
	}
	
	public List<PermissionGroupEntity> findPermissionGroupList(String platformType){
		 return permissionGroupMapper.findListByPlatform(platformType);
	}
	
	public Page<PermissionGroupEntity> pageQueryPermissionGroupList(PageParams pageParam,final String platformType){
		Page<PermissionGroupEntity> page = PageExecutor.pagination(pageParam, new PageDataLoader<PermissionGroupEntity>() {
			@Override
			public List<PermissionGroupEntity> load() {
				if(StringUtils.isBlank(platformType))return permissionGroupMapper.selectAll();
				return permissionGroupMapper.findListByPlatform(platformType);
			}
		});
		//查询关联权限资源
		for (PermissionGroupEntity entity : page.getData()) {
			List<IdNamePair> apis = permissionResourceMapper.findByPermGroupId(PermissionResourceType.api.name(), entity.getId())
			                        .stream().map(e -> {
			                        	return new IdNamePair(e.getId(), e.getName());
			                        }).collect(Collectors.toList());
			entity.setApis(apis);
		}
		return page;
	}
	
	public Page<PermissionResourceEntity> pageQueryResourceList(PageParams pageParam,QueryResourceParam queryParam){
		return PageExecutor.pagination(pageParam, new PageDataLoader<PermissionResourceEntity>() {
			@Override
			public List<PermissionResourceEntity> load() {
				return permissionResourceMapper.findListByParam(queryParam);
			}
		});
	}
	
	
	/**
	 * 查询权限树并按条件标记选中状态
	 * @param param
	 * @return
	 */
	public List<TreeModel> buildPermissionTreeWithCheckStatus(QueryPermissionCheckedParam param){

		List<TreeModel> result = new ArrayList<>();
		List<PermissionResourceEntity> resources = permissionResourceMapper.findAllEnabled();
		//所有叶节点菜单Id
		List<Integer> leafMenuIds = new ArrayList<>();
		Map<Integer, PermissionResourceEntity> apiResourceMap = new HashMap<>();
		for (PermissionResourceEntity entity : resources) {
			if(PermissionResourceType.menu.name().equals(entity.getType())){
				if(StringUtils.isNotBlank(entity.getUri())){
					leafMenuIds.add(entity.getId());
				}
				result.add(new TreeModel(entity.getId(), entity.getName(), entity.getUri(), entity.getIcon(), entity.getParentId(), false));
			}else{
				apiResourceMap.put(entity.getId(), entity);
			}
		}
		
		List<SubordinateRelationEntity> relations = relationMapper.findSubRelationsByParentIds(param.getPlatformType(),SubRelationType.ApiToMenu.getCode(), leafMenuIds);
		PermissionResourceEntity apiResource;
		for (SubordinateRelationEntity relation : relations) {
			apiResource = apiResourceMap.get(relation.getChildId());
			if(apiResource == null)continue;
			result.add(new TreeModel(apiResource.getId(), apiResource.getName(), apiResource.getUri(), apiResource.getIcon(), relation.getParentId(), true));
		}

		//根据用户id除了部门默认用户组外的权限设置选中状态
		if(param.getUserId() != null){
			List<GrantRelationEntity> grantRelations = relationMapper.findGrantRelationsByTargetId(param.getPlatformType(), GrantRelationType.PermToUser.getCode(), param.getUserId());
			if(!grantRelations.isEmpty()){
				List<Integer> resourceIds = grantRelations.stream().map(e->e.getSourceId()).collect(Collectors.toList());
				for (TreeModel model : result) {
					if(model.isChecked())continue;
					model.setChecked(resourceIds.contains(model.getId()));
				}
			}
		}
	
		//生成层级关系的树
		return TreeModel.build(result).getChildren();
	}
	
	/**
	 * 给用户分配权限
	 * @param currentUser
	 * @param param
	 */
	@Transactional
	public void grantPermission(Integer currentUserId,GrantPermissionParam param){
		//用户关联关系
		if(param.getUserGroupIds() != null && !param.getUserGroupIds().isEmpty()){
			permissionInternalService.updateSubordinateRelationsByChild(param.getUserId(), param.getUserGroupIds(), param.getPlatformType(), SubRelationType.UserToUserGroup);
		}
		//权限组关系
        if(param.getPermGroupIds() != null && !param.getPermGroupIds().isEmpty()){
        	permissionInternalService.updateGrantRelations(param.getUserId(), param.getPermGroupIds(), param.getPlatformType(), GrantRelationType.PermGroupToUser);
        }
        //额外的接口权限
        if(param.getApiIds() != null && !param.getApiIds().isEmpty()){
        	permissionInternalService.updateGrantRelations(param.getUserId(), param.getApiIds(), param.getPlatformType(), GrantRelationType.PermToUser);
        }
	}
	
	/**
	 * 设置菜单与接口绑定关系
	 * @param menuId
	 * @param apiIds
	 */
	@Transactional
	public void setMenuRelateApis(Integer menuId,List<Integer> apiIds){
		PermissionResourceEntity menu = permissionResourceMapper.selectByPrimaryKey(menuId);
		permissionInternalService.updateSubordinateRelationsByParent(menuId, apiIds, menu.getPlatformType(), SubRelationType.ApiToMenu);
	}
	
	public List<Integer> findMenuRelateApiIds(Integer menuId){
		PermissionResourceEntity menu = permissionResourceMapper.selectByPrimaryKey(menuId);
		//用户组外直接关联的权限
		List<SubordinateRelationEntity> relations = relationMapper.findSubRelationsByParentId(menu.getPlatformType(), SubRelationType.ApiToMenu.getCode(), menu.getId());
		if(!relations.isEmpty()){
			return relations.stream().map(e->e.getChildId()).collect(Collectors.toList());
		}
		return new ArrayList<>(0);
	}

	public List<PermissionResourceEntity> findMenuRelateApiResource(Integer menuId){
		List<Integer> apiIds = findMenuRelateApiIds(menuId);
		if(apiIds.isEmpty())return new ArrayList<>(0); 
		return permissionResourceMapper.findByIds(PermissionResourceType.api.name(), apiIds);
	}
	
	/**
	 * 获取用户的权限数据
	 * @param platformType
	 * @param userId
	 * @param resourceType 为空则查全部
	 * @return
	 */
	public List<PermissionResourceEntity> findUserPermissionResource(String platformType,Integer userId,PermissionResourceType resourceType){
		//超级管理员
		if(userId == 1){
			QueryResourceParam param = new QueryResourceParam();
			param.setPlatformType(platformType);
			param.setType(resourceType.name());
			param.setEnabled(true);
			return permissionResourceMapper.findListByParam(param);
		}
		//用户关联的用户组
		List<Integer> userGroupIds = relationMapper.findSubRelationsByChildId(platformType,SubRelationType.UserToUserGroup.getCode(), userId)
		  .stream()
		  .map(e -> e.getParentId()).collect(Collectors.toList());
		//
		List<PermissionResourceEntity> permissionResources;
		if(PermissionResourceType.menu == resourceType){
			permissionResources = permissionResourceMapper.findRelateMenuByUserGroupIds(userGroupIds);
		}else{
			permissionResources = permissionResourceMapper.findRelateApiByUserGroupIds(userGroupIds);
		}
		//用户组外直接关联的权限
		List<GrantRelationEntity> relations = relationMapper.findGrantRelationsByTargetId(platformType, GrantRelationType.PermToUser.getCode(), userId);
		if(!relations.isEmpty()){
			List<Integer> resourceIds = relations.stream().map(e->e.getSourceId()).collect(Collectors.toList());
			permissionResources.addAll(permissionResourceMapper.findByIds(resourceType == null ? null : resourceType.name(),resourceIds));
		}
		//查询父级菜单用于生成树形结构
		if(!permissionResources.isEmpty() && resourceType == PermissionResourceType.menu){
			Map<Integer, PermissionResourceEntity> notLeafMenus = permissionResourceMapper.findNotLeafMenus()
			                        .stream().collect(Collectors.toMap(PermissionResourceEntity::getId, e->e));
			
			List<PermissionResourceEntity> parentResources = new ArrayList<>();
			for (PermissionResourceEntity entity : permissionResources) {
				if(entity.getParentId() == null)continue;
				PermissionResourceEntity parent = notLeafMenus.remove(entity.getParentId());
				if(parent != null){
					parentResources.add(parent);
					//继续查找上级菜单，如果存在
					while(parent.getParentId() != null && parent.getParentId() >= 0){
						parent =  notLeafMenus.get(parent.getParentId());
						if(parent != null)parentResources.add(parent);
					}
				}
			}
			
			if(!parentResources.isEmpty()){
				permissionResources.addAll(parentResources);
			}
			
		}
		return permissionResources;
	}
	
	/**
	 * 查询构建用户接口权限编码数据:平台→权限编码
	 * @param userId
	 * @return
	 */
	public Map<String, List<String>> buildUserPermissionCodes(Integer userId){
		Map<String, List<String>> permissionMaps = new HashMap<>(2);
		//查询用户关联的平台
		List<String> platforms = relationMapper.findUserRelatePlatform(userId);
		for (String platform : platforms) {
			List<String> permissions = findUserPermissionResource(platform, userId, PermissionResourceType.api)
			     .stream()
			     .map(e -> e.getPermissionCode()).collect(Collectors.toList());
			permissionMaps.put(platform, permissions);
		}
		
		return permissionMaps;
	}
	
	/**
	 * 查询构建全部的权限编码数据:权限类型→权限编码
	 * @return
	 */
	public Map<String, List<String>> buildGlobalPermissionCodes(){
		Map<String, List<String>> datas = new HashMap<>(3);
		
		List<PermissionResourceEntity> permResources;
		QueryResourceParam param = new QueryResourceParam();
		param.setType(PermissionResourceType.api.name());
        permResources = permissionResourceMapper.findListByParam(param);
		
		List<String> permCodes;
		for (PermissionResourceEntity perm : permResources) {
			permCodes = datas.get(perm.getGrantType());
			if(permCodes == null){
				permCodes = new ArrayList<>();
				datas.put(perm.getGrantType(), permCodes);
			}
			permCodes.add(perm.getPermissionCode());
		}
        
		return datas;
	}
	
	
	public List<PermissionResourceEntity> findPermissionResources(QueryResourceParam param){
		return permissionResourceMapper.findListByParam(param);
	}
	
	public List<PermissionGroupEntity> findPermissionGroups(String platformType){
		return permissionGroupMapper.findListByPlatform(platformType);
	}
	
	public PermissionResourceEntity findPermissionResource(Integer id){
		return permissionResourceMapper.selectByPrimaryKey(id);
	}
	
	@Transactional
	public void batchCreatePermResources(String platformType,String[] menuNames,String menuUri,List<ApiDefine> apis){
		PermissionResourceEntity menu = null;
		Integer parentId = null;
		for (int i = 0; i < menuNames.length; i++) {			
			menu = permissionResourceMapper.findByTypeAndName(platformType, PermissionResourceType.menu.name(), menuNames[i]);
			if(menu == null){
				menu = new PermissionResourceEntity();
				menu.setName(menuNames[i]);
				if(i == menuNames.length - 1){					
					menu.setUri(menuUri);
				}
				menu.setParentId(parentId);
				menu.setPlatformType(platformType);
				menu.setType(PermissionResourceType.menu.name());
				menu.setHttpMethod(HttpMethodType.GET.name());
				menu.setCreatedAt(new Date());
				permissionResourceMapper.insertSelective(menu);
			}
			parentId = menu.getId();
		}
		
		PermissionResourceEntity api;
		ModuleEntity module; 
		List<Integer> apiIds = new ArrayList<>(apis.size());
		for (ApiDefine apiDefine : apis) {
			module = moduleMapper.findByServiceId(apiDefine.getModuleName());
			AssertUtil.notNull(module);
			api = permissionResourceMapper.findApiResourceByUri(module.getId(), apiDefine.getUri());
			if(api == null){
				api = new PermissionResourceEntity();
				api.setName(apiDefine.getName());
				api.setPlatformType(platformType);
				api.setType(PermissionResourceType.api.name());
				api.setUri(apiDefine.getUri());
				api.setHttpMethod(apiDefine.getMethod());
				api.setModuleId(module.getId());
                api.setGrantType(apiDefine.getGrantType());
				api.setCreatedAt(new Date());
				permissionResourceMapper.insertSelective(api);
			}
			apiIds.add(api.getId());
		}
		
		//绑定关系
		permissionInternalService.updateSubordinateRelationsByParent(menu.getId(), apiIds, platformType, SubRelationType.ApiToMenu);

	}
	
}
