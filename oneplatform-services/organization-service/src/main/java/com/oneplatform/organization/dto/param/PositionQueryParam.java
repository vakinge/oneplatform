package com.oneplatform.organization.dto.param;

/**
 * 
 * <br>
 * Class Name   : DepartmentQueryParam
 */
public class PositionQueryParam {

    private String name;
	private String departmentId;
    private Integer enabled;
    
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
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	
    
}