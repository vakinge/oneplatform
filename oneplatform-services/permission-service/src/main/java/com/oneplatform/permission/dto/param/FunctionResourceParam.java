package com.oneplatform.permission.dto.param;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.oneplatform.permission.dto.MenuItem;

import io.swagger.annotations.ApiModelProperty;

public class FunctionResourceParam {

	private Integer id;

	/**
	 * 父ID
	 */
	@ApiModelProperty("父ID")
	private Integer parentId;

	/**
	 * 资源名称
	 */
	@ApiModelProperty(value = "功能资源名称", required = true)
	@NotBlank(message = "功能资源名称不能为空")
	private String name;

	private String code;

	private String type;

	@ApiModelProperty("排序")
	private Integer sort;

	private List<MenuItem> items;
	
	private boolean display = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	

}