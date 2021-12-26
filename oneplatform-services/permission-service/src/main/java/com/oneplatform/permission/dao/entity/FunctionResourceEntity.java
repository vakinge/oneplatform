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

    @Column(name = "sub_type")
    private String subType;
    
    /**
     * 资源名称
     */
    private String name;

    private String code;
    
    @Column(name = "item_content")
    private String itemContent;

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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
    
    
}