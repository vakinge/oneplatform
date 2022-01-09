package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;

@Table(name = "system_portal")
public class BizSystemPortalEntity extends StandardBaseEntity {

	 @Column(name = "index_path")
    private String indexPath;
    
    @Column(name = "tenant_id",updatable = false)
    private String tenantId;
    
    @Column(name = "client_type",updatable = false)
    private String clientType;

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
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

}
