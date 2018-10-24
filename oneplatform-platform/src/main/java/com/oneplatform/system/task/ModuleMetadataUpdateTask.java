/**
 * 
 */
package com.oneplatform.system.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jeesuite.common.util.DateUtils;
import com.jeesuite.scheduler.AbstractJob;
import com.jeesuite.scheduler.JobContext;
import com.jeesuite.scheduler.annotation.ScheduleConf;
import com.jeesuite.spring.ApplicationStartedListener;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.oneplatform.base.GlobalContants;
import com.oneplatform.base.model.ModuleMetadata;
import com.oneplatform.platform.zuul.CustomRouteLocator;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.submodel.ServiceInstance;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年11月7日
 */
@Component
@ScheduleConf(cronExpr="0 0/5 * * * ?",jobName="moduleMetadataUpdateTask",executeOnStarted = false)
public class ModuleMetadataUpdateTask extends AbstractJob implements ApplicationStartedListener{
	
	private final static Logger logger = LoggerFactory.getLogger("com.oneplatform.system.task");
	
	private @Autowired ModuleEntityMapper moduleMapper;
	private @Autowired(required=false) CustomRouteLocator routeLocator; 
	private @Autowired RestTemplate restTemplate;
	private @Autowired(required=false) EurekaClient eurekaClient;
	
	private static Map<String, ModuleEntity> activeModulesCache = new HashMap<>();
	
	
	public static Map<String, ModuleEntity> getActiveModules() {
		return activeModulesCache;
	}

	@Override
	public void doJob(JobContext context) throws Exception {
		updateModules();
	}

	@Override
	public boolean parallelEnabled() {
		return true;
	}
	
	private void updateModules(){
		//数据库已经存在的模块
		Map<String, ModuleEntity> hisModules = moduleMapper.findAll().stream().collect(Collectors.toMap((ModuleEntity::getServiceId), module -> module));
        //
		Map<String, ModuleEntity> activeModules = getActiveModulesFromEureka();
		
		boolean refreshRequired = false;
		
		ModuleEntity moduleEntity;
		ModuleMetadata metadata;
		for (String serviceId : activeModules.keySet()) {
			if(!hisModules.containsKey(serviceId)){
				if(GlobalContants.MODULE_NAME.equalsIgnoreCase(serviceId))continue;
				metadata = fetchModuleMetadata(serviceId);
				if(metadata == null)continue;
				moduleEntity = activeModules.get(serviceId);
				moduleEntity.setName(metadata.getName());
				moduleEntity.setRouteName(metadata.getRoutePath());
				moduleEntity.setServiceId(serviceId);
				moduleEntity.setApidocUrl(String.format("/api/%s/swagger-ui.html", moduleEntity.getRouteName()));
				moduleEntity.setEnabled(true);
				moduleEntity.setCreatedAt(new Date());
				moduleEntity.setMetadata(metadata);
				moduleMapper.insertSelective(moduleEntity);
				//
				activeModulesCache.put(serviceId, moduleEntity);
				refreshRequired = true;
			}
		}
		
		
		for (String serviceId : hisModules.keySet()) {
			moduleEntity = hisModules.get(serviceId);
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase(serviceId)){
				if(!activeModulesCache.containsKey(serviceId)){
					activeModulesCache.put(serviceId, moduleEntity);
				}
				continue;
			}
			if(activeModules.containsKey(serviceId)){
				activeModulesCache.put(serviceId, moduleEntity);
				moduleEntity.setServiceInstances(activeModules.get(serviceId).getServiceInstances());
				if(DateUtils.getDiffMinutes(new Date(), moduleEntity.getFetchMetaDataTime()) > 15){					
					moduleEntity.setMetadata(fetchModuleMetadata(serviceId));
					moduleEntity.setFetchMetaDataTime(new Date());
				}
				continue;
			}
			activeModulesCache.remove(serviceId);
			moduleEntity.setEnabled(false);
			moduleMapper.updateByPrimaryKey(moduleEntity);
		}
		
		if(refreshRequired){
			routeLocator.refresh();
		}


	}
	
	
    private Map<String, ModuleEntity> getActiveModulesFromEureka(){
    	
    	Map<String, ModuleEntity> result = new HashMap<>();
    	List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
    	if(applications == null)return result;
    	ModuleEntity module;
    	for (Application application : applications) {
    		String serviceId = application.getName().toUpperCase();
			module = result.get(serviceId);
    		if(module == null){
    			module = new ModuleEntity();
    			module.setServiceId(serviceId);
    			result.put(serviceId, module);
    		}
    		
    		for (InstanceInfo instanceInfo : application.getInstances()) {
    			module.getServiceInstances().add(new ServiceInstance(instanceInfo));
			}
    		
		}
    	
    	return result;
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
    
    private ModuleMetadata fetchModuleMetadata(String serviceId){
       try {
    	   
			ParameterizedTypeReference<ModuleMetadata> arearesponseType = new ParameterizedTypeReference<ModuleMetadata>() {};
			ModuleMetadata metadata = restTemplate.exchange("http://"+serviceId.toUpperCase()+"/metadata", HttpMethod.GET, null, arearesponseType).getBody();
			return metadata;
		} catch (Exception e) {
			return null;
		}
    }

	@Override
	public void onApplicationStarted(ApplicationContext applicationContext) {
		updateModules();
	}

}
