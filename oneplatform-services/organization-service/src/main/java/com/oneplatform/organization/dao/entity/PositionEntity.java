package com.oneplatform.organization.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.oneplatform.organization.dao.StandardBaseEntity;

@Table(name = "position")
public class PositionEntity extends StandardBaseEntity {
  
    /**
     * 岗位名称
     */
    private String name;

    /**
     * 部门ID
     */
    @Column(name = "department_id")
    private String departmentId;
    
    @Transient
    private String departmentName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	
    
}