package com.oneplatform.system.constants;

/**
 * 从属关系
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月22日
 */
public enum SubRelationType {

	UserToUserGroup(1, "用户-用户组"), 
	PermToPermGroup(2, "权限-权限组"), 
	ApiToMenu(3, "接口-菜单");

	private final int code;
	private final String alias;

	private SubRelationType(int code, String alias) {
		this.code = code;
		this.alias = alias;
	}

	public int getCode() {
		return code;
	}

	public String getAlias() {
		return alias;
	}

	public static String getAlias(int code) {
		SubRelationType[] values = SubRelationType.values();
		for (SubRelationType s : values) {
			if (s.code == code)
				return s.getAlias();
		}
		return null;
	}
	
	public static SubRelationType get(byte code) {
		SubRelationType[] values = SubRelationType.values();
		for (SubRelationType s : values) {
			if (s.code == code)
				return s;
		}
		return null;
	}
	
}
