package com.oneplatform.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.DateUtils;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.ApiInfo;
import com.oneplatform.base.util.ApiInfoHolder;
import com.oneplatform.platform.zuul.CustomRouteLocator;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.submodel.ServiceInstance;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;
import com.oneplatform.system.dto.param.ModuleParam;


/**
 * 模块服务
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月17日
 */
@Service
public class ModuleService  {

	private @Autowired ModuleEntityMapper moduleMapper;
	private @Autowired ResourceEntityMapper resourceMapper;
	private @Autowired RestTemplate restTemplate;
	private @Autowired EurekaClient eurekaClient;
	private @Autowired CustomRouteLocator routeLocator; 
	
	public List<ModuleEntity> findAllModules(){
		List<ModuleEntity> list = moduleMapper.findAll();
		Map<String, ModuleEntity> hisModules = new HashMap<>();
		for (ModuleEntity moduleEntity : list) {
			hisModules.put(moduleEntity.getServiceId().toUpperCase(), moduleEntity);
		}
		Map<String, List<ServiceInstance>> activeInstances = getAllInstanceFromEureka();
		
		boolean refreshRequired = false;
		
		for (String serviceId : activeInstances.keySet()) {
			if(!hisModules.containsKey(serviceId)){
				ModuleEntity moduleEntity = new ModuleEntity();
				moduleEntity.setName(serviceId);
				moduleEntity.setRouteName(serviceId.split("-")[0].toLowerCase());
				moduleEntity.setServiceId(serviceId);
				moduleEntity.setApidocUrl(String.format("/api/%s/swagger-ui.html", moduleEntity.getRouteName()));
				moduleEntity.setCreatedAt(new Date());
				moduleMapper.insertSelective(moduleEntity);
				list.add(moduleEntity);
				refreshRequired = true;
			}
		}
		
		if(refreshRequired){
			routeLocator.refresh();
		}
		for (ModuleEntity module : list) {
			module.setServiceInstances(activeInstances.get(module.getServiceId().toUpperCase()));
		}
	
		return list;
	}
	
	public ModuleEntity getmoduleDetails(int id){
		ModuleEntity entity = moduleMapper.selectByPrimaryKey(id);
    	AssertUtil.notNull(entity);
    	getInstanceFromEureka(entity);
    	return entity;
	}
	
    public void updateModule(int operUserId,ModuleParam param){
    	ModuleEntity entity = moduleMapper.selectByPrimaryKey(param.getId());
    	AssertUtil.notNull(entity);
    	
    	entity.setName(param.getName());
    	entity.setRouteName(param.getRouteName());
    	entity.setApidocUrl(param.getApidocUrl());
    	entity.setCorsUris(param.getCorsUris());
    	entity.setInternal(param.isInternal());
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(operUserId);
    	
    	moduleMapper.updateByPrimaryKeySelective(entity);
    	
	}
    
    public void switchModule(int operUserId,Integer id,boolean enable){
    	if(id == 1){
    		throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "系统模块不允许禁用");
    	}
    	ModuleEntity entity = moduleMapper.selectByPrimaryKey(id);
    	AssertUtil.notNull(entity);
    	if(entity.getEnabled() == enable)return;
    	entity.setEnabled(enable);
    	
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(operUserId);
    	
    	moduleMapper.updateByPrimaryKeySelective(entity);
    	
    }
    
    public void deleteModule(int operUserId,int id){
    	if(id == 1){
    		throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "系统模块不允许禁用");
    	}
    	ModuleEntity moduleEntity = moduleMapper.selectByPrimaryKey(id);
    	if(moduleEntity == null)return;
    	Application application = eurekaClient.getApplication(moduleEntity.getServiceId());
    	if(application != null){
    		throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "该模块在运行中不允许删除");
        }
    	moduleMapper.deleteByPrimaryKey(id);
    }
    
    public Map<Integer, ModuleEntity> getAllModules(boolean enabledOnly){
    	List<ModuleEntity> modules = enabledOnly ? moduleMapper.findAllEnabled() : moduleMapper.findAll();
		Map<Integer, ModuleEntity> moduleMaps = new HashMap<>();
		for (ModuleEntity moduleEntity : modules) {
			moduleMaps.put(moduleEntity.getId(), moduleEntity);
		}
		return moduleMaps;
    }

    private void getInstanceFromEureka(ModuleEntity module){
    	Application application = eurekaClient.getApplication(module.getServiceId());
    	if(application != null){
    		List<InstanceInfo> instances = application.getInstances();
    		if(instances == null)return;
    		for (InstanceInfo instance : instances) {
    			module.getServiceInstances().add(new ServiceInstance(instance));
			}
    	}
    }
    
    private Map<String, List<ServiceInstance>> getAllInstanceFromEureka(){
    	
    	Map<String, List<ServiceInstance>> result = new HashMap<>();
    	List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
    	if(applications == null)return result;
    	List<ServiceInstance> instances;
    	for (Application application : applications) {
    		instances = result.get(application.getName());
    		if(instances == null){
    			instances = new ArrayList<>();
    			result.put(application.getName(), instances);
    		}
    		
    		for (InstanceInfo instanceInfo : application.getInstances()) {
    			instances.add(new ServiceInstance(instanceInfo));
			}
    		
		}
    	
    	return result;
    }

	public List<ApiInfo> findModuleApis(int moduleId){
		if(moduleId == 1)return ApiInfoHolder.getApiInfos();
		ModuleEntity module = moduleMapper.selectByPrimaryKey(moduleId);
		List<ApiInfo> apiInfos = null;
		try {
			ParameterizedTypeReference<List<ApiInfo>> arearesponseType = new ParameterizedTypeReference<List<ApiInfo>>() {};
			apiInfos = restTemplate.exchange("http://"+module.getServiceId().toUpperCase()+"/getApis", HttpMethod.GET, null, arearesponseType).getBody();
			if(StringUtils.isBlank(module.getApiInfos()) || DateUtils.getDiffMinutes(module.getUpdatedAt(), new Date()) > 60){				
				module.setApiInfos(JsonUtils.toJson(apiInfos));
				module.setUpdatedAt(new Date());
				moduleMapper.updateByPrimaryKey(module);
			}
		} catch (Exception e) {
			apiInfos = JsonUtils.toList(module.getApiInfos(), ApiInfo.class);
		}
		
		return apiInfos;
	}
	
	public List<ApiInfo> findNotPermModuleApis(int moduleId){
		List<ApiInfo> apis = findModuleApis(moduleId);
		List<String> permCodes = resourceMapper.findPermCodeByModule(moduleId);
		
		if(permCodes.isEmpty()){
			return apis;
		}
		Iterator<ApiInfo> iterator = apis.iterator();
		while(iterator.hasNext()){
			ApiInfo apiInfo = iterator.next();
			if(permCodes.contains(apiInfo.getUrl())){
				iterator.remove();
			}
		}
		return apis;
	}


}
