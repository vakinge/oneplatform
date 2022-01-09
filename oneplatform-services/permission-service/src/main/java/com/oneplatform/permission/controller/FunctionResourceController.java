package com.oneplatform.permission.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.TreeModel;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.JsonUtils;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dto.FunctionResource;
import com.oneplatform.permission.dto.MenuItem;
import com.oneplatform.permission.dto.ResourceTreeModel;
import com.oneplatform.permission.dto.param.FunctionResourceParam;
import com.oneplatform.permission.service.FunctionResourceService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/functionResource")
public class FunctionResourceController {

    private @Autowired FunctionResourceService functionResourceService;

    
    @ApiOperation(value = "新增菜单", notes = "### 新增菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @PostMapping("add")
    public IdParam<Integer> add(@RequestBody FunctionResourceParam param) {
        return functionResourceService.addFunctionResource(param);
    }

    
    @ApiOperation(value = "删除菜单", notes = "### 删除菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @PostMapping("delete")
    public void delete(@RequestBody IdParam<Integer> param) {
        functionResourceService.deleteFunctionResource(param.getId());
    }

    
    @ApiOperation(value = "更新菜单", notes = "### 更新菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    public void update(@RequestBody FunctionResourceParam param) {
        functionResourceService.updateFunctionResource(param);
    }

    
    @ApiOperation(value = "切换菜单激活状态", notes = "### 切换菜单激活状态 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @PostMapping("toggle")
    public void switchMenu(@RequestBody IdParam<Integer> param) {
        functionResourceService.switchMenu(param.getId());
    }

    
    @ApiOperation(value = "根据id获取菜单", notes = "### 根据id获取菜单 \n -xxx")
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired, actionLog = true)
    @GetMapping("{id}")
    public FunctionResource get(@PathVariable("id") Integer id) {
        FunctionResourceEntity entity = functionResourceService.getFunctionResourceById(id);
        FunctionResource model = BeanUtils.copy(entity, FunctionResource.class);
    	if(StringUtils.isNotBlank(entity.getItemContent())) {
    		model.setItems(JsonUtils.toList(entity.getItemContent(), MenuItem.class));
    	}
		return model;
    }

 
    
    @ApiMetadata(permissionLevel = PermissionLevel.LoginRequired)
    @ApiOperation(value = "获取菜单权限树")
    @GetMapping(value = "/tree")
    @ResponseBody
    public List<TreeModel> menuPermissions(@RequestParam(value = "withButton",required = false,defaultValue="true") boolean withButton) {
        List<ResourceTreeModel> resources = functionResourceService.getSystemMenuTree(withButton);  
		return TreeModel.build(resources).getChildren();
    }
    

}
