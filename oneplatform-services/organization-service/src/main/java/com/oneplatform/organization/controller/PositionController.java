package com.oneplatform.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.organization.dao.entity.PositionEntity;
import com.oneplatform.organization.dto.Position;
import com.oneplatform.organization.dto.param.PositionParam;
import com.oneplatform.organization.dto.param.PositionQueryParam;
import com.oneplatform.organization.service.PositionService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * <br>
 * Class Name   : PositionController
 *
 */
@RestController
public class PositionController {

	private @Autowired PositionService positionService;

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@GetMapping("{id}")
	public Position getById(@PathVariable(value = "id") String id) {
		PositionEntity entity = positionService.findById(id);
		return BeanUtils.copy(entity, Position.class);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "新增")
	@PostMapping(value = "add")
	public IdParam<String> add(@RequestBody @Validated PositionParam param) {

		PositionEntity entity = BeanUtils.copy(param, PositionEntity.class);
		positionService.addPosition(entity);

		return new IdParam<>(entity.getId());
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "更新")
	@PostMapping(value = "update")
	public void update(@RequestBody @Validated PositionParam param) {
		PositionEntity entity = BeanUtils.copy(param, PositionEntity.class);
		positionService.updatePosition(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@ApiOperation(value = "分页查询", notes = "### markdown格式描述信息 \n - xxx")
	@PostMapping("list")
	public Page<Position> pageQuery(@RequestBody PageQueryRequest<PositionQueryParam> param) {
		if (param.getExample() == null)
			param.setExample(new PositionQueryParam());
		Page<Position> page = positionService.pageQuery(new PageParams(param.getPageNo(), param.getPageSize()),
				param.getExample());
		return page;
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "切换状态", notes = "### 切换状态 \n - 禁用或启用")
	@PostMapping(value = "toggle")
	public void toggle(@RequestBody IdParam<String> param) {
		PositionEntity entity = positionService.findById(param.getId());
		entity.setEnabled(!entity.getEnabled());
		positionService.updatePosition(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "删除")
	@PostMapping(value = "delete")
	public void delete(@RequestBody IdParam<String> param) {
		PositionEntity entity = positionService.findById(param.getId());
		entity.setDeleted(true);
		positionService.updatePosition(entity);
	}

}
