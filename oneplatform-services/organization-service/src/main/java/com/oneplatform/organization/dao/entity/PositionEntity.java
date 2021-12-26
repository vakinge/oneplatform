package com.oneplatform.organization.dao.entity;

import com.oneplatform.organization.dao.StandardBaseEntity;
import java.util.Date;
import javax.persistence.*;

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

    
}