package com.oneplatform.modules.system.dto;

import io.swagger.annotations.ApiModelProperty;


public class BizSystemDomain {

    @ApiModelProperty(value = "系统管理域名id")
    private Integer id;

    @ApiModelProperty(value = "关联系统id")
    private Integer systemId;

    @ApiModelProperty(value = "域名")
    private String domain;
    
    @ApiModelProperty(value = "所属租户")
    private String tenantId;
    
    @ApiModelProperty(value = "应用端类型")
    private String clientType;

    @ApiModelProperty(value = "可用状态")
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
    
    

}
