package com.oneplatform.organization.controller;

import java.util.List;

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
import com.jeesuite.common.model.TreeModel;
import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.organization.dao.entity.DepartmentEntity;
import com.oneplatform.organization.dto.Department;
import com.oneplatform.organization.dto.param.DepartmentParam;
import com.oneplatform.organization.dto.param.DepartmentQueryParam;
import com.oneplatform.organization.service.DepartmentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("dept")
public class DepartmentController {

	private @Autowired DepartmentService departmentService;

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@GetMapping("{id}")
	public Department getById(@PathVariable(value = "id") String id) {
		DepartmentEntity entity = departmentService.findById(id);
		return BeanUtils.copy(entity, Department.class);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "新增")
	@PostMapping(value = "add")
	public IdParam<String> add(@RequestBody @Validated DepartmentParam param) {

		DepartmentEntity entity = BeanUtils.copy(param, DepartmentEntity.class);
		departmentService.addDepartment(entity);

		return new IdParam<>(entity.getId());
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "更新")
	@PostMapping(value = "update")
	public void update(@RequestBody @Validated DepartmentParam param) {
		DepartmentEntity entity = BeanUtils.copy(param, DepartmentEntity.class);
		departmentService.updateDepartment(entity);
	}
	
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "切换状态", notes = "### 切换状态 \n - 禁用或启用")
	@PostMapping(value = "toggle")
	public void toggle(@RequestBody IdParam<String> param) {
		DepartmentEntity entity = departmentService.findById(param.getId());
		entity.setEnabled(!entity.getEnabled());
		departmentService.updateDepartment(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired)
	@ApiOperation(value = "删除")
	@PostMapping(value = "delete")
	public void delete(@RequestBody IdParam<String> param) {
		DepartmentEntity entity = departmentService.findById(param.getId());
		entity.setDeleted(true);
		departmentService.updateDepartment(entity);
	}
	
	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = false)
	@ApiOperation(value = "查询部门树")
	@PostMapping(value = "list")
	public List<TreeModel> findDepartmentTree(@RequestBody DepartmentQueryParam param) {
		List<Department> departments = departmentService.findDepartmentList(param);
		return TreeModel.build(departments).getChildren();
	}
	
}
