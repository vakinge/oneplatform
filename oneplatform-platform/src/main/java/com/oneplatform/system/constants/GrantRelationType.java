package com.oneplatform.system.constants;

/**
 * 授权关系
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2019年4月22日
 */
public enum GrantRelationType {

	PermToUser(1, "用户-权限"), 
	PermGroupToUser(2, "用户-权限组"), 
	PermToUserGroup(3, "用户组-权限"), 
	PermGroupToUserGroup(4, "用户组-权限组");

	private final int code;
	private final String alias;

	private GrantRelationType(int code, String alias) {
		this.code = (int) code;
		this.alias = alias;
	}

	public int getCode() {
		return code;
	}

	public String getAlias() {
		return alias;
	}

	public static String getAlias(int code) {
		GrantRelationType[] values = GrantRelationType.values();
		for (GrantRelationType s : values) {
			if (s.code == code)
				return s.getAlias();
		}
		return null;
	}
	
	public static GrantRelationType get(int code) {
		GrantRelationType[] values = GrantRelationType.values();
		for (GrantRelationType s : values) {
			if (s.code == code)
				return s;
		}
		return null;
	}
	
}
