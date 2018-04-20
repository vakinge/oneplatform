package com.oneplatform.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.platform.shiro.SSOHelper;
import com.oneplatform.system.dto.param.LoginParam;
import com.oneplatform.system.service.ResourcesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/")
@Api("User Login API")
public class AdminController {

	private @Autowired ResourcesService roleResourcesService;

	@ApiOperation(value = "处理登录请求")
	@RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> doLogin(HttpServletRequest request,@RequestBody LoginParam param) {
		
		Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(param.getLoginName(),param.getPassword(),param.isRememerMe());
        try {
            subject.login(token);
            //
            SSOHelper.process(request);
            return new WrapperResponse<>();
        }catch (LockedAccountException lae) {
        	token.clear();
        	throw new JeesuiteBaseException(1001, "账号已锁定");
        } catch (AuthenticationException e) {
            token.clear();
            throw new JeesuiteBaseException(1002, "登录名或者密码错误");
        }
        
		
	} 
	
	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "logout", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<String> logout() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null){
			SSOHelper.reset();
			subject.logout();
		}
		return new WrapperResponse<>();
	} 
	
	@ApiOperation(value = "查询当前登录用户信息")
	@RequestMapping(value = "profile", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<LoginUserInfo> getCurrentUser() {
		LoginUserInfo user= LoginContext.getLoginUser();
		return new WrapperResponse<>(user);
	} 
	
	
	@ApiOperation(value = "查询当前登录用户菜单")
	@RequestMapping(value = "menus", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TreeModel>> getCurrentMenus() {
		LoginUserInfo user= LoginContext.getLoginUser();
		List<TreeModel> menus = roleResourcesService.findUserMenus(user.getId());
		return new WrapperResponse<>(menus);
	} 
	
}
