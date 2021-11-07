package com.oneplatform.modules.system.dto.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class UserGroupParam {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 用户组名称
     */
    @ApiModelProperty("用户组名称")
    private String name;

    /**
     * 关联租户id
     */
    @ApiModelProperty("关联租户id")
    private String tenantId;

    /**
     * 关联部门id
     */
    @ApiModelProperty("关联部门id")
    private String departmentId;

    private String departmentName;
    private String departmentLongCode;
	private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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

	public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentLongCode() {
		return departmentLongCode;
	}

	public void setDepartmentLongCode(String departmentLongCode) {
		this.departmentLongCode = departmentLongCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    
}