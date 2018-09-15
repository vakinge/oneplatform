package com.oneplatform.platform;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jeesuite.spring.ApplicationStartedListener;
import com.oneplatform.platform.zuul.CustomRouteLocator;


/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月27日
 */
@Component
public class PlatformConfigManager implements InitializingBean,ApplicationStartedListener{

	private static Logger log = LoggerFactory.getLogger(PlatformConfigManager.class);
	
	public static final String CONTEXT_CLIENT_IP = "ctx-client-ip";
	public static final String CONTEXT_ROUTE_NAME = "ctx-route-name";
	
	@Autowired
	private Environment environment;
	
	@Autowired  
	private CustomRouteLocator routeLocator; 

	private String contextpth;
	
	private Map<String, Pattern> corsEnabledUriPatterns = new HashMap<>();

	public String getContextpth() {
		return contextpth;
	}

	public void setContextpth(String contextpth) {
		this.contextpth = contextpth;
	}

	public Pattern getCorsEnabledUriPattern(String routeName) {
		return corsEnabledUriPatterns.get(routeName);
	}

	public void setCorsEnabledUriPatterns(Map<String, Pattern> corsEnabledUriPatterns) {
		this.corsEnabledUriPatterns = corsEnabledUriPatterns;
	}

	@Override
	public void onApplicationStarted(ApplicationContext applicationContext) {
		routeLocator.refresh();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		contextpth = environment.getProperty("server.servlet.context-path","");

		log.info("contextpth:{}", contextpth);
		if (StringUtils.isNotBlank(contextpth) && contextpth.endsWith("/")) {
			contextpth = contextpth.substring(0, contextpth.length() - 1);
		}
	}
	
}
