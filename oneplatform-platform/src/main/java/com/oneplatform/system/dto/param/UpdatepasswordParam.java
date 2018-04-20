package com.oneplatform.system.dto.param;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdatepasswordParam {

	@JsonIgnore
	private int userId;
	private String oldPassword;
	private String password;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
