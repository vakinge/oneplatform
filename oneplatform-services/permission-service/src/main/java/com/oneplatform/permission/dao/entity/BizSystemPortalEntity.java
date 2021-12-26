package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;

@Table(name = "system_portal")
public class BizSystemPortalEntity extends StandardBaseEntity {

	 @Column(name = "home_page")
    private String homePage;
    
    @Column(name = "tenant_id",updatable = false)
    private String tenantId;
    
    @Column(name = "client_type",updatable = false)
    private String clientType;

    @Column(name = "enabled")
    private Boolean enabled = true;


    public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
