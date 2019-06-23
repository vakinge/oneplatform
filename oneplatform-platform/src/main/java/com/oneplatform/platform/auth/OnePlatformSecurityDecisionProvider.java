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
package com.oneplatform.platform.auth;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.security.SecurityDecisionProvider;
import com.jeesuite.security.exception.UserNotFoundException;
import com.jeesuite.security.exception.UserPasswordWrongException;
import com.jeesuite.security.model.BaseUserInfo;
import com.jeesuite.security.model.UserSession;
import com.jeesuite.springweb.WebConstants;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.GlobalContants;
import com.oneplatform.base.constants.PermissionType;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dto.LoginUserInfo;
import com.oneplatform.system.service.AccountService;
import com.oneplatform.system.service.BizPlatformService;
import com.oneplatform.system.service.PermissionService;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年12月4日
 */
@Component
public class OnePlatformSecurityDecisionProvider extends SecurityDecisionProvider {

	private @Autowired BizPlatformService bizPlatformService;
	private @Autowired AccountService accountService;
	private @Autowired PermissionService permissionService;

	@Override
	public String contextPath() {
		return ResourceUtils.getProperty("server.servlet.context-path", "");
	}
	
	@Override
	public String[] anonymousUrlPatterns() {
		return StringUtils.splitByWholeSeparator(ResourceUtils.getProperty("anonymous.uris"), ";");
	}

	@Override
	public BaseUserInfo validateUser(String name, String password) throws UserNotFoundException, UserPasswordWrongException {
		AccountEntity entity = accountService.findByLoginAccount(name);
		if(entity == null)throw new UserNotFoundException();
		password = AccountEntity.encryptPassword(password);
		if(!password.equals(entity.getPassword()))throw new UserPasswordWrongException();
		
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setId(entity.getId().toString());
		userInfo.setUserName(entity.getUsername());
		
		return userInfo;
	}

	@Override
	public List<String> findAllUriPermissionCodes() {
		List<String> result = permissionService.buildGlobalPermissionCodes().get(PermissionType.Authorized);
		return result;
	}


	@Override
	public String getCurrentProfile(HttpServletRequest request) {
		//TODO 根据请求域名判断当前业务系统
		return "admin";
	}

	@Override
	public Map<String, List<String>> getUserPermissionCodes(String userId) {
		Map<String, List<String>> permissionCodes = permissionService.buildUserPermissionCodes(Integer.parseInt(userId));
		return permissionCodes;
	}

	@Override
	public void authorizedPostHandle(UserSession session) {
		RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
				session.toEncodeString());
	}

	@Override
	public String superAdminName() {
		return GlobalContants.SUPER_ADMIN_NAME;
	}

	@Override
	public String[] protectedUrlPatterns() {
		return null;
	}

	@Override
	public String _401_Error_Page() {
		return null;
	}

	@Override
	public String _403_Error_Page() {
		return null;
	}

}
