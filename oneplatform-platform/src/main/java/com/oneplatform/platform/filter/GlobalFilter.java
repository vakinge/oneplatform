package com.oneplatform.platform.filter;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.springweb.WebConstants;
import com.jeesuite.springweb.model.WrapperResponse;
import com.jeesuite.springweb.utils.IpUtils;
import com.jeesuite.springweb.utils.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.base.util.SecurityCryptUtils;
import com.oneplatform.platform.PlatformConfigManager;
import com.oneplatform.platform.auth.AuthSessionHelper;
import com.oneplatform.platform.auth.LoginHelper;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年6月12日
 */
@Component
public class GlobalFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(GlobalFilter.class);
	
	public static final String CONTEXT_SESSION_KEY = "context-session";
	
	@Autowired(required=false)
	private PlatformConfigManager configManager;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		//
		MDC.clear();
		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			HttpServletRequest request = ctx.getRequest();
			ctx.put(PlatformConfigManager.CONTEXT_CLIENT_IP, IpUtils.getIpAddr(request)); 
			
			String invokeIp = request.getHeader(WebConstants.HEADER_INVOKER_IP);
			if(StringUtils.isNotBlank(invokeIp)){
				ctx.addZuulRequestHeader(WebConstants.HEADER_INVOKER_IP,invokeIp);
			}
			
			ctx.addZuulRequestHeader(WebConstants.HEADER_AUTH_TOKEN, SecurityCryptUtils.generateAuthCode());
			
			String proto = request.getHeader(WebConstants.HEADER_FORWARDED_PROTO);
			if(StringUtils.isNotBlank(proto)){
				ctx.addZuulRequestHeader(WebConstants.HEADER_FORWARDED_ORIGN_PROTO,proto);
			}
			//
			String routeName = Objects.toString(ctx.get(PlatformConfigManager.CONTEXT_ROUTE_NAME),null);
			if(routeName == null){			
				routeName = request.getRequestURI().substring(configManager.getContextpth().length() + 1);
				if(routeName.contains("/"))routeName = routeName.substring(0,routeName.indexOf("/"));
				ctx.put(PlatformConfigManager.CONTEXT_ROUTE_NAME, routeName);
			}
			
			LoginSession session = AuthSessionHelper.getSessionIfNotCreateAnonymous(request,ctx.getResponse(),LoginHelper.ssoEnabled);
			ctx.put(CONTEXT_SESSION_KEY, session);
			//
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for (Cookie cookie : cookies) {
					if(cookie.getName().startsWith(WebConstants.HEADER_PREFIX)){
						ctx.addZuulRequestHeader(cookie.getName(),cookie.getValue());
					}
				}
			}
		} catch (Exception e) {
			String error = "Error during filtering[GlobalFilter]";
			log.error(error,e);
			WebUtils.responseOutJson(ctx.getResponse(), JsonUtils.toJson(new WrapperResponse<>(500, error)));
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}


}
