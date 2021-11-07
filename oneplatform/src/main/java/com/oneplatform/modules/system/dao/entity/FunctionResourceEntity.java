package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.core.base.StandardBaseEntity;


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
    
    @Column(name = "view_path")
    private String viewPath;

    /**
     * 资源图标
     */
    private String icon;
    
    @Column(name = "client_type",updatable = false)
    private String clientType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 可用状态(0:不可用;1:可用)
     */
    private Boolean enabled;


    /**
     * 获取父ID
     *
     * @return parent_id - 父ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父ID
     *
     * @param parentId 父ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	/**
     * 获取资源图标
     *
     * @return icon - 资源图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置资源图标
     *
     * @param icon 资源图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

	/**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取可用状态(0:不可用;1:可用)
     *
     * @return enabled - 可用状态(0:不可用;1:可用)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置可用状态(0:不可用;1:可用)
     *
     * @param enabled 可用状态(0:不可用;1:可用)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

	protected String getClientType() {
		return clientType;
	}

	protected void setClientType(String clientType) {
		this.clientType = clientType;
	}
    
    
    
}