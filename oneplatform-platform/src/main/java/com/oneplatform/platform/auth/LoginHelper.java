package com.oneplatform.platform.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesuite.common.util.ResourceUtils;
import com.oneplatform.base.GlobalContants.UserScope;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.base.model.UserInfo;
import com.oneplatform.system.dao.entity.AccountEntity;

public class LoginHelper {

	public static boolean ssoEnabled = ResourceUtils.getBoolean("sso.enabled", true);
	public static UserInfo login(HttpServletRequest request, HttpServletResponse response, AccountEntity account) {

		LoginSession session = AuthSessionHelper.getSessionIfNotCreateAnonymous(request, response,ssoEnabled);
		session.setUserId(account.getId());
		session.setUserName(account.getUsername());
		if(account.getId() == 1){
			session.getScopes().add(UserScope.sa.name());
		}else{
			//TODO 后续支持多端用户后需要查询scope表
		}
		UserInfo userInfo = new UserInfo(account.getId(), account.getUsername());
		userInfo.setRealname(account.getRealname());
		userInfo.setEmail(account.getEmail());
		userInfo.setMobile(account.getMobile());
		session.setUserInfo(userInfo);
		session.setExpiresIn(LoginSession.SESSION_EXPIRE_SECONDS);
		
		if(ssoEnabled){			
			LoginSession otherSession = AuthSessionHelper.getLoginSessionByUserId(account.getId());
			if(otherSession != null && !otherSession.getSessionId().equals(session.getSessionId())){
				AuthSessionHelper.removeLoginSession(otherSession.getSessionId());
			}
		}
		
		AuthSessionHelper.storgeLoginSession(session,ssoEnabled);
		//敏感信息不返回前端
		userInfo.setRealname(null);
		userInfo.setEmail(null);
		userInfo.setMobile(null);
		
		return userInfo;
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		AuthSessionHelper.destroySessionAndCookies(request, response);
	}
}
