package com.oneplatform.cms.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.annotation.ApiPermOptions;
import com.oneplatform.base.constants.PermissionType;
import com.oneplatform.base.model.IdParam;
import com.oneplatform.base.model.SelectOptGroup;
import com.oneplatform.base.model.TreeModel;
import com.oneplatform.cms.dao.entity.CategoryEntity;
import com.oneplatform.cms.dto.param.CategoryParam;
import com.oneplatform.cms.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/article/category")

public class CategoryController {

	private @Autowired CategoryService categoryService;
	
	@ApiOperation(value = "查询文章分类树")
	@RequestMapping(value = "tree", method = RequestMethod.GET)
	@ApiPermOptions(perms = PermissionType.Anonymous)
    public @ResponseBody WrapperResponse<List<TreeModel>> getPermissionGroups() {
		List<TreeModel> list = categoryService.findAllCategories().stream().map(e -> {
			return new TreeModel(e.getId(), e.getName(), e.getId().toString(), null, e.getPid(), e.getPid() != null && e.getPid() > 0);
		}).collect(Collectors.toList());
		return new WrapperResponse<>(TreeModel.build(list).getChildren());
	}
	
	@ApiOperation(value = "查询文章分类下拉框选项")
	@RequestMapping(value = "options", method = RequestMethod.GET)
	@ApiPermOptions(perms = PermissionType.Anonymous)
    public @ResponseBody WrapperResponse<List<SelectOptGroup>> findOptions() {
		List<CategoryEntity> categories = categoryService.findAllCategories();
		
		List<SelectOptGroup> optGroups = categories.stream().filter(e -> e.getPid() == null).map(e -> {
			return new SelectOptGroup(e.getName(),e.getId().toString());
		}).collect(Collectors.toList());
		
		Map<String, SelectOptGroup> idRGroupMap = optGroups.stream().collect(Collectors.toMap(SelectOptGroup::getValue, e -> e));
		
        for (CategoryEntity category : categories) {
			if(category.getPid() == null)continue;
			idRGroupMap.get(category.getPid().toString()).getOptions().add(new SelectOption(category.getId().toString(), category.getName()));
		}
		
		return new WrapperResponse<>(optGroups);
	}
	
	@ApiOperation(value = "新增文章分类")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ApiPermOptions(perms = PermissionType.Authorized)
    public @ResponseBody WrapperResponse<String> addCmsArticle(@RequestBody CategoryParam param) {
	
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "更新文章分类")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ApiPermOptions(perms = PermissionType.Authorized)
    public @ResponseBody WrapperResponse<String> updateCmsArticle(@RequestBody CategoryParam param) {
	
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "删除文章分类")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ApiPermOptions(perms = PermissionType.Authorized)
    public @ResponseBody WrapperResponse<String> deleteCmsArticle(@RequestBody IdParam param) {
		return new WrapperResponse<>();
	}
}
