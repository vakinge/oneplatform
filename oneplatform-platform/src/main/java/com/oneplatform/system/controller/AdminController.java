package com.oneplatform.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.springweb.model.WrapperResponse;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.UserInfo;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.platform.auth.LoginHelper;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dto.param.LoginParam;
import com.oneplatform.system.service.AccountService;
import com.oneplatform.system.service.ResourcesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user/")
@Api("User Login API")
public class AdminController {

	private @Autowired AccountService accountService;
	private @Autowired ResourcesService roleResourcesService;

	@ApiOperation(value = "处理登录请求")
	@RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<UserInfo> doLogin(HttpServletRequest request,HttpServletResponse response,@RequestBody LoginParam param) {
		AccountEntity entity = accountService.findByLoginAccount(param.getLoginName());
		AssertUtil.notNull(entity, "用户不存在");
		String password = AccountEntity.encryptPassword(param.getPassword());
		AssertUtil.isTrue(password.equals(entity.getPassword()), ExceptionCode.REQUEST_PARAM_ERROR.code, "密码错误");
		
		//login add cookies
		UserInfo userInfo = LoginHelper.login(request, response, entity);
		return new WrapperResponse<>(userInfo);
	} 
	
	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,HttpServletResponse response) {
		LoginHelper.logout(request, response);
		return "redirect:" + WebUtils.getBaseUrl(request) + "/login.html";
	} 
	
	@ApiOperation(value = "查询当前登录用户信息")
	@RequestMapping(value = "profile", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<UserInfo> getCurrentUser() {
		UserInfo user= LoginContext.getLoginSession().getUserInfo();
		//敏感信息不返回前端
		user.setRealname(null);
		user.setEmail(null);
		user.setMobile(null);
		return new WrapperResponse<>(user);
	} 
	
	
	@ApiOperation(value = "查询当前登录用户菜单")
	@RequestMapping(value = "menus", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getCurrentMenus() {
		List<TreeModel> menus = roleResourcesService.findUserMenus(LoginContext.getLoginUserId());
		return new WrapperResponse<>(menus);
	} 
	
}
