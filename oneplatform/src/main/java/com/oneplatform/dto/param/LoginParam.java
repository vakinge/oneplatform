package com.oneplatform.dto.param;

public class LoginParam {

	private String account;
	private String password;
	private String userType;
	private String vcode;
	private boolean rememerMe;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
