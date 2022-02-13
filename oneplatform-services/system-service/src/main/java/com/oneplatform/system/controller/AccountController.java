package com.oneplatform.system.controller;

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
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dto.Account;
import com.oneplatform.system.dto.param.AccountParam;
import com.oneplatform.system.dto.param.AccountQueryParam;
import com.oneplatform.system.service.AccountService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * <br>
 * Class Name   : AccountController
 *
 */
@RestController
@RequestMapping("account")
public class AccountController {

	private @Autowired AccountService accountService;

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@GetMapping("{id}")
	public Account getById(@PathVariable(value = "id") String id) {
		AccountEntity entity = accountService.findById(id);
		return BeanUtils.copy(entity, Account.class);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "新增")
	@PostMapping(value = "add")
	public IdParam<String> add(@RequestBody @Validated AccountParam param) {

		AccountEntity entity = BeanUtils.copy(param, AccountEntity.class);
		accountService.addAccount(entity);

		return new IdParam<>(entity.getId());
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "更新")
	@PostMapping(value = "update")
	public void update(@RequestBody @Validated AccountParam param) {
		AccountEntity entity = BeanUtils.copy(param, AccountEntity.class);
		accountService.updateAccount(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
	@ApiOperation(value = "分页查询", notes = "### markdown格式描述信息 \n - xxx")
	@PostMapping("list")
	public Page<Account> pageQuery(@RequestBody PageQueryRequest<AccountQueryParam> param) {
		if (param.getExample() == null)
			param.setExample(new AccountQueryParam());
		Page<Account> page = accountService.pageQuery(new PageParams(param.getPageNo(), param.getPageSize()),
				param.getExample());
		return page;
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "切换状态", notes = "### 切换状态 \n - 禁用或启用")
	@PostMapping(value = "toggle")
	public void toggle(@RequestBody IdParam<String> param) {
		AccountEntity entity = accountService.findById(param.getId());
		entity.setEnabled(!entity.getEnabled());
		accountService.updateAccount(entity);
	}

	@ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
	@ApiOperation(value = "删除")
	@PostMapping(value = "delete")
	public void delete(@RequestBody IdParam<String> param) {
		AccountEntity entity = accountService.findById(param.getId());
		entity.setDeleted(true);
		accountService.updateAccount(entity);
	}
	
}
