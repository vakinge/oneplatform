package com.oneplatform.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.AuthUser;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.security.model.ApiPermission;
import com.jeesuite.zuul.api.AccountApi;
import com.jeesuite.zuul.security.GatewaySecurityDecisionProvider;

@Component
public class OnePlatformSecurityDecisionProvider extends GatewaySecurityDecisionProvider {

	@Autowired
	private AccountApi accountService;

	@Override
	public AuthUser validateUser(String type,String name, String password) throws JeesuiteBaseException {
		
		AuthUser authUser = accountService.validateAccount(type,name, password);

		LoginUserInfo userInfo = BeanUtils.copy(authUser, LoginUserInfo.class);
		//TODO 租户列表
			
		return userInfo;
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
