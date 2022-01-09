package com.oneplatform.permission.dto;

import com.oneplatform.permission.constants.ClientType;

/**
 * 
 * <br>
 * Class Name : MenuItem
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年6月15日
 */
public class MenuItem {

	private String clientType = ClientType.PC.name();
	private String router;
	private String viewPath;
	private String icon;
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public String getViewPath() {
		return viewPath;
	}
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
