package com.oneplatform.permission.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jeesuite.mybatis.core.BaseEntity;
import com.jeesuite.mybatis.plugin.autofield.annotation.CreatedAt;
import com.jeesuite.mybatis.plugin.autofield.annotation.CreatedBy;
import com.jeesuite.mybatis.plugin.autofield.annotation.UpdatedAt;
import com.jeesuite.mybatis.plugin.autofield.annotation.UpdatedBy;

public class StandardBaseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreatedAt
    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private String createdBy;

    @UpdatedAt
    @Column(name = "updated_at")
    private Date updatedAt;

    @UpdatedBy
    @Column(name = "updated_by")
    private String updatedBy;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
