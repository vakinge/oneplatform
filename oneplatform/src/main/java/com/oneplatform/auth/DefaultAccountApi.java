package com.oneplatform.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.AuthUser;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.zuul.api.AccountApi;
import com.jeesuite.zuul.model.AccountScope;
import com.oneplatform.system.dto.Account;
import com.oneplatform.system.service.AccountService;

@Service
public class DefaultAccountApi implements AccountApi {

	@Autowired
	private AccountService accountService;
	
	private Map<String, String> superAdmins = ResourceUtils.getMappingValues("jeesuite.security.superadmin.mapping");


	@Override
	public LoginUserInfo validateAccount(String type,String accountName, String password) throws JeesuiteBaseException {
		LoginUserInfo authUser = new LoginUserInfo();
		if(superAdmins.containsKey(accountName)) {
			if(!BCrypt.checkpw(password, superAdmins.get(accountName))) {
				throw new JeesuiteBaseException("账号不存在或密码错误");
			}
			authUser.setAdmin(true);
			authUser.setName(accountName);
		}else {
			Account account = accountService.validateAccount(type,accountName, password);
			authUser.setId(account.getId());
			authUser.setName(account.getName());
			authUser.setDefaultTenantId(account.getTenantId());
			authUser.setPrincipalType(account.getPrincipalType());
			authUser.setPrincipalId(account.getPrincipalId());
			authUser.setAdmin(account.isAdmin());
		}
		
		return authUser;
	}

	@Override
	public List<AccountScope> findAccountScopes(String accountId) {
		List<com.oneplatform.system.dto.AccountScope> scopes = accountService.findAccountScopes(accountId);
		return BeanUtils.copy(scopes, AccountScope.class);
	}

}
