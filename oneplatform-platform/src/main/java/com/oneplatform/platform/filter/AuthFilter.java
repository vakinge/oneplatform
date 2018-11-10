/*
 * Copyright 2016-2018 www.jeesuite.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.springweb.WebConstants;
import com.jeesuite.springweb.utils.WebUtils;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.platform.auth.AuthPermHelper;
import com.oneplatform.platform.auth.AuthSessionHelper;
import com.oneplatform.platform.auth.LoginHelper;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年11月8日
 */
public class AuthFilter implements Filter {

	private static final String MSG_401_UNAUTHORIZED = "{\"code\": 401,\"msg\":\"401 Unauthorized\"}";
	private static String MSG_403_FORBIDDEN = "{\"code\": 403,\"msg\":\"403 Forbidden\"}";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		boolean anon = AuthPermHelper.anonymousAllowed(request.getRequestURI());

		LoginSession session = AuthSessionHelper.getSessionIfNotCreateAnonymous(request,response,LoginHelper.ssoEnabled);

		if (session != null) {
			LoginContext.setLoginSession(session);
			RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
					session.toEncodeString());
		}

		if (anon == false && session.isAnonymous()) {
			WebUtils.responseOutJson(response, MSG_401_UNAUTHORIZED);
			return;
		}
		
		if (anon == false){
			if(session.isSuperAdmin()){
				//TODO 超管可以访问系统默认的接口，不能访问业务接口
			}else{
				String permssionCode = AuthPermHelper.getPermssionCode(request.getRequestURI());
				if(StringUtils.isNotBlank(permssionCode) && !AuthPermHelper.isPermitted(session.getUserId(),permssionCode)){
					WebUtils.responseOutJson(response, MSG_403_FORBIDDEN);
					return;
				}
			}
		}
			
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {}

}
