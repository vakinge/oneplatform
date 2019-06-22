package com.oneplatform.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.security.SecurityDelegating;
import com.jeesuite.security.client.LoginContext;
import com.jeesuite.security.model.BaseUserInfo;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.annotation.ApiScanIgnore;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.system.dto.param.LoginParam;
import com.oneplatform.system.dto.param.UpdatepasswordParam;
import com.oneplatform.system.service.AccountService;
import com.oneplatform.system.service.ResourcesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user/")
@Api("User Login API")
@ApiScanIgnore
public class UserContextController {

	private @Autowired AccountService accountService;
	private @Autowired ResourcesService roleResourcesService;

	@ApiOperation(value = "处理登录请求")
	@RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> doLogin(HttpServletRequest request,HttpServletResponse response,@RequestBody LoginParam param) {
		SecurityDelegating.doAuthentication(param.getLoginName(), param.getPassword());
		return new WrapperResponse<>();
	} 
	
	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "logout", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> logout(HttpServletRequest request,HttpServletResponse response) {
		SecurityDelegating.doLogout();
		return new WrapperResponse<>();
	} 
	
	@ApiOperation(value = "查询当前登录用户信息")
	@RequestMapping(value = "profile", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<BaseUserInfo> getCurrentUser() {
		BaseUserInfo userInfo = SecurityDelegating.getCurrentSession().getUserInfo();
		//TODO 敏感信息不返回前端
		return new WrapperResponse<>(userInfo);
	} 
	
	
	@ApiOperation(value = "查询当前登录用户菜单")
	@RequestMapping(value = "menus", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getCurrentMenus() {
		List<TreeModel> menus = roleResourcesService.findUserMenus(LoginContext.getIntFormatUserId());
		return new WrapperResponse<>(menus);
	}
	
	@ApiOperation(value = "更新密码")
	@RequestMapping(value = "updatepwd", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> updatePassword(@RequestBody UpdatepasswordParam param) {
		if(StringUtils.isAnyBlank(param.getPassword(),param.getOldPassword())){
			throw new JeesuiteBaseException(4001, "新旧密码必填");
		}
		Integer loginUserId = LoginContext.getIntFormatUserId();
		param.setUserId(loginUserId);
		accountService.updatePassword(loginUserId, param);
		return new WrapperResponse<>();
	}
	
}
