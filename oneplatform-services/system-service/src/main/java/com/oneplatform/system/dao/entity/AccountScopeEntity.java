package com.oneplatform.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.system.dao.StandardBaseEntity;

@Table(name = "account_scope")
public class AccountScopeEntity extends StandardBaseEntity {
  
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "tenant_id")
    private String tenantId;
    
    @Column(name = "principal_type")
    private String principalType;
    
    @Column(name = "principal_id")
    private String principalId;

    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPrincipalType() {
		return principalType;
	}

	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

    
}