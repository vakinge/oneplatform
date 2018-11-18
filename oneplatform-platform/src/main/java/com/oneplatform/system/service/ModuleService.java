package com.oneplatform.system.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.oneplatform.base.GlobalContants.ModuleType;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.ApiInfo;
import com.oneplatform.platform.task.ModuleMetadataUpdateTask;
import com.oneplatform.system.constants.ResourceType;
import com.oneplatform.system.dao.entity.ModuleEntity;
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
	
	
	public List<ModuleEntity> findActiveModules(){
		return ModuleMetadataUpdateTask.getActiveModules().values()
		        .stream()
		        .collect(Collectors.toList());
	}
	
	public List<ModuleEntity> findActiveServiceModules(){
		return ModuleMetadataUpdateTask.getActiveModules().values()
		        .stream()
		        .filter( e -> ModuleType.service.name().equals(e.getModuleType()))
		        .collect(Collectors.toList());
	}
	
	public ModuleEntity getmoduleDetails(int moduleId){
		Optional<ModuleEntity> optional = ModuleMetadataUpdateTask.getActiveModules().values()
				.stream()
				.filter(m -> (m.getId().intValue() == moduleId))
				.findFirst();
		if(!optional.isPresent())throw new JeesuiteBaseException(ExceptionCode.RECORD_NOT_EXIST.code, "模块不存在或者未运行");
    	return optional.get();
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
    	Optional<ModuleEntity> optional = ModuleMetadataUpdateTask.getActiveModules().values().stream().filter(m -> (m.getId().intValue() == id)).findFirst();
		if(optional.isPresent()){
    		throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "该模块在运行中不允许删除");
        }
    	moduleMapper.deleteByPrimaryKey(id);
    }
    
    public Map<Integer, ModuleEntity> getAllModules(){
    	List<ModuleEntity> modules = moduleMapper.findAll();
    	//ModuleMetadataUpdateTask.getActiveModules().values().stream().collect(Collectors.toList());
		
		Map<Integer, ModuleEntity> moduleMaps = new HashMap<>();
		for (ModuleEntity moduleEntity : modules) {
			moduleMaps.put(moduleEntity.getId(), moduleEntity);
		}
		return moduleMaps;
    }


	public List<ApiInfo> findModuleApis(int moduleId){
		Optional<ModuleEntity> optional = ModuleMetadataUpdateTask.getActiveModules().values().stream().filter(m -> (m.getId().intValue() == moduleId)).findFirst();
		if(!optional.isPresent())throw new JeesuiteBaseException(ExceptionCode.RECORD_NOT_EXIST.code, "模块不存在或者未运行");
		return optional.get().getMetadata().getApis();
	}
	
	public List<ApiInfo> findNotPermModuleApis(int moduleId){
		List<ApiInfo> apis = findModuleApis(moduleId);
		List<String> permCodes = resourceMapper.findResourceFieldByModule(moduleId,ResourceType.uri.name());
		
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
