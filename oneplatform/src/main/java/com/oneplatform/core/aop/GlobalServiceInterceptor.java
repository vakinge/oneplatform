package com.oneplatform.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jeesuite.spring.interceptor.JeesuiteSpringBaseInterceptor;

/**
 * 
 * <br>
 * Class Name   : GlobalServiceInterceptor
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2020年4月20日
 */
@Component
@Aspect
@Order(0)
public class GlobalServiceInterceptor extends JeesuiteSpringBaseInterceptor {

	@Override
	@Pointcut("execution(public * com.oneplatform.modules.*.service.*.*(..))")
	public void pointcut() {}


}
