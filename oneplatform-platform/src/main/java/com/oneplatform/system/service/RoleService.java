/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.platform.auth.AuthPermHelper;
import com.oneplatform.system.dao.entity.RoleEntity;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;
import com.oneplatform.system.dao.mapper.RoleEntityMapper;
import com.oneplatform.system.dto.param.AssignResourceParam;
import com.oneplatform.system.dto.param.RoleParam;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月10日
 */
@Service
public class RoleService {

	private @Autowired RoleEntityMapper roleMapper;
	private @Autowired ResourceEntityMapper resourceMapper;
	
	public List<RoleEntity> findAllRoles(){
		return roleMapper.selectAll();
	}
	
	public void addRole(int operUserId,RoleParam param){
		AssertUtil.isNull(roleMapper.findByName(param.getName()), "角色名称重复");
		RoleEntity entity = BeanUtils.copy(param, RoleEntity.class);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(operUserId);
		roleMapper.insertSelective(entity);
	}
	
	public void updateRole(int operUserId,RoleParam param){
		RoleEntity entity = roleMapper.selectByPrimaryKey(param.getId());
		AssertUtil.notNull(entity);
		RoleEntity sameNameEntity = roleMapper.findByName(param.getName());
		if(sameNameEntity != null && !sameNameEntity.getId().equals(param.getId())){
			throw new JeesuiteBaseException(ExceptionCode.REQUEST_DUPLICATION.code, "角色名称重复");
		}
		entity.setName(param.getName());
		entity.setMemo(param.getMemo());
		entity.setUpdatedAt(new Date());
		entity.setUpdatedBy(operUserId);
		roleMapper.updateByPrimaryKeySelective(entity);
	}
	
	@Transactional
	public void deleteRole(int operUserId,int id){
		//判断是否有用户绑定了该角色
		Integer bindAccountNums = roleMapper.countRoleBindAccount(id);
		if(bindAccountNums > 0)throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "请先解除该角色绑定账号");
		//清除该角色下的资源绑定关系
		resourceMapper.deleteAllRoleResources(id);
		roleMapper.deleteByPrimaryKey(id);
	}
	
	public void switchRole(int operUserId,Integer id,boolean enable){
		RoleEntity entity = roleMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(entity);
    	if(entity.getEnabled() == enable)return;
    	entity.setEnabled(enable);
    	
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(operUserId);
    	
    	roleMapper.updateByPrimaryKey(entity);
	}
	
	@Transactional
	public void  assignmentResources(int operUserId,AssignResourceParam param){
		List<Integer> newIds = Arrays.asList(param.getAssignmentIds());
		List<Integer> assignedIds = resourceMapper.findRoleResourceIds(param.getType(),param.getRoleId());
		
		List<Integer> deleteList = new ArrayList<>(assignedIds);
		deleteList.removeAll(newIds);
		
		boolean acted = false;
		if(!deleteList.isEmpty()){
			resourceMapper.deleteRoleResources(param.getRoleId(), deleteList.toArray(new Integer[0]));
			acted = true;
		}
		
		List<Integer> addList = new ArrayList<>(newIds);
		addList.removeAll(assignedIds);
		
		if(!addList.isEmpty()){
			resourceMapper.addRoleResources(param.getRoleId(), addList.toArray(new Integer[0]));
			acted = true;
		}
		if(acted){
			AuthPermHelper.reset();
		}
	}
}
