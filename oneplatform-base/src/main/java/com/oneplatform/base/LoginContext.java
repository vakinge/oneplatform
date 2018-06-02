package com.oneplatform.base;

import com.jeesuite.springweb.RequestContextHelper;
import com.jeesuite.springweb.WebConstants;
import com.jeesuite.springweb.exception.UnauthorizedException;
import com.oneplatform.base.model.LoginSession;

public class LoginContext {
	
	private final static ThreadLocal<LoginSession> holder = new ThreadLocal<>();

	public static LoginSession getLoginSession(){
		LoginSession session = holder.get();
		if(session == null){
			String headerString = RequestContextHelper.getRequest().getHeader(WebConstants.HEADER_AUTH_USER);
			session = LoginSession.decode(headerString);
			//由于线程复用导致部分没有响应的请求没有清掉线程变量，故去掉
//			if(session != null){
//				holder.set(session);
//			}
		}
		return session;
	}
	
	public static Integer getLoginUserId(){
		return getAndValidateLoginUser().getUserId();
	}
	
	public static void setLoginSession(LoginSession session){
		if(session == null){
			holder.remove();
		}else{			
			holder.set(session);
		}
	}
	
	public static void resetLoginSessionrHolder(){
		holder.remove();
	}
	
	/**
	 * 获取登录信息，未登录抛出异常
	 * @return
	 */
	public static LoginSession getAndValidateLoginUser(){
		LoginSession loginInfo = getLoginSession();
		if(loginInfo == null)throw new UnauthorizedException();
		return loginInfo;
	}
	
}
