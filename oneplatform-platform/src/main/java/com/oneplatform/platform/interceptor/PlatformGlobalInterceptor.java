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
package com.oneplatform.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.jeesuite.springweb.WebConstants;
import com.jeesuite.springweb.exception.ForbiddenAccessException;
import com.jeesuite.springweb.exception.UnauthorizedException;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.interceptor.GlobalDefaultInterceptor;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.platform.shiro.AuthHelper;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月25日
 */
public class PlatformGlobalInterceptor extends GlobalDefaultInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean anon = AuthHelper.anonymousAllowed(request.getRequestURI());

		LoginUserInfo user = null;
		try {
			user = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
		} catch (Exception e) {
		}
		if (user != null) {
			LoginContext.setLoginUser(user);
			RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
					user.toEncodeString());
		}

		if (anon == false && user == null) {
			throw new UnauthorizedException();
		}
		
		String permssionCode = AuthHelper.getPermssionCode(request.getRequestURI());
		if(StringUtils.isNotBlank(permssionCode) && !SecurityUtils.getSubject().isPermitted(permssionCode)){
			throw new ForbiddenAccessException();
		}
		
		return super.preHandle(request, response, handler);
	}

	
}
