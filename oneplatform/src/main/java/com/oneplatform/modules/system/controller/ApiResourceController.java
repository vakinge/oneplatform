package com.oneplatform.modules.system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.model.SelectOption;
import com.jeesuite.springweb.annotation.ApiMetadata;
import com.jeesuite.springweb.model.PageQueryRequest;
import com.oneplatform.modules.system.dao.entity.ApiResourceEntity;
import com.oneplatform.modules.system.dto.ApiResource;
import com.oneplatform.modules.system.dto.param.ApiResourceParam;
import com.oneplatform.modules.system.dto.param.ApiResourceQueryParam;
import com.oneplatform.modules.system.service.ApiResourceService;

import io.swagger.annotations.ApiOperation;


@RestController("AppApiResourceController")
@RequestMapping("/apiResource")
public class ApiResourceController {

    private @Autowired ApiResourceService apiResourceService;

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "新增接口",notes = "### 新增接口 \n - xxx")
    @PostMapping(value = "add")
    @ResponseBody
    public IdParam<Integer> add(ApiResourceParam param) {
        return apiResourceService.addApiResource(param);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "删除接口",notes = "### 删除接口 \n - xxx")
    @PostMapping(value = "delete")
    @ResponseBody
    public void delete(IdParam<Integer> param) {
        apiResourceService.deleteApiResource(param.getId());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "更新接口信息",notes = "### 更新接口信息 \n - xxx")
    @PostMapping("update")
    @ResponseBody
    public void update(ApiResourceParam param) {
        apiResourceService.updateApiResource(param);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "接口激活状态变更",notes = "### 接口激活状态变更 \n - xxx")
    @PostMapping("toggle")
    @ResponseBody
    public void toggleApi(IdParam<Integer> param) {
        apiResourceService.switchApiResource(param.getId());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "根据id获取接口",notes = "### 根据id获取接口 \n - xxx")
    @GetMapping(value = "{id}")
    @ResponseBody
    public ApiResource get(Integer id) {
        return apiResourceService.getApiResourceById(id);
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "分页查询接口列表",notes = "### 分页查询接口列表 \n - xxx")
    @PostMapping("list")
    @ResponseBody
    public Page<ApiResource> pageQry(PageQueryRequest<ApiResourceQueryParam> queryParam) {
        return apiResourceService.pageQry(new PageParams(queryParam.getPageNo(),queryParam.getPageSize()),queryParam.getExample());
    }

    
    @ApiMetadata(permissionLevel = PermissionLevel.PermissionRequired,actionLog = true)
    @ApiOperation(value = "获取接口下拉列表",notes = "### 获取接口下拉列表 \n - xxx")
    @GetMapping("options")
    @ResponseBody
    public List<SelectOption> options(@RequestParam(name="moduleId",required=false) Integer moduleId) {
    	List<ApiResourceEntity> list = apiResourceService.findByModuleId(moduleId);
    	return list.stream().map(e -> {
            return new SelectOption(e.getId().toString(),e.getName());
        }).collect(Collectors.toList());
    }


}
