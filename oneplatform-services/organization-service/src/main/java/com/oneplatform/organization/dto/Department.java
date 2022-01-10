package com.oneplatform.organization.dto;

import com.jeesuite.common.model.TreeModel;

/**
 * 
 * <br>
 * Class Name   : Department
 */
public class Department extends TreeModel{

    private String code;
    private String orgType;
    private String fullCode;
    private String fullName;
    private Boolean isVirtual;
    private Integer enabled;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
    private String createdBy;
    private String updatedBy;
    
    private Staff leader;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public java.util.Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.util.Date createdAt) {
		this.createdAt = createdAt;
	}

	public java.util.Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(java.util.Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Staff getLeader() {
		return leader;
	}

	public void setLeader(Staff leader) {
		this.leader = leader;
	}

    
}