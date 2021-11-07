package com.oneplatform.modules.system.dto;

public class BizSystemModule {

    private Integer id;

    private String name;
    
    private String appId;

    /**
     * 关联系统
     */
    private Integer systemId;
    
    private String serviceId;

    private String routeName;

    private String anonymousUris;

    private Boolean enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getAnonymousUris() {
		return anonymousUris;
	}

	public void setAnonymousUris(String anonymousUris) {
		this.anonymousUris = anonymousUris;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

    
}