package com.oneplatform.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.Page;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.system.dao.entity.ActionLogEntity;
import com.oneplatform.system.dao.mapper.ActionLogEntityMapper;
import com.oneplatform.system.dto.param.LogQueryParam;

@RestController
@RequestMapping("actionLog")
public class ActionLogController {

	@Autowired
	private ActionLogEntityMapper actionLogMapper;
	
	@ApiMetadata(IntranetAccessOnly = true,actionLog = false)
	@PostMapping("add")
	public void addActionLog(@RequestBody ActionLogEntity entity) {
		actionLogMapper.insertSelective(entity);
	}
	
	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired,actionLog = false)
	@PostMapping("list")
	public Page<ActionLogEntity> pageQuery(@RequestBody PageQueryRequest<LogQueryParam> param) {
		return PageExecutor.pagination(param.asPageParam(), () -> actionLogMapper.findListByQueryParam(param.getExample()));
	}
}
