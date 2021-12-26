package com.oneplatform.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.FormatValidateUtils;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dao.entity.AccountScopeEntity;
import com.oneplatform.system.dao.mapper.AccountEntityMapper;
import com.oneplatform.system.dao.mapper.AccountScopeEntityMapper;
import com.oneplatform.system.dto.Account;
import com.oneplatform.system.dto.AccountScope;

@Service
public class AccountService {

	@Autowired
	private AccountEntityMapper  aaccountMapper;
	
	@Autowired
	private AccountScopeEntityMapper accountScopeMapper;
	
	public Account findByAccount(String account) {
		AccountEntity entity;
		if(FormatValidateUtils.isMobile(account)) {
			entity = aaccountMapper.findByMobile(account);
		}else if(FormatValidateUtils.isEmail(account)) {
			entity = aaccountMapper.findByEmail(account);
		}else {
			entity = aaccountMapper.findByName(account);
		}
		if(entity == null || !entity.getEnabled()) {
			throw new JeesuiteBaseException("账号不存在或者已停用");
		}
		
		Account acountDto = BeanUtils.copy(entity, Account.class);

		return acountDto;
	}
	
	public Account validateAccount(String type,String account,String password) {
		Account acountDto = findByAccount(account);
		if(!BCrypt.checkpw(password, acountDto.getPassword())) {
			throw new JeesuiteBaseException("账号不存在或密码错误");
		}
		if(type != null) {
			AccountScopeEntity scopeEntity = accountScopeMapper.findByAccountIdAndType(acountDto.getId(), type);
			if(scopeEntity != null) {
				acountDto.setTenantId(scopeEntity.getTenantId());
				acountDto.setPrincipalType(scopeEntity.getPrincipalType());
				acountDto.setPrincipalId(scopeEntity.getPrincipalId());
				acountDto.setAdmin(scopeEntity.getIsAdmin());
			}
		}
		return acountDto;
	}
	
	public List<AccountScope> findAccountScopes(String accountId){
		List<AccountScopeEntity> scopes = accountScopeMapper.findByAccountId(accountId);
		return scopes.stream().map(o -> {
			AccountScope scope = new AccountScope();
			scope.setPrincipalType(o.getPrincipalType());
			scope.setPrincipalId(o.getPrincipalId());
			scope.setTenantId(o.getTenantId());
			scope.setAdmin(o.getIsAdmin());
			return scope;
		}).collect(Collectors.toList());
	}
}
