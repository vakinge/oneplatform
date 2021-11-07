package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.core.base.StandardBaseEntity;


@Table(name = "user_group")
public class UserGroupEntity extends StandardBaseEntity {

    /**
     * 用户组名称
     */
    private String name;

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
    
    @Column(name = "dept_name")
    private String deptName;
    
    @Column(name = "user_type")
    private String userType;

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

    /**
     * 可用状态(0:不可用;1:可用)
     */
    private Boolean enabled;

    /**
     * 获取用户组名称
     *
     * @return name - 用户组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户组名称
     *
     * @param name 用户组名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
     * 获取关联部门id
     *
     * @return dept_id - 关联部门id
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * 设置关联部门id
     *
     * @param deptId 关联部门id
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	protected String getUserType() {
		return userType;
	}

	protected void setUserType(String userType) {
		this.userType = userType;
	}

	/**
     * 获取是否系统默认
     *
     * @return is_default - 是否系统默认
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否系统默认
     *
     * @param isDefault 是否系统默认
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取可用状态(0:不可用;1:可用)
     *
     * @return enabled - 可用状态(0:不可用;1:可用)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置可用状态(0:不可用;1:可用)
     *
     * @param enabled 可用状态(0:不可用;1:可用)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}


}