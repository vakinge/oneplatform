package com.oneplatform.platform.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jeesuite.springweb.WebConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.platform.auth.AuthPermHelper;

@Component
public class AuthFilter extends ZuulFilter {

	private static final String MSG_401_UNAUTHORIZED = "{\"code\": 401,\"msg\":\"401 Unauthorized\"}";
	private static String MSG_403_FORBIDDEN = "{\"code\": 403,\"msg\":\"403 Forbidden\"}";
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		boolean anon = AuthPermHelper.anonymousAllowed(request.getRequestURI());
		
		LoginSession session = (LoginSession) ctx.get(GlobalFilter.CONTEXT_SESSION_KEY);

		if (session != null) {
			LoginContext.setLoginSession(session);
			RequestContext.getCurrentContext().addZuulRequestHeader(WebConstants.HEADER_AUTH_USER,
					session.toEncodeString());
		}

		if (anon == false && session.isAnonymous()) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody(MSG_401_UNAUTHORIZED);
			return null;
		}
		
		String permssionCode = AuthPermHelper.getPermssionCode(request.getRequestURI());
		if(StringUtils.isNotBlank(permssionCode) && !AuthPermHelper.isPermitted(session.getUserId(),permssionCode)){
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(403);
			ctx.setResponseBody(MSG_403_FORBIDDEN);
			return null;
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
