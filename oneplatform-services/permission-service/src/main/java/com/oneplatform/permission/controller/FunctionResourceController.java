package com.oneplatform.permission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dto.MenuItem;
import com.oneplatform.permission.dto.ResourceItem;
import com.oneplatform.permission.dto.param.FunctionResourceParam;
import com.oneplatform.permission.dto.param.FunctionResourceQueryParam;
import com.oneplatform.permission.service.FunctionResourceService;

import io.swagger.annotations.ApiOperation;


@RestController
public class FunctionResourceController {

    private @Autowired FunctionResourceService FunctionResourceService;

    
    @ApiOperation(value = "新增菜单", notes = "### 新增菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public IdParam<Integer> add(FunctionResourceParam param) {
        return FunctionResourceService.addFunctionResource(param);
    }

    
    @ApiOperation(value = "删除菜单", notes = "### 删除菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public void delete(IdParam<Integer> param) {
        FunctionResourceService.deleteFunctionResource(param.getId());
    }

    
    @ApiOperation(value = "更新菜单", notes = "### 更新菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public void update(FunctionResourceParam param) {
        FunctionResourceService.updateFunctionResource(param);
    }

    
    @ApiOperation(value = "切换菜单激活状态", notes = "### 切换菜单激活状态 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public void switchMenu(IdParam<Integer> param) {
        FunctionResourceService.switchMenu(param.getId());
    }

    
    @ApiOperation(value = "根据id获取菜单", notes = "### 根据id获取菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public MenuItem get(Integer id) {
        FunctionResourceEntity entity = FunctionResourceService.getFunctionResourceById(id);
		return BeanUtils.copy(entity, MenuItem.class);
    }

    
    @ApiOperation(value = "分页查询菜单列表", notes = "### 分页查询菜单列表 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public Page<MenuItem> pageQry(PageQueryRequest<FunctionResourceQueryParam> queryParam) {
        PageParams pageParams = new PageParams(queryParam.getPageNo(),queryParam.getPageSize());
		return FunctionResourceService.pageQryFunctionResource(pageParams,queryParam.getExample());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取菜单权限树")
    @GetMapping(value = "/permissions")
    @ResponseBody
    public List<ResourceItem> menuPermissions(@RequestParam("systemId") Integer systemId) {
        return FunctionResourceService.getSystemMenuTree(systemId,true);
    }

}
