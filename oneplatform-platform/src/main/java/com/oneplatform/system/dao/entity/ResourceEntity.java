package com.oneplatform.system.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.mybatis.core.BaseEntity;

@Table(name = "sys_resources")
public class ResourceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父ID，顶级为0
     */
    @Column(name = "parent_id",updatable = false)
    private Integer parentId;

    @Column(name = "module_id",updatable = false)
    private Integer moduleId;

    /**
     * 资源名称
     */
    private String name;
    
    @Column(name = "code",updatable = false)
    private String code;

    /**
     * 资源编码(url,按钮编码等)
     */
    @Column(name = "resource",updatable = false)
    private String resource;

    private String type;

    /**
     * 资源图标
     */
    private String icon;

    private Boolean enabled = true;
    
    @Column(name = "is_default",updatable = false)
    private Boolean isDefault;

    /**
     * 排序
     */
    private Integer sort = 99;

    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @Column(name = "created_by",updatable = false)
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;
    
    @Transient
    private String moduleName;

    
    public ResourceEntity() {}
    
	public ResourceEntity(Integer id, Integer parentId, Integer moduleId, String name) {
		this.id = id;
		this.parentId = parentId;
		this.moduleId = moduleId;
		this.name = name;
	}

	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父ID，顶级为0
     *
     * @return parent_id - 父ID，顶级为0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父ID，顶级为0
     *
     * @param parentId 父ID，顶级为0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return module_id
     */
    public Integer getModuleId() {
        return moduleId == null ? 0 : moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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

	public String getResource() {
		return StringUtils.trimToNull(resource);
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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


    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return created_by
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return updated_by
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public boolean hasChildren(){
    	return parentId != null && parentId > 0;
    }
    
    public boolean isLeaf(){
    	return StringUtils.isNotBlank(resource);
    }
}