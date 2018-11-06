package com.oneplatform.system.dto.param;

public class AssignResourceParam {

	private int roleId;
	private String type;
	private Integer[] assignmentIds;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer[] getAssignmentIds() {
		return assignmentIds;
	}
	public void setAssignmentIds(Integer[] assignmentIds) {
		this.assignmentIds = assignmentIds;
	}
	
	
}
