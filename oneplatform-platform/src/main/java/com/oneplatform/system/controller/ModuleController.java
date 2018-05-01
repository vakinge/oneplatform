package com.oneplatform.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.ApiInfo;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.base.model.SelectOption;
import com.oneplatform.system.dao.entity.ModuleEntity;
import com.oneplatform.system.dto.param.ModuleParam;
import com.oneplatform.system.dto.param.SwitchParam;
import com.oneplatform.system.service.ModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/module")
@Api("Module Management API")
public class ModuleController {

	private @Autowired ModuleService moduleService;
	
	@ApiOperation(value = "查询所有模块")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<ModuleEntity>> getAllModules() {
		List<ModuleEntity> modules = moduleService.findAllModules();
		return new WrapperResponse<>(modules);
	}
	
	@ApiOperation(value = "按id查询")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<ModuleEntity> getById(@PathVariable("id") int id) {
		ModuleEntity entity = moduleService.getmoduleDetails(id);
		return new WrapperResponse<>(entity);
	}
	
	
	@ApiOperation(value = "更新模块")
	@RequestMapping(value = "update", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> updateModule(@RequestBody ModuleParam param) {
		LoginUserInfo loginUser = LoginContext.getLoginUser();
		moduleService.updateModule(loginUser, param);
		
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "启用/停止模块")
	@RequestMapping(value = "switch", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> switchModule(@RequestBody SwitchParam param) {
		LoginUserInfo loginUser = LoginContext.getLoginUser();
		moduleService.switchModule(loginUser, param.getId(),param.getValue());
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "删除模块")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> deleteModule(@PathVariable("id") int id) {
		LoginUserInfo loginUser = LoginContext.getLoginUser();
		moduleService.deleteModule(loginUser, id);
		return new WrapperResponse<>();
	}
    
	
	@ApiOperation(value = "查询所有模块（下拉框选项）")
	@RequestMapping(value = "options", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> getAllModulesAsSelectOption() {
		List<ModuleEntity> modules = moduleService.findAllModules();
		
		List<SelectOption> options = new ArrayList<>();
		for (ModuleEntity moduleEntity : modules) {
			if(!moduleEntity.getEnabled())continue;
			options.add(new SelectOption(moduleEntity.getId().toString(), moduleEntity.getName()));
		}
		return new WrapperResponse<>(options);
	}
	
	@ApiOperation(value = "查询无需鉴权接口列表")
	@RequestMapping(value = "notperm_apis/{moduleId}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<ApiInfo>> getApis(@PathVariable("moduleId") int moduleId) {
		List<ApiInfo> list = moduleService.findNotPermModuleApis(moduleId);
		return new WrapperResponse<>(list);
	}
    
}
