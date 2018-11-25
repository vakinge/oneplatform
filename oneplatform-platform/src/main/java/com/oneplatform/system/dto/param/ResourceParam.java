package com.oneplatform.system.dto.param;

import org.apache.commons.lang3.StringUtils;

public class ResourceParam {

	private Integer id;
    private Integer parentId;
    private Integer moduleId;
    private String name;
    private String resource;
    private String type;
    private String icon;
    private Integer sort;
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
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getName() {
		return StringUtils.trimToNull(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getResource() {
		return StringUtils.trimToNull(resource);
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getType() {
		return StringUtils.trimToNull(type);
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return StringUtils.trimToNull(icon);
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
    
    
}
