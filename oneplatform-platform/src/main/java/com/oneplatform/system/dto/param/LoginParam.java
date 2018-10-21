package com.oneplatform.system.dto.param;

public class LoginParam {

	private String loginName;
	private String password;
	private String vcode;
	private boolean rememerMe;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public void setUsername(String username) {
		this.loginName = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public boolean isRememerMe() {
		return rememerMe;
	}
	public void setRememerMe(boolean rememerMe) {
		this.rememerMe = rememerMe;
	}
	
	
	
	
}
