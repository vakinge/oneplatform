package com.oneplatform.base;

import com.jeesuite.springweb.RequestContextHelper;
import com.jeesuite.springweb.WebConstants;
import com.jeesuite.springweb.exception.UnauthorizedException;
import com.oneplatform.base.model.LoginUserInfo;

public class LoginContext {
	
	private final static ThreadLocal<LoginUserInfo> holder = new ThreadLocal<>();

	public static LoginUserInfo getLoginUser(){
		LoginUserInfo loginUserInfo = holder.get();
		if(loginUserInfo == null){
			String headerString = RequestContextHelper.getRequest().getHeader(WebConstants.HEADER_AUTH_USER);
			loginUserInfo = LoginUserInfo.decode(headerString);
			//由于线程复用导致部分没有响应的请求没有清掉线程变量，故去掉
//			if(loginUserInfo != null){
//				holder.set(loginUserInfo);
//			}
		}
		return loginUserInfo;
	}
	
	public static void setLoginUser(LoginUserInfo loginUserInfo){
		if(loginUserInfo == null){
			holder.remove();
		}else{			
			holder.set(loginUserInfo);
		}
	}
	
	public static void resetLoginUserHolder(){
		holder.remove();
	}
	
	/**
	 * 获取登录信息，未登录抛出异常
	 * @return
	 */
	public static LoginUserInfo getAndValidateLoginUser(){
		LoginUserInfo loginInfo = getLoginUser();
		if(loginInfo == null)throw new UnauthorizedException();
		return loginInfo;
	}
	
}
