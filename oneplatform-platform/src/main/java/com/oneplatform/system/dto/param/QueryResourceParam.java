package com.oneplatform.system.dto.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeesuite.mybatis.plugin.pagination.PageParams;

import io.swagger.annotations.ApiModelProperty;

public class QueryResourceParam extends PageParams{

    private Integer parentId;
    private Integer moduleId;
    private String type;
    private String platformType;
    private Boolean enabled;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String grantType;
    
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getModuleId() {
		return moduleId != null && moduleId == 0 ? null : moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
}
