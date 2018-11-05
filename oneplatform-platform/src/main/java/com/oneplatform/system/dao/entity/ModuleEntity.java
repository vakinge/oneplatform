package com.oneplatform.system.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.mybatis.core.BaseEntity;
import com.oneplatform.base.model.ModuleMetadata;
import com.oneplatform.system.dao.entity.submodel.ServiceInstance;

@Table(name = "sys_module")
public class ModuleEntity extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "service_id",updatable = false)
    private String serviceId;

    @Column(name = "route_name")
    private String routeName;

    /**
     * 可跨域uri集合(多个;隔开)
     */
    @Column(name = "cors_uris")
    private String corsUris;
    
    @Column(name = "apidoc_url")
    private String apidocUrl;
    
    @Column(name = "module_type",updatable = false)
    private String moduleType;

    /**
     * 是否内部服务模块
     */
    private Boolean internal;

    private Boolean enabled = true;

    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @Column(name = "created_by",updatable = false)
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Transient
    private List<ServiceInstance> serviceInstances;
    
    @Transient
    private ModuleMetadata metadata;
    
    @Transient
    private Date fetchMetaDataTime;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return service_id
     */
    public String getServiceId() {
        return StringUtils.isBlank(serviceId) ? serviceId : serviceId.toUpperCase();
    }

    /**
     * @param serviceId
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return route_name
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * @param routeName
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * 获取可跨域uri集合(多个;隔开)
     *
     * @return cors_uris - 可跨域uri集合(多个;隔开)
     */
    public String getCorsUris() {
        return corsUris;
    }

    /**
     * 设置可跨域uri集合(多个;隔开)
     *
     * @param corsUris 可跨域uri集合(多个;隔开)
     */
    public void setCorsUris(String corsUris) {
        this.corsUris = corsUris;
    }

    /**
     * 获取是否内部服务模块
     *
     * @return internal - 是否内部服务模块
     */
    public Boolean getInternal() {
        return internal;
    }

    /**
     * 设置是否内部服务模块
     *
     * @param internal 是否内部服务模块
     */
    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    /**
     * @return enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

	public String getApidocUrl() {
		return apidocUrl;
	}

	public void setApidocUrl(String apidocUrl) {
		this.apidocUrl = apidocUrl;
	}
	
	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public int getInstanceNums() {
		return serviceInstances == null ? 0 : serviceInstances.size();
	}

	public List<ServiceInstance> getServiceInstances() {
		return serviceInstances == null ? (serviceInstances = new ArrayList<>()) : serviceInstances;
	}

	public void setServiceInstances(List<ServiceInstance> serviceInstances) {
		this.serviceInstances = serviceInstances;
	}

	public ModuleMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ModuleMetadata metadata) {
		this.metadata = metadata;
	}

	public Date getFetchMetaDataTime() {
		return fetchMetaDataTime == null ? createdAt : fetchMetaDataTime;
	}

	public void setFetchMetaDataTime(Date fetchMetaDataTime) {
		this.fetchMetaDataTime = fetchMetaDataTime;
	}

	@Override
	public String toString() {
		return "ModuleEntity [id=" + id + ", name=" + name + ", serviceId=" + serviceId + ", routeName=" + routeName
				+ ", moduleType=" + moduleType + "]";
	}

	
}