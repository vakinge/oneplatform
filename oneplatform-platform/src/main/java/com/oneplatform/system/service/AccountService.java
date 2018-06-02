package com.oneplatform.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.util.BeanCopyUtils;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor.PageDataLoader;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.PageResult;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dao.entity.RoleEntity;
import com.oneplatform.system.dao.mapper.AccountEntityMapper;
import com.oneplatform.system.dao.mapper.RoleEntityMapper;
import com.oneplatform.system.dto.param.AccountParam;
import com.oneplatform.system.dto.param.AccountQueryParam;
import com.oneplatform.system.dto.param.UpdatepasswordParam;

@Service
public class AccountService {

	private @Autowired AccountEntityMapper accountMapper;
	private @Autowired RoleEntityMapper roleMapper;

	public AccountEntity findById(int id) {
		AccountEntity entity = accountMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(entity);
		List<RoleEntity> roles = roleMapper.findUserRoles(entity.getId());
		entity.setRoles(roles);
		return entity;
	}
	
	public AccountEntity findByLoginAccount(String loginName) {
		return accountMapper.findByLoginName(loginName);
	}
	
	public PageResult<AccountEntity> pageQuery(PageParams pageParam,AccountQueryParam param){
		
		Page<AccountEntity> page = PageExecutor.pagination(pageParam, new PageDataLoader<AccountEntity>() {
			@Override
			public List<AccountEntity> load() {
				return accountMapper.findByParam(param);
			}
		});
		return new PageResult<AccountEntity>(page.getPageNo(), page.getPageSize(), page.getTotal(), page.getData());
	}

	@Transactional
	public void addAccount(int operUserId, AccountParam param) {
		AccountEntity existEntity = accountMapper.findByLoginName(param.getMobile());
		AssertUtil.isNull(existEntity, "手机号码已存在");
		existEntity = accountMapper.findByLoginName(param.getEmail());
		AssertUtil.isNull(existEntity, "邮箱已存在");
		existEntity = accountMapper.findByLoginName(param.getUsername());
		AssertUtil.isNull(existEntity, "用户名已存在");
		AccountEntity entity = BeanCopyUtils.copy(param, AccountEntity.class);
		entity.setEnabled(true);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(operUserId);
		entity.setPassword(AccountEntity.encryptPassword("123456"));
		accountMapper.insertSelective(entity);
		
		roleMapper.addAccountRoles(entity.getId(), param.getRoleIds());
	}

	@Transactional
	public void updateAccount(int operUserId, AccountParam param) {
		AccountEntity entity = findById(param.getId());
		entity.setEmail(param.getEmail());
		entity.setMobile(param.getMobile());
		entity.setRealname(param.getRealname());
		entity.setUpdatedAt(new Date());
		entity.setUpdatedBy(operUserId);
		
		accountMapper.updateByPrimaryKeySelective(entity);
		
		assignmentRoles(operUserId, entity.getId(), param.getRoleIds());
		
	}
	
	public void deleteAccount(int operUserId, int id) {
		accountMapper.deleteByPrimaryKey(id);
	}
	
	public void updatePassword(int operUserId, UpdatepasswordParam param) {
		AccountEntity entity = findById(param.getUserId());
		String oldPass = AccountEntity.encryptPassword(param.getOldPassword());
		if(!StringUtils.equals(oldPass, entity.getPassword())){
			throw new JeesuiteBaseException(ExceptionCode.REQUEST_PARAM_ERROR.code, "原密码错误");
		}
		entity.setPassword(AccountEntity.encryptPassword(param.getPassword()));
		entity.setUpdatedAt(new Date());
		entity.setUpdatedBy(operUserId);
		
		accountMapper.updateByPrimaryKeySelective(entity);
	}
	
	public void switchAccount(int operUserId,Integer id,boolean enable){
		AccountEntity entity = findById(id);
		if(entity.getEnabled() != enable){
			entity.setEnabled(enable);
			entity.setUpdatedAt(new Date());
			entity.setUpdatedBy(operUserId);
			
			accountMapper.updateByPrimaryKeySelective(entity);
		}
	}
	
	@Transactional
	public void  assignmentRoles(int operUserId,int accountId,Integer[] roleIds){
		List<Integer> newRoleIds = new ArrayList<Integer>(Arrays.asList(roleIds));
		List<Integer> assignedRoleIds = roleMapper.findAccountRoleIds(accountId);
		
		List<Integer> deleteList = new ArrayList<>(assignedRoleIds);
		deleteList.removeAll(newRoleIds);
		
		if(!deleteList.isEmpty()){
			roleMapper.deleteAccountRoles(accountId, deleteList.toArray(new Integer[0]));
		}
		
		List<Integer> addList = new ArrayList<>(newRoleIds);
		addList.removeAll(assignedRoleIds);
		
		if(!addList.isEmpty()){
			roleMapper.addAccountRoles(accountId, addList.toArray(new Integer[0]));
		}
	}
}
