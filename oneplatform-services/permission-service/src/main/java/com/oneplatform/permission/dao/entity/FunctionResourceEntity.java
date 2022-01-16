package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;


@Table(name = "function_resource")
public class FunctionResourceEntity extends StandardBaseEntity {

    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    
    private String type;

    /**
     * 资源名称
     */
    private String name;

    private String code;
    
    private String router;
    
    @Column(name = "view_path")
    private String viewPath;
    
    private String icon;

    @Column(name = "client_type",updatable = false)
    private String clientType;
    
    @Column(name = "is_open_access")
    private Boolean isOpenAccess = Boolean.FALSE;
    
    @Column(name = "is_default")
    private Boolean isDefault = Boolean.FALSE;
    
    @Column(name = "is_display")
    private Boolean isDisplay = Boolean.TRUE;

    /**
     * 排序
     */
    private Integer sort;


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
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

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsOpenAccess() {
		return isOpenAccess;
	}

	public void setIsOpenAccess(Boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
    
}