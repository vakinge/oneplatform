package com.oneplatform.base.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.oneplatform.base.constants.PermissionType;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ApiPermOptions {
	boolean ignore() default false;
	String name() default "";
	PermissionType perms() default PermissionType.Logined;
}
