package com.oneplatform.permission.dto.param;

import javax.validation.constraints.NotBlank;

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

	private String viewPath;

	private String icon;

	@ApiModelProperty("排序")
	private Integer sort;

	private boolean display = true;
	private boolean openAccess = Boolean.FALSE;
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
	public String getViewPath() {
		return viewPath;
	}
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public boolean isOpenAccess() {
		return openAccess;
	}
	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	

}