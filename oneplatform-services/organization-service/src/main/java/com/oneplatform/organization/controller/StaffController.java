package com.oneplatform.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.organization.dao.entity.StaffEntity;
import com.oneplatform.organization.dto.Staff;
import com.oneplatform.organization.dto.StaffDetails;
import com.oneplatform.organization.dto.param.StaffParam;
import com.oneplatform.organization.dto.param.StaffQueryParam;
import com.oneplatform.organization.service.StaffService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("staff")
public class StaffController {

	private @Autowired StaffService staffService;

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@GetMapping("{id}")
	public Staff getById(@PathVariable(value = "id") String id) {
		StaffEntity entity = staffService.findById(id);
		return BeanUtils.copy(entity, Staff.class);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "新增")
	@PostMapping(value = "add")
	public IdParam<String> add(@RequestBody @Validated StaffParam param) {

		StaffEntity entity = BeanUtils.copy(param, StaffEntity.class);
		staffService.addStaff(entity);

		return new IdParam<>(entity.getId());
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "更新")
	@PostMapping(value = "update")
	public void update(@RequestBody @Validated StaffParam param) {
		StaffEntity entity = BeanUtils.copy(param, StaffEntity.class);
		staffService.updateStaff(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@ApiOperation(value = "分页查询", notes = "### markdown格式描述信息 \n - xxx")
	@PostMapping("list")
	public Page<StaffDetails> pageQuery(@RequestBody PageQueryRequest<StaffQueryParam> param) {
		if (param.getExample() == null)
			param.setExample(new StaffQueryParam());
		Page<StaffDetails> page = staffService.pageQuery(new PageParams(param.getPageNo(), param.getPageSize()),
				param.getExample());
		return page;
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "切换状态", notes = "### 切换状态 \n - 禁用或启用")
	@PostMapping(value = "toggle")
	public void toggle(@RequestBody IdParam<String> param) {
		StaffEntity entity = staffService.findById(param.getId());
		entity.setEnabled(!entity.getEnabled());
		staffService.updateStaff(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "删除")
	@PostMapping(value = "delete")
	public void delete(@RequestBody IdParam<String> param) {
		StaffEntity entity = staffService.findById(param.getId());
		entity.setDeleted(true);
		staffService.updateStaff(entity);
	}
}
