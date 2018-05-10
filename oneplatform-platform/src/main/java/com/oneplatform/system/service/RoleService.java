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
import com.jeesuite.common.util.BeanCopyUtils;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.platform.shiro.AuthHelper;
import com.oneplatform.system.dao.entity.RoleEntity;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;
import com.oneplatform.system.dao.mapper.RoleEntityMapper;
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
	
	public void addRole(LoginUserInfo loginUser,RoleParam param){
		AssertUtil.isNull(roleMapper.findByName(param.getName()), "角色名称重复");
		RoleEntity entity = BeanCopyUtils.copy(param, RoleEntity.class);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(loginUser.getId());
		roleMapper.insertSelective(entity);
	}
	
	public void updateRole(LoginUserInfo loginUser,RoleParam param){
		RoleEntity entity = roleMapper.selectByPrimaryKey(param.getId());
		AssertUtil.notNull(entity);
		RoleEntity sameNameEntity = roleMapper.findByName(param.getName());
		if(sameNameEntity != null && !sameNameEntity.getId().equals(param.getId())){
			throw new JeesuiteBaseException(ExceptionCode.REQUEST_DUPLICATION.code, "角色名称重复");
		}
		entity.setName(param.getName());
		entity.setMemo(param.getMemo());
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(loginUser.getId());
		roleMapper.insertSelective(entity);
	}
	
	@Transactional
	public void deleteRole(LoginUserInfo loginUser,int id){
		//判断是否有用户绑定了该角色
		Integer bindAccountNums = roleMapper.countRoleBindAccount(id);
		if(bindAccountNums > 0)throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "请先解除该角色绑定账号");
		//清除该角色下的资源绑定关系
		resourceMapper.deleteAllRoleResources(id);
		roleMapper.deleteByPrimaryKey(id);
	}
	
	public void switchRole(LoginUserInfo loginUser,Integer id,boolean enable){
		RoleEntity entity = roleMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(entity);
    	if(entity.getEnabled() == enable)return;
    	entity.setEnabled(enable);
    	
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(loginUser.getId());
    	
    	roleMapper.updateByPrimaryKey(entity);
	}
	
	@Transactional
	public void  assignmentResources(LoginUserInfo loginUser,int roleId,Integer[] resourceIds){
		List<Integer> newIds = new ArrayList<Integer>(Arrays.asList(resourceIds));
		List<Integer> assignedIds = resourceMapper.findRoleResourceIds(roleId);
		
		List<Integer> deleteList = new ArrayList<>(assignedIds);
		deleteList.removeAll(newIds);
		
		boolean acted = false;
		if(!deleteList.isEmpty()){
			resourceMapper.deleteRoleResources(roleId, deleteList.toArray(new Integer[0]));
			acted = true;
		}
		
		List<Integer> addList = new ArrayList<>(newIds);
		addList.removeAll(assignedIds);
		
		if(!addList.isEmpty()){
			resourceMapper.addRoleResources(roleId, addList.toArray(new Integer[0]));
			acted = true;
		}
		if(acted){
			AuthHelper.reset();
		}
	}
}
