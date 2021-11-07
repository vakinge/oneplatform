package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.core.base.StandardBaseEntity;

@Table(name = "account_scopes")
public class AccountScopeEntity extends StandardBaseEntity {

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "is_admin")
    private boolean admin;

    private Boolean enabled;

    private Boolean deleted;

    /**
     * @return account_id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * @return tenant_id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
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
     * @return deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}