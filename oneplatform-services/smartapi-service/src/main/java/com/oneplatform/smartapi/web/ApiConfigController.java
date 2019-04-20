/*
 * Copyright 2016-2018 www.jeesuite.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.smartapi.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.smartapi.core.staticapi.kit.ResultValidator;
import com.jeesuite.smartapi.core.staticapi.variables.SystemVariables;
import com.jeesuite.smartapi.dao.entity.SmartApiConfigEntity;
import com.jeesuite.smartapi.dto.PageResult;
import com.jeesuite.smartapi.dto.SmartApiConfig;
import com.jeesuite.smartapi.dto.params.AddApiParam;
import com.jeesuite.smartapi.dto.params.ApiQueryParam;
import com.jeesuite.smartapi.service.SmartapiConfigService;
import com.jeesuite.springweb.model.WrapperResponse;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.model.SwitchParam;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年10月26日
 */
@RestController
@RequestMapping("/apiconfig")
public class ApiConfigController {
	
	@Autowired
	private SmartapiConfigService apiConfigService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> add(@RequestBody AddApiParam params) {
		apiConfigService.add(params);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> update(@RequestBody Map<String, Object> params) {
		SmartApiConfigEntity entity = BeanUtils.mapToBean(params, SmartApiConfigEntity.class);
		apiConfigService.update(entity);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> delete(@PathVariable("id") int id) {
		apiConfigService.delete(id);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<SmartApiConfig> details(HttpServletRequest request,@PathVariable("id") int id) {
		SmartApiConfig details = apiConfigService.getDetails(id);
		String baseUrl = WebUtils.getBaseUrl(request);
		details.setUri(baseUrl + "/v1" + details.getUri());
		return new WrapperResponse<>(details);
	}
	
	@RequestMapping(value = "switch", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> switchState(@RequestBody SwitchParam param) {
		if(param.getValue()){
			apiConfigService.enable(param.getId());
		}else{
			apiConfigService.disable(param.getId());
		}
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
    public @ResponseBody PageResult<SmartApiConfigEntity> list(@ModelAttribute ApiQueryParam param) {
		PageResult<SmartApiConfigEntity> page = apiConfigService.pageQuery(param,param);
		return page;
	}
	

	@RequestMapping(value = "variablesOptions", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> systemVariables() {
		return new WrapperResponse<>(SystemVariables.getVariableOptions());
	}
	
	@RequestMapping(value = "validatorOptions", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> resultValidatorExprs() {
		return new WrapperResponse<>(ResultValidator.getValidatorExprOptions());
	}
}
