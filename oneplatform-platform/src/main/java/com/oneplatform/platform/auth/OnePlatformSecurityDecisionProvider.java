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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.oneplatform.base.GlobalContants.ModuleType;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.system.constants.ResourceType;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;
import com.oneplatform.system.dto.LoginUserInfo;
import com.oneplatform.system.service.AccountService;
import com.oneplatform.system.service.ResourcesService;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年12月4日
 */
@Component
public class OnePlatformSecurityDecisionProvider extends SecurityDecisionProvider {

	private @Autowired ResourceEntityMapper resourceMapper;
	private @Autowired ModuleEntityMapper moduleMapper;
	private @Autowired ResourcesService resourcesService;
	private @Autowired AccountService accountService;

	@Override
	public String contextPath() {
		return ResourceUtils.getProperty("server.servlet.context-path", "");
	}
	
	@Override
	public String[] anonymousUris() {
		return StringUtils.splitByWholeSeparator(ResourceUtils.getProperty("anonymous.uris"), ";");
	}

	@Override
	public BaseUserInfo validateUser(String name, String password) throws UserNotFoundException, UserPasswordWrongException {
		AccountEntity entity = accountService.findByLoginAccount(name);
		if(entity == null)throw new UserNotFoundException();
		password = AccountEntity.encryptPassword(password);
		if(!password.equals(entity.getPassword()))throw new UserPasswordWrongException();
		
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setId(entity.getId());
		userInfo.setUserName(entity.getUsername());
		
		return userInfo;
	}

	@Override
	public List<String> findAllUriPermissionCodes() {
		List<String> result = new ArrayList<>();
		Map<Integer,ModuleEntity> modulesMap = moduleMapper.findAll().stream().collect(Collectors.toMap(ModuleEntity::getId, entity -> entity));
		List<ResourceEntity> resources = resourceMapper.findResources(ResourceType.uri.name());
		ModuleEntity module;
		for (ResourceEntity resource : resources) {
			module = modulesMap.get(resource.getModuleId());
			if(module == null)continue;
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase(module.getServiceId()) 
					|| ModuleType.plugin.name().equals(module.getModuleType())){
				result.add(resource.getResource());
			}else{ 
				result.add("/" + module.getRouteName() + resource.getResource());
			}
		}
		return result;
	}


	@Override
	public List<String> getUserPermissionCodes(Serializable userId) {
		Set<String> codes = resourcesService.findAllPermsByUserId((int)userId);
		return new ArrayList<String>(codes);
	}

	@Override
	public void authorizedPostHandle(UserSession session) {
		LoginSession loginSession = new LoginSession(session.getSessionId(),(int)session.getUserId() , session.getUserName());
		LoginContext.setLoginSession(loginSession);
		RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
				loginSession.toEncodeString());
	}

	@Override
	public String superAdminName() {
		return GlobalContants.SUPER_ADMIN_NAME;
	}

}
