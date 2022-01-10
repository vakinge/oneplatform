package com.oneplatform.organization.dto.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <br>
 * Class Name   : PositionParam
 */
public class PositionParam {

    @ApiModelProperty("主数据ID")
    private String id;
    
    @ApiModelProperty("主数据编码")
    private String serialNo;
    
    @ApiModelProperty("岗位名称")
    private String name;
    
    @ApiModelProperty("组织全编码")
    private String departmentId;
    
    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("排序索引")
    private String sortIndex;

    @ApiModelProperty("生效时间")
    private java.util.Date effectDate;

    @ApiModelProperty("失效时间")
    private java.util.Date invalidDate;

    @ApiModelProperty("是否启用")
    private String enabled;

    @ApiModelProperty("旧系统映射ID")
    private String orgin_id;
    
    @ApiModelProperty("属性名")
    private java.util.Date createdAt;
    
    @ApiModelProperty("属性名")
    private String createdBy;
    
    @ApiModelProperty("更新时间")
    private java.util.Date updatedAt;
    
    @ApiModelProperty("属性名")
    private String updatedBy;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getOrgin_id() {
        return orgin_id;
    }

    public void setOrgin_id(String orgin_id) {
        this.orgin_id = orgin_id;
    }

    public java.util.Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(java.util.Date effectDate) {
        this.effectDate = effectDate;
    }
    public java.util.Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(java.util.Date invalidDate) {
        this.invalidDate = invalidDate;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}