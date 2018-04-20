/*
   * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.platform.shiro;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.oneplatform.base.model.LoginUserInfo;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月11日
 */
public class SSOHelper {
	
	private static final String SSO_CACHE_KEY = "shiro.sso:%s";

	public static void process(HttpServletRequest request){
		LoginUserInfo loginUser = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
		if(loginUser == null)return;
		
		DefaultWebSecurityManager securityManager =  (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		Cache<Object, Object> cache = securityManager.getCacheManager().getCache(CachingSessionDAO.ACTIVE_SESSION_CACHE_NAME);
		
		String cacheKey = String.format(SSO_CACHE_KEY, loginUser.getId());
		String oldSessionId = Objects.toString(cache.get(cacheKey), null);
		
		String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
		if(oldSessionId != null && !StringUtils.equals(sessionId, oldSessionId)){
			//tick
			cache.remove(oldSessionId);
		}
		
		cache.put(cacheKey, sessionId);
	}
	
	public static void reset(){
		LoginUserInfo loginUser = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
		if(loginUser == null)return;
		
		DefaultWebSecurityManager securityManager =  (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		Cache<Object, Object> cache = securityManager.getCacheManager().getCache(CachingSessionDAO.ACTIVE_SESSION_CACHE_NAME);
		
		String cacheKey = String.format(SSO_CACHE_KEY, loginUser.getId());
		cache.remove(cacheKey);
	}
}
