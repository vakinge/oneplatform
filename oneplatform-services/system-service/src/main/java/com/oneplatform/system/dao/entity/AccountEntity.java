package com.oneplatform.system.dao.entity;

import javax.persistence.Table;

import com.oneplatform.system.dao.StandardBaseEntity;

@Table(name = "account")
public class AccountEntity extends StandardBaseEntity {
 
    private String name;

    private String email;

    private String mobile;

    private String password;
    
    private String avatar;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
    
}