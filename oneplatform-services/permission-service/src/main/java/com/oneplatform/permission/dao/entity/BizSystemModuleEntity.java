package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;

@Table(name = "system_module")
public class BizSystemModuleEntity extends StandardBaseEntity {
 
    private String name;

    /**
     * 关联系统
     */
    @Column(name = "system_id")
    private Integer systemId;
    
    @Column(name = "service_id")
    private String serviceId;
    
    @Column(name = "proxy_url")
    private String proxyUrl;
    
    @Column(name = "route_name")
    private String routeName;

    @Column(name = "anonymous_uris")
    private String anonymousUris;
 
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
     * 获取关联系统
     *
     * @return system_id - 关联系统
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * 设置关联系统
     *
     * @param systemId 关联系统
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	

	public String getProxyUrl() {
		return proxyUrl;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
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
     * @return anonymous_uris
     */
    public String getAnonymousUris() {
        return anonymousUris;
    }

    /**
     * @param anonymousUris
     */
    public void setAnonymousUris(String anonymousUris) {
        this.anonymousUris = anonymousUris;
    }

}