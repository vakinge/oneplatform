package com.oneplatform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.model.AuthUser;
import com.jeesuite.security.SecurityDelegating;
import com.jeesuite.springweb.CurrentRuntimeContext;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.dto.param.LoginParam;

@Controller
@RequestMapping("/user")
public class UserAuthController {

	@RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> doLogin(HttpServletRequest request,HttpServletResponse response,@RequestBody LoginParam param) {
		SecurityDelegating.doAuthentication(param.getAccount(), param.getPassword());
		return new WrapperResponse<>();
	} 
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> logout(HttpServletRequest request,HttpServletResponse response) {
		SecurityDelegating.doLogout();
		return new WrapperResponse<>();
	} 
	
	@RequestMapping(value = "current", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<AuthUser> getCurrentUser() {
		AuthUser authUser = CurrentRuntimeContext.getAndValidateCurrentUser();
		return new WrapperResponse<>(authUser);
	} 
}
