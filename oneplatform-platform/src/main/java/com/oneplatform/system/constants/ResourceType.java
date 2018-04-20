package com.oneplatform.system.constants;

public enum ResourceType {

	//'menu', 'uri','button'
	menu("菜单"),uri("接口"),button("按钮"),all("全部");
	
	public final String typeName;

	private ResourceType(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
