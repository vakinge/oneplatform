package com.oneplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.CurrentRuntimeContext;
import com.jeesuite.common.model.AuthUser;
import com.jeesuite.security.SecurityDelegating;
import com.jeesuite.security.model.UserSession;
import com.oneplatform.auth.LoginParam;

@Controller
@RequestMapping("/user")
public class UserAuthController {

	@RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody UserSession doLogin(@RequestBody LoginParam param) {
		UserSession session = SecurityDelegating.doAuthentication(param.getUserType(),param.getAccount(), param.getPassword());
		return session;
	} 
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
    public @ResponseBody String logout() {
		SecurityDelegating.doLogout();
		return null;
	} 
	
	@RequestMapping(value = "current", method = RequestMethod.GET)
    public @ResponseBody AuthUser getCurrentUser() {
		AuthUser authUser = CurrentRuntimeContext.getAndValidateCurrentUser();
		return authUser;
	} 
}
