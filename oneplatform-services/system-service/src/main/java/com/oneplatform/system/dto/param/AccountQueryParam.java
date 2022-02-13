package com.oneplatform.system.dto.param;

/**
 * 
 * 
 * <br>
 * Class Name   : AccountQueryParam
 *
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @version 1.0.0
 * @date Feb 12, 2022
 */
public class AccountQueryParam {

	private String name;
    private String email;
    private String mobile;
    private Boolean enabled;
    
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
    

}
