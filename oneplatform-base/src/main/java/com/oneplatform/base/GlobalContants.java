package com.oneplatform.base;

import com.jeesuite.common.util.ResourceUtils;

public class GlobalContants {

	public static final String  MODULE_NAME = ResourceUtils.getProperty("spring.application.name");
	
	public static final int SESSION_EXPIRE_SECONDS = ResourceUtils.getInt("auth.session.expire.seconds", 7200);
	
	public static enum ModuleType{
		plugin,service;
	}
	
	public static enum UserScope{
		sa,admin,saas,user
	}
}
