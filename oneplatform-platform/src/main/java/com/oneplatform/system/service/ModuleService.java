package com.oneplatform.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.BeanCopyUtils;
import com.jeesuite.common.util.DateUtils;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.model.ApiInfo;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.base.util.ApiInfoHolder;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.submodel.ServiceInstance;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
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
	
	private @Autowired RestTemplate restTemplate;
	
	private @Autowired EurekaClient eurekaClient;
	
	public List<ModuleEntity> findAllModules(){
		List<ModuleEntity> list = moduleMapper.findAll();
		if(!list.isEmpty()){
			Map<String, List<ServiceInstance>> instances = getAllInstanceFromEureka();
			for (ModuleEntity module : list) {
				module.setServiceInstances(instances.get(module.getServiceId().toUpperCase()));
			}
		}
		return list;
	}
	
	public ModuleEntity getmoduleDetails(int id){
		ModuleEntity entity = moduleMapper.selectByPrimaryKey(id);
    	AssertUtil.notNull(entity);
    	getInstanceFromEureka(entity);
    	return entity;
	}
	
	public void addModule(LoginUserInfo loginUser,ModuleParam param){
		
		AssertUtil.isNull(moduleMapper.findByServiceId(param.getServiceId()), String.format("ServiceId[%s]已存在", param.getServiceId()));
		AssertUtil.isNull(moduleMapper.findByServiceId(param.getRouteName()), String.format("RouteName[%s]已存在", param.getRouteName()));
		
		ModuleEntity entity = BeanCopyUtils.copy(param, ModuleEntity.class);
		entity.setCreatedAt(new Date());
		entity.setCreatedBy(loginUser.getId());
		
		moduleMapper.insertSelective(entity);
	}
	
    public void updateModule(LoginUserInfo loginUser,ModuleParam param){
    	ModuleEntity entity = moduleMapper.selectByPrimaryKey(param.getId());
    	AssertUtil.notNull(entity);
    	
    	entity.setName(param.getName());
    	entity.setApidocUrl(param.getApidocUrl());
    	entity.setCorsUris(param.getCorsUris());
    	entity.setInternal(param.isInternal());
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(loginUser.getId());
    	
    	moduleMapper.updateByPrimaryKeySelective(entity);
    	
	}
    
    public void switchModule(LoginUserInfo loginUser,Integer id,boolean enable){
    	ModuleEntity entity = moduleMapper.selectByPrimaryKey(id);
    	AssertUtil.notNull(entity);
    	if(entity.getEnabled() == enable)return;
    	entity.setEnabled(enable);
    	
    	entity.setUpdatedAt(new Date());
    	entity.setUpdatedBy(loginUser.getId());
    	
    	moduleMapper.updateByPrimaryKeySelective(entity);
    	
    }
    
    public void deleteModule(LoginUserInfo loginUser,int id){
    	moduleMapper.deleteByPrimaryKey(id);
    }
    
    public Map<Integer, ModuleEntity> getAllEnabledModules(){
    	List<ModuleEntity> modules = moduleMapper.findAllEnabled();
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
		if(moduleId == 0)return ApiInfoHolder.getApiInfos();
		ModuleEntity module = moduleMapper.selectByPrimaryKey(moduleId);
		List<ApiInfo> apiInfos = null;
		try {
			ParameterizedTypeReference<List<ApiInfo>> arearesponseType = new ParameterizedTypeReference<List<ApiInfo>>() {};
			apiInfos = restTemplate.exchange("http://"+module.getServiceId().toUpperCase()+"/getApis", HttpMethod.GET, null, arearesponseType).getBody();
			if(StringUtils.isBlank(module.getApiInfos()) || DateUtils.getDiffMinutes(module.getUpdatedAt(), new Date()) > 120){				
				module.setApiInfos(JsonUtils.toJson(apiInfos));
				module.setUpdatedAt(new Date());
				moduleMapper.updateByPrimaryKey(module);
			}
		} catch (Exception e) {
			apiInfos = JsonUtils.toList(module.getApiInfos(), ApiInfo.class);
		}
		
		return apiInfos;
	}


}
