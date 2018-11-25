/**
 * 
 */
package com.oneplatform.platform.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.oneplatform.base.GlobalContants.ModuleType;
import com.oneplatform.base.model.Menu;
import com.oneplatform.base.model.ModuleMetadata;
import com.oneplatform.base.util.ModuleMetadataHolder;
import com.oneplatform.platform.zuul.CustomRouteLocator;
import com.oneplatform.system.constants.ResourceType;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dao.entity.ResourceEntity;
import com.oneplatform.system.dao.entity.submodel.ServiceInstance;
import com.oneplatform.system.dao.mapper.ModuleEntityMapper;
import com.oneplatform.system.dao.mapper.ResourceEntityMapper;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年11月7日
 */
@Component
@ScheduleConf(cronExpr="0 0/1 * * * ?",jobName="moduleMetadataUpdateTask",executeOnStarted = false)
public class ModuleMetadataUpdateTask extends AbstractJob implements ApplicationStartedListener{
	
	private final static Logger logger = LoggerFactory.getLogger("com.oneplatform.system.task");
	
	private @Autowired ModuleEntityMapper moduleMapper;
	private @Autowired ResourceEntityMapper resourceMapper;
	private @Autowired(required=false) CustomRouteLocator routeLocator; 
	private @Autowired RestTemplate restTemplate;
	private @Autowired(required=false) EurekaClient eurekaClient;
	//已经入库的模块信息
	Map<String, ModuleEntity> historyModules;
	//注册中心当前可用的模块信息
	private static Map<String, ModuleEntity> activeModulesCache = new HashMap<>();
	
	
	public static Map<String, ModuleEntity> getActiveModules() {
		return activeModulesCache;
	}

	@Override
	public void doJob(JobContext context) throws Exception {
		updateModulesFromEureka();
	}

	@Override
	public boolean parallelEnabled() {
		return true;
	}
	
	private void updateModulesFromEureka(){
		if(historyModules == null)return;
        //
		Map<String, ModuleEntity> activeModules = getActiveModulesFromEureka();
		
		boolean refreshRequired = false;
		
		ModuleEntity moduleEntity;
		ModuleMetadata metadata;
		for (String serviceId : activeModules.keySet()) {
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase(serviceId))continue;
			if(!historyModules.containsKey(serviceId)){
				metadata = fetchModuleMetadata(serviceId);
				if(metadata == null)continue;
				moduleEntity = activeModules.get(serviceId);
				moduleEntity.setName(metadata.getName());
				if(StringUtils.isNotBlank(metadata.getIdentifier())){
					moduleEntity.setRouteName(metadata.getRouteName());
					moduleEntity.setInternal(false);
				}else{
					moduleEntity.setInternal(true);
				}
				
				moduleEntity.setServiceId(serviceId);
				moduleEntity.setModuleType(metadata.getType());
				moduleEntity.setApidocUrl(String.format("/api/%s/swagger-ui.html", moduleEntity.getRouteName()));
				moduleEntity.setEnabled(true);
				moduleEntity.setCreatedAt(new Date());
				moduleEntity.setMetadata(metadata);
				moduleMapper.insertSelective(moduleEntity);
				//
				historyModules.put(serviceId, moduleEntity);
				activeModulesCache.put(serviceId, moduleEntity);
				//创建菜单
				createModuleMenusIfNotExist(moduleEntity);
				if(!refreshRequired)refreshRequired = !moduleEntity.getInternal();
			}
		}
		
		
		for (String serviceId : historyModules.keySet()) {
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase(serviceId))continue;
			moduleEntity = historyModules.get(serviceId);
			if(ModuleType.plugin.name().equals(moduleEntity.getModuleType()))continue;
			if(activeModules.containsKey(serviceId)){
				activeModulesCache.put(serviceId, moduleEntity);
				moduleEntity.setServiceInstances(activeModules.get(serviceId).getServiceInstances());
				if(moduleEntity.getFetchMetaDataTime() == null || DateUtils.getDiffMinutes(new Date(), moduleEntity.getFetchMetaDataTime()) > 15){					
					moduleEntity.setMetadata(fetchModuleMetadata(serviceId));
					moduleEntity.setFetchMetaDataTime(new Date());
					//创建菜单
					createModuleMenusIfNotExist(moduleEntity);
				}
				continue;
			}
			activeModulesCache.remove(serviceId);
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
    
    private void createModuleMenusIfNotExist(ModuleEntity moduleEntity){
    	ModuleMetadata metadata = moduleEntity.getMetadata();
    	if(metadata.getMenus() == null || metadata.getMenus().isEmpty())return;
    	ResourceEntity parent = resourceMapper.findModuleParent(moduleEntity.getId());
    	if(parent == null){
    		parent = new ResourceEntity();
    		parent.setModuleId(moduleEntity.getId());
    		parent.setName(metadata.getName());
    		parent.setIcon(metadata.getMenuIcon());
    		parent.setType(ResourceType.menu.name());
    		parent.setParentId(0);
    		parent.setEnabled(true);
    		parent.setCreatedAt(new Date());
    		resourceMapper.insertSelective(parent);
    	}
    	ResourceEntity child;
		for (Menu menu : metadata.getMenus()) {
			
			child = resourceMapper.findByModuleAndCode(moduleEntity.getId(), menu.getKey());
			if(child == null){
				child = new ResourceEntity();
				child.setModuleId(moduleEntity.getId());
				child.setName(menu.getText());
				child.setCode(menu.getKey());
				child.setResource(menu.getUri());
				child.setIcon(menu.getIcon());
				child.setType(ResourceType.menu.name());
				child.setParentId(parent.getId());
				parent.setSort(child.getSort());
				child.setEnabled(true);
				child.setCreatedAt(new Date());
				resourceMapper.insertSelective(child);
			}
		}
    }

	@Override
	public void onApplicationStarted(ApplicationContext applicationContext) {
		
		historyModules = moduleMapper.findAll().stream().collect(Collectors.toMap((ModuleEntity::getServiceId), module -> module));
		ModuleEntity platform = historyModules.get(GlobalContants.MODULE_NAME.toUpperCase());
		activeModulesCache.put(platform.getServiceId(), platform);
		
		List<ModuleMetadata> metadatas = ModuleMetadataHolder.getMetadatas();
		ModuleEntity moduleEntity;
		for (ModuleMetadata metadata : metadatas) {
			if(metadata.getIdentifier().equalsIgnoreCase(GlobalContants.MODULE_NAME)){
				platform.setMetadata(metadata);
			}else{
				moduleEntity = moduleMapper.findByServiceId(metadata.getIdentifier().toUpperCase());
				if(moduleEntity == null){
					moduleEntity = new ModuleEntity();
					moduleEntity.setModuleType(metadata.getName());
					moduleEntity.setName(metadata.getName());
					moduleEntity.setServiceId(metadata.getIdentifier());
					moduleEntity.setRouteName(metadata.getRouteName());
					moduleEntity.setModuleType(metadata.getType());
					moduleMapper.insertSelective(moduleEntity);
				}
				moduleEntity.setMetadata(metadata);
				//
				createModuleMenusIfNotExist(moduleEntity);
				activeModulesCache.put(moduleEntity.getServiceId(), moduleEntity);
			}
		}
		//
		updateModulesFromEureka();
	}

}
