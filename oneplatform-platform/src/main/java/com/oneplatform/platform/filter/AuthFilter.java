//package com.oneplatform.platform.filter;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.shiro.SecurityUtils;
//import org.springframework.stereotype.Component;
//
//import com.jeesuite.springweb.WebConstants;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.oneplatform.base.LoginContext;
//import com.oneplatform.base.model.LoginUserInfo;
//
//@Component
//public class AuthFilter extends ZuulFilter {
//
//	private static final String MSG_401_UNAUTHORIZED = "{\"code\": 401,\"msg\":\"401 Unauthorized\"}";
//	private List<String> ignoreList = new ArrayList<String>(Arrays.asList("/api/login"));
//	
//	@Override
//	public boolean shouldFilter() {
//		return true;
//	}
//
//	@Override
//	public Object run() {
//		RequestContext ctx = RequestContext.getCurrentContext();
//		boolean anon = ignoreList.contains(ctx.getRequest().getRequestURI());
//
//		LoginUserInfo user = null;
//		try {
//			user = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
//		} catch (Exception e) {
//		}
//		if (user != null) {
//			LoginContext.setLoginUser(user);
//			RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
//					user.toEncodeString());
//		}
//
//		if (anon == false && user == null) {
//			ctx.setSendZuulResponse(false);
//			ctx.setResponseStatusCode(401);
//			ctx.setResponseBody(MSG_401_UNAUTHORIZED);
//			return null;
//		}
//		
//		return null;
//	}
//
//	@Override
//	public String filterType() {
//		return "pre";
//	}
//
//	@Override
//	public int filterOrder() {
//		return 1;
//	}
//
//}
