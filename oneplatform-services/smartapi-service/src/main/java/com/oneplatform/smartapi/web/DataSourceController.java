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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.smartapi.core.staticapi.SmartApiConfigContext;
import com.jeesuite.smartapi.core.staticapi.model.ColumnMetadata;
import com.jeesuite.smartapi.core.staticapi.model.TableMetadata;
import com.jeesuite.smartapi.dao.entity.DatasourceEntity;
import com.jeesuite.smartapi.dto.TableInfo;
import com.jeesuite.smartapi.service.DatasourceService;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.model.SwitchParam;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年10月26日
 */
@RestController
@RequestMapping("/dsconfig")
public class DataSourceController {
	
	@Autowired
	private DatasourceService datasourceService;
	//add
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> add(@RequestBody Map<String, Object> params) {
		DatasourceEntity entity = BeanUtils.mapToBean(params, DatasourceEntity.class);
		datasourceService.add(entity);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> update(@RequestBody Map<String, Object> params) {
		DatasourceEntity entity = BeanUtils.mapToBean(params, DatasourceEntity.class);
		datasourceService.update(entity);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> delete(@PathVariable("id") int id) {
		datasourceService.delete(id);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "switch", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> switchState(@RequestBody SwitchParam param) {
		if(param.getValue()){
			datasourceService.enable(param.getId());
		}else{
			datasourceService.disable(param.getId());
		}
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<List<DatasourceEntity>> list() {
		List<DatasourceEntity> list = datasourceService.findAll(false);
		for (DatasourceEntity entity : list) {
			entity.setConnPassword(null);
			if(StringUtils.isBlank(entity.getIncludeTables())){
				entity.setIncludeTables("*");
			}
		}
		return new WrapperResponse<>(list);
	}
	
	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<DatasourceEntity> get(@PathVariable("id") int id) {
		DatasourceEntity entity = datasourceService.find(id);
		entity.setConnPassword(null);
		return new WrapperResponse<>(entity);
	}
	
	@RequestMapping(value = "tables/{id}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<TableInfo>> listTables(@PathVariable("id") int id) {
		DatasourceEntity entity = datasourceService.find(id);
		List<TableMetadata> tables = SmartApiConfigContext.getTablesNotExistDoCache(entity);
		List<TableInfo> resultData = new ArrayList<>(tables.size());
		TableInfo info;
		for (TableMetadata table : tables) {
			info = new TableInfo();
			info.setTableName(table.getTableName());
			info.setPkColumnNames(new ArrayList<>());
			for (ColumnMetadata c : table.getPkColumns()) {
				info.getPkColumnNames().add(c.getJavaName());
			}
			info.setColumns(table.getColumnMetadatas());
			info.setComments(table.getComments());
			resultData.add(info);
		}
		return new WrapperResponse<>(resultData);
	}
	
	@RequestMapping(value = "options", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> getAllModulesAsSelectOption() {
		List<SelectOption> resultDatas = new ArrayList<>();
		List<DatasourceEntity> list = datasourceService.findAll(true);
		for (DatasourceEntity entity : list) {
			resultDatas.add(new SelectOption(entity.getId().toString(), entity.getName()));
		}
		return new WrapperResponse<>(resultDatas);
	}
	
}
