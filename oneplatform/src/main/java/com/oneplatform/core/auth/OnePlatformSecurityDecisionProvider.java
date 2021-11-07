package com.oneplatform.core.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeesuite.common.model.AuthUser;
import com.jeesuite.security.SecurityDecisionProvider;
import com.jeesuite.security.exception.UserNotFoundException;
import com.jeesuite.security.exception.UserPasswordWrongException;
import com.jeesuite.security.model.ApiPermission;
import com.oneplatform.modules.system.dao.entity.AccountEntity;
import com.oneplatform.modules.system.service.AccountService;

@Component
public class OnePlatformSecurityDecisionProvider extends SecurityDecisionProvider {

	@Autowired
	private AccountService accountService;
	
	@Override
	public List<String> anonymousUrlPatterns() {
		return Arrays.asList("");
	}

	@Override
	public AuthUser validateUser(String name, String password)
			throws UserNotFoundException, UserPasswordWrongException {
		AccountEntity account = accountService.findByAccount(name);
		if(account == null) {
			throw new UserNotFoundException();
		}
		
		if(!StringUtils.equals(AccountEntity.encryptPassword(password), account.getPassword())) {
			throw new UserPasswordWrongException();
		}
		
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setId(account.getId().toString());
		userInfo.setUsername(account.getName());
			
		return userInfo;
	}

	@Override
	public List<ApiPermission> getAllApiPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserApiPermissionUris(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String error401Page() {
		return null;
	}

	@Override
	public String error403Page() {
		return null;
	}

}
