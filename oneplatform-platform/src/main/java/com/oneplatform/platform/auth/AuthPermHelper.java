/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.platform.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.spring.InstanceFactory;
import com.oneplatform.system.constants.ResourceType;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;
import com.oneplatform.system.service.ResourcesService;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月14日
 */
public class AuthPermHelper {
	
	private static final String USER_PERMS_PRIFIX = "perms:";

	private static Cache cache =  InstanceFactory.getInstance(AuthCacheManager.class).getPermCache();

	private static volatile boolean loadFinished = false;
	private static String contextPath = ResourceUtils.getProperty("server.context-path");
	private static final String WILDCARD_START = "{";
	
	private static volatile List<String> anonUris = new ArrayList<>();
	private static volatile List<Pattern> anonUriPatterns = new ArrayList<>();
	// 无通配符uri
	private static volatile Map<String,String> nonWildcardUriPerms = new HashMap<>();
	private static volatile Map<Pattern,String>  wildcardUriPermPatterns = new HashMap<>();
	
	public static boolean anonymousAllowed(String uri){
		
        doLoadPermDatasIfRequired();
		
		if(anonUris.contains(uri))return true;
		for (Pattern pattern : anonUriPatterns) {
			if(pattern.matcher(uri).matches())return true;
		}
		return false;
	}

	public static String getPermssionCode(String uri){
		
		doLoadPermDatasIfRequired();
		
		if(anonUris.contains(uri))return null;
		for (Pattern pattern : anonUriPatterns) {
			if(pattern.matcher(uri).matches())return null;
		}
		if(nonWildcardUriPerms.containsKey(uri))return nonWildcardUriPerms.get(uri);
		
		for (Pattern pattern : wildcardUriPermPatterns.keySet()) {
			if(pattern.matcher(uri).matches())return wildcardUriPermPatterns.get(pattern);
		}
		
		return null;
	}
	
	
	public static boolean isPermitted(int accountId,String permCode){
		String key = USER_PERMS_PRIFIX + accountId;
		Set<String> perms = cache.getObject(key);
		if(perms == null || perms.isEmpty()){
			ResourcesService resourcesService = InstanceFactory.getInstance(ResourcesService.class);
			perms = resourcesService.findAllPermsByUserId(accountId);
			cache.setObject(key, perms);
		}
		return perms.contains(permCode);
	}
	
	private static void doLoadPermDatasIfRequired(){
		if(loadFinished)return;
		
		if(contextPath.endsWith("/")){
			contextPath = contextPath.substring(0,contextPath.indexOf("/"));
		}
		
		String[] uris = StringUtils.split(ResourceUtils.getProperty("anonymous.uris"), ";");
        for (String uri : uris) {
        	uri = contextPath + uri;
        	if(uri.endsWith("*")){
        		String regex = uri.replaceAll("\\*+", ".*");
        		anonUriPatterns.add(Pattern.compile(regex));
        	}else{
        		anonUris.add(uri);
        	}
        }
		
		ModuleEntityMapper moduleMapper = InstanceFactory.getInstance(ModuleEntityMapper.class);
		List<ModuleEntity> modules = moduleMapper.findAllEnabled();
		
		Map<Integer,ModuleEntity> modulesAsmap = new HashMap<>();
		for (ModuleEntity module : modules) {
			modulesAsmap.put(module.getId(), module);
		}
		ResourceEntityMapper resourceMapper = InstanceFactory.getInstance(ResourceEntityMapper.class);
		List<ResourceEntity> resources = resourceMapper.findResources(ResourceType.uri.name());
		String fullUri = null;
		for (ResourceEntity resource : resources) {
			if(resource.getModuleId() == 0){
				fullUri = contextPath + resource.getCode();
			}else{ 
				ModuleEntity module = modulesAsmap.get(resource.getModuleId());
				if(module == null)continue;
				fullUri = contextPath + "/" + module.getRouteName() + resource.getCode();
			}
			
			if(resource.getCode().contains(WILDCARD_START)){
				String regex = fullUri.replaceAll("\\{.*?(?=})", ".*").replaceAll("\\}", "");
				wildcardUriPermPatterns.put(Pattern.compile(regex),resource.getCode());
			}else{
				nonWildcardUriPerms.put(fullUri, resource.getCode());
			}
		}

		loadFinished = true;
	}
	
	public static void refreshPermData(int accountId){
		String key = USER_PERMS_PRIFIX + accountId;
		cache.remove(key);
	}
	
	public static void refreshPermData(){
		cache.removeAll();
	}
	
	public static void reset(){
		anonUris.clear();
		anonUriPatterns.clear();
		nonWildcardUriPerms.clear();
		wildcardUriPermPatterns.clear();
		loadFinished = false;
	}

}
