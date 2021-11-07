package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.core.base.StandardBaseEntity;

@Table(name = "system_domain")
public class BizSystemDomainEntity extends StandardBaseEntity {

    private String domain;
    
    @Column(name = "tenant_id",updatable = false)
    private String tenantId;
    
    @Column(name = "client_type",updatable = false)
    private String clientType;

    @Column(name = "enabled")
    private Boolean enabled = true;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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
