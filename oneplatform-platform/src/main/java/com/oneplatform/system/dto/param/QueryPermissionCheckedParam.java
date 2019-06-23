package com.oneplatform.system.dto.param;

public class QueryPermissionCheckedParam {

    private String platformType;
    private String scope;
    private Integer userId;
    private Integer companyId;
    private Integer dempartmentId;
    private Boolean enabled;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getDempartmentId() {
		return dempartmentId;
	}
	public void setDempartmentId(Integer dempartmentId) {
		this.dempartmentId = dempartmentId;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	

}
