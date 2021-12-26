package com.oneplatform.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.AuthUser;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.zuul.AccountApi;
import com.jeesuite.zuul.model.AccountScope;
import com.oneplatform.system.dto.Account;
import com.oneplatform.system.service.AccountService;

@Service
public class DefaultAccountApi implements AccountApi {

	@Autowired
	private AccountService accountService;
	


	@Override
	public AuthUser validateAccount(String type,String accountName, String password) throws JeesuiteBaseException {
		Account account = accountService.validateAccount(type,accountName, password);
		AuthUser authUser = new AuthUser();
		authUser.setId(account.getId());
		authUser.setName(account.getName());
		return authUser;
	}

	@Override
	public List<AccountScope> findAccountScopes(String accountId) {
		List<com.oneplatform.system.dto.AccountScope> scopes = accountService.findAccountScopes(accountId);
		return BeanUtils.copy(scopes, AccountScope.class);
	}

}
