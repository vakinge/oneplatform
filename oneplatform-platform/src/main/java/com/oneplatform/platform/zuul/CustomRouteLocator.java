package com.oneplatform.platform.zuul;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.spring.InstanceFactory;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;  
  
/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年10月3日
 */
public class CustomRouteLocator extends SimpleRouteLocator   {  
  
    private final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);  
  
    private Set<String> registryRouteIds;
    
    public CustomRouteLocator(String servletPath, ZuulProperties properties) {  
        super(servletPath, properties);  
        registryRouteIds = new HashSet<>(properties.getRoutes().keySet());
        logger.info("servletPath:{},registryRouteIds:{}", servletPath,registryRouteIds);  
    }  
  

    public void refresh() {  
    	super.doRefresh();  
    }  
  
    @Override  
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {  
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();  
        
        routesMap.putAll(super.locateRoutes());  
        
        ModuleEntityMapper entityMapper = null;
        try {entityMapper = InstanceFactory.getInstance(ModuleEntityMapper.class);} catch (Exception e) {}
        if(entityMapper == null)return routesMap;
        
        List<ModuleEntity> serviceModules = entityMapper.findAllEnabled();
        
        String path = null;
        ZuulProperties.ZuulRoute zuulRoute = null;
        for (ModuleEntity module : serviceModules) {
        	if(module.getEnabled() == false || module.getId() == 1 || module.getInternal())continue;
        	path = String.format("/%s/**", module.getRouteName());
        	
        	zuulRoute = new ZuulProperties.ZuulRoute();  
        	zuulRoute.setPath(path);
        	zuulRoute.setId(module.getRouteName());
        	zuulRoute.setServiceId(module.getServiceId());
        	
        	routesMap.put(path, zuulRoute);
        	registryRouteIds.add(module.getRouteName());
        	
        	logger.info("add new Route:{} = {}",path,JsonUtils.toJson(zuulRoute));
		}

        return routesMap;  
    }  
  
 
} 
