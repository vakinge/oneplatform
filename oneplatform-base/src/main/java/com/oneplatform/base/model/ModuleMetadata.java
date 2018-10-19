package com.oneplatform.base.model;

import java.util.List;

public class ModuleMetadata {

	private String name;
	private String routePath;
	private List<ApiInfo> apis;
	private List<Menu> menus;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoutePath() {
		return routePath;
	}
	public void setRoutePath(String routePath) {
		this.routePath = routePath;
	}
	public List<ApiInfo> getApis() {
		return apis;
	}
	public void setApis(List<ApiInfo> apis) {
		this.apis = apis;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	
	
}
