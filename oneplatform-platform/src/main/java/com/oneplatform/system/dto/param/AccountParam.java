package com.oneplatform.system.dto.param;

import org.apache.commons.lang3.StringUtils;

public class AccountParam {

	private Integer id;
	
	private String username;

    private String email;

    private String mobile;

    private String realname;
    
    private Integer[] roleIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return StringUtils.trimToNull(username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return StringUtils.trimToNull(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return StringUtils.trimToNull(mobile);
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealname() {
		return StringUtils.trimToNull(realname);
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
    
    
}
