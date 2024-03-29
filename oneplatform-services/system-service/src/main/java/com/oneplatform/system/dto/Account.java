package com.oneplatform.system.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account extends BaseDto{

	private String name;

    private String email;

    private String mobile;
    
    private String avatar;
    
    @JsonIgnore
    private String password;
    
    private String staffName;
    private String staffNo;
    
    private List<AccountScope> scopes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public List<AccountScope> getScopes() {
		return scopes;
	}

	public void setScopes(List<AccountScope> scopes) {
		this.scopes = scopes;
	}

	
}
