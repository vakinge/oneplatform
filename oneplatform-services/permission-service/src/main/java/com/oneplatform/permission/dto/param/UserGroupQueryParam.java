package com.oneplatform.permission.dto.param;

import com.jeesuite.springweb.model.BaseQueryParam;

import io.swagger.annotations.ApiModelProperty;

public class UserGroupQueryParam extends BaseQueryParam {

    /**
     * 用户组名称
     */
    @ApiModelProperty("用户组名称")
    private String name;

    @ApiModelProperty("关联租户id")
    private String tenantId;
    /**
     * 关联部门id
     */
    @ApiModelProperty("关联部门id")
    private String departmentId;

    /**
     * 是否系统默认
     */
    @ApiModelProperty("是否系统默认")
    private Boolean isDefault;

    /**
     * 激活状态
     */
    @ApiModelProperty("激活状态")
    private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}