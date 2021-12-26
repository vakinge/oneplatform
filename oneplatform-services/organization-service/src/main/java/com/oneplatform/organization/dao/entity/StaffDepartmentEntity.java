package com.oneplatform.organization.dao.entity;

import com.oneplatform.organization.dao.StandardBaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "staff_departments")
public class StaffDepartmentEntity extends StandardBaseEntity {
 
    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "department_id")
    private String departmentId;

    /**
     * 岗位id
     */
    @Column(name = "position_id")
    private String positionId;

    /**
     * 雇佣关系
     */
    @Column(name = "employee_type")
    private String employeeType;

    /**
     * 是否主部门
     */
    @Column(name = "is_primary")
    private Boolean isPrimary;

    /**
     * 生效时间
     */
    @Column(name = "effect_date")
    private Date effectDate;

    /**
     * 失效时间
     */
    @Column(name = "invalid_date")
    private Date invalidDate;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

    
}