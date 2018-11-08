package com.oneplatform.platform.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.spring.InstanceFactory;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.model.LoginSession;

public class AuthSessionHelper {
	
	private static final String NULL = "null";
	private static final String ACCESSTOKEN = "accessToken";
	
	//auth cache keys
	private static final String SESSION_CACHE_KEY = "auth:session:%s";
	private final static String LOGIN_UID_CACHE_KEY = "auth:uid:%s";
	//是否是会话cookies
	private static boolean cookieTemporary = Boolean.parseBoolean(ResourceUtils.getProperty("auth.cookie.temporary", "true"));
	private static String SESSION_COOKIE_NAME = ResourceUtils.getProperty("auth.session.cookie.name", "platform_sid");
	
	private static Cache cache;
	
	public static Cache getCache() {
		if(cache == null){
			cache = InstanceFactory.getInstance(AuthCacheManager.class).getSessionCache();
		}
		return cache;
	}

	public static LoginSession getLoginSession(String sessionId){
		if(StringUtils.isBlank(sessionId))return null;
		String key = String.format(SESSION_CACHE_KEY, sessionId);
		return getCache().getObject(key);
	}
	
	
	public static LoginSession getSessionIfNotCreateAnonymous(HttpServletRequest request,HttpServletResponse response,boolean ssoEnabled){
		LoginSession session = null;
		String sessionId = getSessionId(request);
		if(StringUtils.isNotBlank(sessionId)){
			session = getLoginSession(sessionId);
		}
		
		if(session == null){			
			session = LoginSession.create();
			storgeLoginSession(session,ssoEnabled);
			
			String domain = WebUtils.getRootDomain(request);
			Cookie cookie = createSessionCookies(session.getSessionId(), domain, session.getExpiresIn());
			response.addCookie(cookie);
		}

		return session;
	}
	
	public static LoginSession getLoginSessionByUserId(long  userId){
		String key = String.format(LOGIN_UID_CACHE_KEY, userId);
		String sessionId = getCache().getString(key);
		if(StringUtils.isBlank(sessionId))return null;
		return  getLoginSession(sessionId);
	}
	
	public static void storgeLoginSession(LoginSession session,boolean ssoEnabled){
		String key = String.format(SESSION_CACHE_KEY, session.getSessionId());
		getCache().setObject(key,session);
		if(!session.isAnonymous() && ssoEnabled){			
			key = String.format(LOGIN_UID_CACHE_KEY, session.getUserId());
			getCache().setString(key, session.getSessionId());
		}
	}
	
	public static void removeLoginSession(String sessionId){
		String key = String.format(SESSION_CACHE_KEY, sessionId);
		LoginSession session = getLoginSession(sessionId);
		if(session != null){
			getCache().remove(key);
			key = String.format(LOGIN_UID_CACHE_KEY, session.getUserId());
			getCache().remove(key);
		}
	}

    
	private static Cookie createSessionCookies(String sessionId,String domain,int expire){
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME,sessionId);  
		cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		if(expire == 0 || !cookieTemporary){			
			cookie.setMaxAge(expire);
		}
		return cookie;
	}
	
	
	/**
	 * 获取授权Id （accessToken or  sessionId）
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request) {
		String sessionId = request.getParameter(ACCESSTOKEN);
		if(isBlank(sessionId)){
			sessionId = request.getHeader(ACCESSTOKEN);
		}
		if(isBlank(sessionId)){
			Cookie[] cookies = request.getCookies();
			if(cookies == null)return null;
			for (Cookie cookie : cookies) {
				if(SESSION_COOKIE_NAME.equals(cookie.getName())){
					sessionId = cookie.getValue();
					break;
				}
			}
		}
		return sessionId;
	}
	
	private static boolean isBlank(String str){
		return StringUtils.isBlank(str) || NULL.equals(str);
	}
	
	public static String destroySessionAndCookies(HttpServletRequest request,HttpServletResponse response) {
		
		String sessionId = AuthSessionHelper.getSessionId(request);
		if(StringUtils.isNotBlank(sessionId)){
			AuthSessionHelper.removeLoginSession(sessionId);
			
			//
			String domain = WebUtils.getRootDomain(request);
			response.addCookie(createSessionCookies(StringUtils.EMPTY, domain, 0));
		}
		return sessionId;
	}
}
