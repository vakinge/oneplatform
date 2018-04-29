package com.oneplatform.system.dto.param;

public class ModuleParam {

	private Integer id;
	private String name;
    private String routeName;
    /**
     * 可跨域uri集合(多个;隔开)
     */
    private String corsUris;
    private String apidocUrl;
    /**
     * 是否内部服务模块
     */
    private boolean internal;
    
    
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
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getCorsUris() {
		return corsUris;
	}
	public void setCorsUris(String corsUris) {
		this.corsUris = corsUris;
	}
	public String getApidocUrl() {
		return apidocUrl;
	}
	public void setApidocUrl(String apidocUrl) {
		this.apidocUrl = apidocUrl;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
    
    

}
