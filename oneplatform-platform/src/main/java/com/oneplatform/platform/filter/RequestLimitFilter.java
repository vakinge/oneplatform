package com.oneplatform.platform.filter;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.google.common.util.concurrent.RateLimiter;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.springweb.model.WrapperResponse;
import com.jeesuite.springweb.utils.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.platform.PlatformConfigManager;
import com.oneplatform.platform.filter.internal.PerFrequencyLimiter;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月18日
 */
public class RequestLimitFilter extends ZuulFilter implements InitializingBean,DisposableBean{
	
	private static final String REQUEST_LIMIT_ENABLED_KEY = "request.limit.enabled";


	private static Logger log = LoggerFactory.getLogger(RequestLimitFilter.class);
	
	
	private static final String MSG_TOO_MANY_REQUESTS = "{\"code\": 429,\"msg\":\"Too Many Requests\"}";
	private static final String MSG_REQUEST_TOO_FAST = "{\"code\": 429,\"msg\":\"Request Too Fast\"}";
	private static final String MSG_403_IP_FORBIDDEEN = "{\"code\": 403,\"msg\":\"IP forbidden\"}";
	
	@Autowired
	private PlatformConfigManager configManager;
	
	//令牌桶算法，每秒允许最大并发限制
	private RateLimiter gloabalLimiter; 
	//单个用户频率限制
	private PerFrequencyLimiter perLimiter;
	
	@Value("${request.limit.post-only:true}")
	private boolean postOnly;
	@Value("${request.limit.max-connections:0}")
	private int maxConnectionLimit;
	@Value("${request.limit.per-frequency:10/10s}")
	private String frequencyRule;
	
	@Override
	public boolean shouldFilter() {
		return ResourceUtils.getBoolean(REQUEST_LIMIT_ENABLED_KEY);
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 2;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
        try {
    		HttpServletRequest request = ctx.getRequest();
    		
    		if(postOnly && !HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())){
    			return null;
    		}
    		
    		if(HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod()) || HttpMethod.HEAD.name().equalsIgnoreCase(request.getMethod())){
    			return null;
    		}
    		
    		if(gloabalLimiter != null){
    			//limiter.acquire();
    			if(!gloabalLimiter.tryAcquire(1, TimeUnit.SECONDS)){
    				ctx.setSendZuulResponse(false);
    				ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
    				ctx.setResponseBody(MSG_TOO_MANY_REQUESTS);
    				return null;
    			}
    		}
    		

    		//后台系统不限制
    		if(perLimiter != null){
    			LoginSession session = (LoginSession) ctx.get(GlobalFilter.CONTEXT_SESSION_KEY);
    			if(!perLimiter.tryAcquire(request,session.getSessionId())){
    				ctx.setSendZuulResponse(false);
    				ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
    				ctx.setResponseBody(MSG_REQUEST_TOO_FAST);
    				return null;
    			}
    		}
		} catch (Exception e) {
			String error = "Error during filtering[RequestLimitFilter]";
			log.error(error,e);
			WebUtils.responseOutJson(ctx.getResponse(), JsonUtils.toJson(new WrapperResponse<>(500, error)));
		}
		return null;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(maxConnectionLimit > 0)gloabalLimiter = RateLimiter.create(maxConnectionLimit); 
		String[] segments = frequencyRule.split("/");
		int time = Integer.parseInt(segments[1].replaceAll("[^0-9]", ""));
		if(segments[1].toLowerCase().endsWith("m")){
			time = time * 60;
		}
		perLimiter = new PerFrequencyLimiter(time, Integer.parseInt(segments[0]));
		log.info("per-Frequency:{}/{}s",segments[0],time);
	}

	@Override
	public void destroy() throws Exception {
		if(perLimiter != null)perLimiter.close();
	}
}
