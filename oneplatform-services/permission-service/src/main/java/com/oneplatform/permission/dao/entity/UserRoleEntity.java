package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;


@Table(name = "user_role")
public class UserRoleEntity extends StandardBaseEntity {

    /**
     * 用户组名称
     */
    private String name;
    
    private String code;
    
    @Column(name = "role_type")
    private String roleType;

    /**
     * 关联租户id
     */
    @Column(name = "tenant_id",updatable = false)
    private String tenantId;

    /**
     * 关联部门id
     */
    @Column(name = "dept_id")
    private String deptId;
    
    private String remarks;
    
    private String tags;
    
    private String permissions;

    /**
     * 是否系统默认
     */
    @Column(name = "is_default")
    private Boolean isDefault = Boolean.FALSE;

    /**
     * 是否开放用户申请权限
     */
    @Column(name = "is_display")
    private Boolean isDisplay = Boolean.TRUE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}