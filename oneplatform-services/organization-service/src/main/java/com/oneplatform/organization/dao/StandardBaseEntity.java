package com.oneplatform.organization.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.jeesuite.mybatis.core.BaseEntity;
import com.jeesuite.mybatis.plugin.autofield.annotation.CreatedAt;
import com.jeesuite.mybatis.plugin.autofield.annotation.CreatedBy;
import com.jeesuite.mybatis.plugin.autofield.annotation.UpdatedAt;
import com.jeesuite.mybatis.plugin.autofield.annotation.UpdatedBy;

public class StandardBaseEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;
	
	private Boolean enabled;

    private Boolean deleted;

    @CreatedAt
    @Column(name = "created_at")
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 更新时间
     */
    @UpdatedAt
    @Column(name = "updated_at")
    private Date updatedAt;

    @UpdatedBy
    @Column(name = "updated_by")
    private String updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

}
