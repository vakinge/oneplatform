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
package com.oneplatform.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.oneplatform.base.model.PageQueryParam;
import com.oneplatform.base.model.PageResult;
import com.oneplatform.system.dao.entity.SysLogEntity;
import com.oneplatform.system.dao.mapper.SysLogEntityMapper;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月24日
 */
@Service
public class SysMonitorService {

	private @Autowired SysLogEntityMapper logMapper;
	
public PageResult<SysLogEntity> pageQuery(PageParams pageParam,PageQueryParam param){
		
		Page<SysLogEntity> page = logMapper.pageQuery(pageParam, param.getConditions());
		return new PageResult<SysLogEntity>(page.getPageNo(), page.getPageSize(), page.getTotal(), page.getData());
	}
}
