package com.oneplatform.base.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ModuleMetadata {

	private String type;
	private String name;
	private String identifier;
	private String routeName;
	private String menuIcon;
	private String apiBasePackages;
	private List<ApiInfo> apis = new ArrayList<>();
	private List<Menu> menus = new ArrayList<>();
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getRouteName() {
		if(routeName == null){
			routeName = StringUtils.splitByWholeSeparator(identifier, "-")[0];
		}
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getApiBasePackages() {
		return apiBasePackages;
	}
	public void setApiBasePackages(String apiBasePackages) {
		this.apiBasePackages = apiBasePackages;
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
