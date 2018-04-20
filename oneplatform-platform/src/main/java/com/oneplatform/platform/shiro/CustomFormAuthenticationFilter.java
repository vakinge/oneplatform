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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.jeesuite.springweb.utils.WebUtils;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月25日
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final String MSG_401_UNAUTHORIZED = "{\"code\": 401,\"msg\":\"401 Unauthorized\"}";
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
            	WebUtils.responseOutJson((HttpServletResponse)response, MSG_401_UNAUTHORIZED);
                return false;
            }
        } else {
        	if(WebUtils.isAjax((HttpServletRequest)request)){        		
        		WebUtils.responseOutJson((HttpServletResponse)response, MSG_401_UNAUTHORIZED);
        	}else{        		
        		saveRequestAndRedirectToLogin(request, response);
        	}
            return false;
        }
    }

	
}
