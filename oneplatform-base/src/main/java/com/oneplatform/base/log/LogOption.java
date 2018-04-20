package com.oneplatform.base.log;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target(METHOD)
public @interface LogOption {

	public boolean skip() default false;
	public String actionName() default "";
}
