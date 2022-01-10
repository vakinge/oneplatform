package com.oneplatform.organization.dto.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <br>
 * Class Name   : DepartmentParam
 */
public class DepartmentParam{

    @ApiModelProperty("主数据ID")
    private String id;

    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("父级ID")
    private String parentId;
    
    @ApiModelProperty("部门类型")
    private String orgType;

    @ApiModelProperty("排序索引")
    private Integer sortIndex;
    
    @ApiModelProperty("属性名")
    private Boolean isVirtual;
    
    @ApiModelProperty("部门负责人id")
    private String leaderId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
    
    
  
}