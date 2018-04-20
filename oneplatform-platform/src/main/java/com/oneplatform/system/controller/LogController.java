package com.oneplatform.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oneplatform.base.model.PageResult;
import com.oneplatform.system.dao.entity.SysLogEntity;
import com.oneplatform.system.dto.param.LogQueryParam;
import com.oneplatform.system.service.SysMonitorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/log")
@Api("Log Management API")
public class LogController {

	private @Autowired SysMonitorService sysMonitorService;
	@ApiOperation(value = "分页查询日志列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
    public @ResponseBody PageResult<SysLogEntity> pageQueryAccounts(@ModelAttribute LogQueryParam param) {
		PageResult<SysLogEntity> page = sysMonitorService.pageQuery(param, param);
		return page;
	}
}
