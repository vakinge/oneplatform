package com.oneplatform.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jeesuite.common.GlobalRuntimeContext;
import com.jeesuite.common.util.PathMatcher;
import com.jeesuite.zuul.model.BizSystemModule;


/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月27日
 */
@Component
public class PlatformConfigManager implements InitializingBean {


	@Autowired
	private Environment environment;

	private PathMatcher globalAnonUriMatcher;
	private Map<String,BizSystemModule> routeServices = new ConcurrentHashMap<>();
	private List<String> filterIgnoreRouteList = new ArrayList<>();
	
	private String swaggerPassword;
	
	private volatile boolean pauseProcessRequest = false;
	
	public void addRouteService(BizSystemModule service){
		synchronized (routeServices) {
			routeServices.put(service.getRouteName(), service);
			if(StringUtils.isNotEmpty(service.getAnonymousUris())){
				String routePath = GlobalRuntimeContext.getContextPath() + "/" + service.getRouteName();
				service.setAnonUriMatcher(new PathMatcher(routePath, service.getAnonymousUris()));
			}
		}
	}
	
	public boolean hasRoute(String routeName){
		if(routeName == null)return false;
		return routeServices.containsKey(routeName);
	}
	
	public BizSystemModule getRouteService(String routeName){
		if(routeName == null)return null;
		return routeServices.get(routeName);
	}
	
	/**
	 * @return the globalAnonUriMatcher
	 */
	public PathMatcher getGlobalAnonUriMatcher() {
		return globalAnonUriMatcher;
	}

	public Map<String, BizSystemModule> getRouteServices() {
		return new HashMap<>(routeServices);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		if(environment.containsProperty("global.anonymous.uris")){
			globalAnonUriMatcher = new PathMatcher(GlobalRuntimeContext.getContextPath(), environment.getProperty("global.anonymous.uris"));
		}
		
		if(environment.containsProperty("global.filter.ignore.route-name")){
			filterIgnoreRouteList.addAll(Arrays.asList(environment.getProperty("global.filter.ignore.route-name").split(",|;")));
		}
		
		swaggerPassword = environment.getProperty("swagger.password", "swagger");
		
	}
	
	/**
	 * @return the filterIgnoreRouteList
	 */
	public List<String> getFilterIgnoreRouteList() {
		return filterIgnoreRouteList;
	}


	public boolean isPauseProcessRequest() {
		return pauseProcessRequest;
	}

	public void setPauseProcessRequest(boolean pauseProcessRequest) {
		this.pauseProcessRequest = pauseProcessRequest;
	}

	public String getSwaggerPassword() {
		return swaggerPassword;
	}
	
}
