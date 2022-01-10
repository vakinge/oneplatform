package com.oneplatform.organization.dto.param;

/**
 * 
 * <br>
 * Class Name   : DepartmentQueryParam
 */
public class DepartmentQueryParam  {
	
	private String name;
	private String parentId;
    private Integer enabled;
 
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getEnabled() {
		return enabled;
	}

	

}