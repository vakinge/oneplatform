package com.oneplatform.platform.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oneplatform.base.model.LoginSession;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.system.dao.entity.AccountEntity;

public class LoginHelper {

	public static void login(HttpServletRequest request, HttpServletResponse response, AccountEntity account) {

		LoginSession session = AuthSessionHelper.getSessionIfNotCreateAnonymous(request, response);
		session.setUserId(account.getId());
		session.setUserName(account.getUsername());
		
		LoginUserInfo userInfo = new LoginUserInfo(account.getId(), account.getUsername());
		userInfo.setRealname(account.getRealname());
		userInfo.setEmail(account.getEmail());
		userInfo.setMobile(account.getMobile());
		session.setUserInfo(userInfo);
		session.setExpiresIn(LoginSession.SESSION_EXPIRE_SECONDS);
		
		LoginSession otherSession = AuthSessionHelper.getLoginSessionByUserId(account.getId());
		if(otherSession != null && !otherSession.getSessionId().equals(session.getSessionId())){
			AuthSessionHelper.removeLoginSession(otherSession.getSessionId());
		}
		
		AuthSessionHelper.storgeLoginSession(session);
		
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		AuthSessionHelper.destroySessionAndCookies(request, response);
	}
}
