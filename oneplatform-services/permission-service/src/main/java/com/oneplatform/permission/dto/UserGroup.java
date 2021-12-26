package com.oneplatform.permission.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class UserGroup {

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
     * 关联系统
     */
    @ApiModelProperty("关联系统id")
    private Integer systemId;

    /**
     * 关联系统名称
     */
    @ApiModelProperty("关联系统名称")
    private String systemName;

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

    /**
     * 是否系统默认
     */
    @ApiModelProperty("是否系统默认")
    private Boolean isDefault;

    private Boolean isDisplay;
    
    /**
     * 可用状态(0:不可用;1:可用)
     */
    @ApiModelProperty("可用状态(0:不可用;1:可用)")
    private Boolean enabled;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;

    /**
     * 创建人姓名
     */
    @ApiModelProperty("创建人姓名")
    private String createdBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedAt;

    /**
     * 更新人姓名
     */
    @ApiModelProperty("更新人姓名")
    private String updatedBy;

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

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getTenantId() {
		return tenantId;
	}

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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