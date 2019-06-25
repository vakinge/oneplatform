package com.oneplatform.cms.dto.param;

public class CategoryParam {

	private Integer id;
    private String name;
    private Integer pid;
    
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
	public Integer getPid() {
		return pid != null && pid == 0 ? null : pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
    
    
    
}
