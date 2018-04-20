package com.oneplatform.system.dto.param;

import com.jeesuite.mybatis.plugin.pagination.PageParams;

public class AccountQueryParam extends PageParams{

	private String username;

    private String email;

    private String mobile;

    private String realname;

    private Boolean enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	} 
    
    
}
