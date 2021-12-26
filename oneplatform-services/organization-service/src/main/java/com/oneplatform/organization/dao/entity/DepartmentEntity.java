package com.oneplatform.organization.dao.entity;

import com.oneplatform.organization.dao.StandardBaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "department")
public class DepartmentEntity extends StandardBaseEntity {
  
    /**
     * 主数据编码
     */
    private String code;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 组织类型
     */
    @Column(name = "org_type")
    private String orgType;

    /**
     * 组织全编码
     */
    @Column(name = "full_code")
    private String fullCode;

    /**
     * 组织全称
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 排序索引
     */
    @Column(name = "sort_index")
    private Integer sortIndex;

    /**
     * 是否虚拟组织 （0:非虚拟 ，1:虚拟）
     */
    @Column(name = "is_virtual")
    private Boolean isVirtual;

    /**
     * 是否叶子节点(0:不是 ， 1：是)
     */
    @Column(name = "is_leaf")
    private Boolean isLeaf;

    /**
     * 部门负责人id
     */
    @Column(name = "leader_id")
    private String leaderId;

    @Column(name = "tenant_id")
    private String tenantId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    
}