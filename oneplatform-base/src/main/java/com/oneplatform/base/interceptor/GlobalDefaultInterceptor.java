package com.oneplatform.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oneplatform.base.kit.AsyncTaskProduceClient;
import com.oneplatform.base.log.LogContext;
import com.oneplatform.base.log.LogContext.LogObject;
import com.oneplatform.base.log.LogOption;

import io.swagger.annotations.ApiOperation;

public class GlobalDefaultInterceptor implements HandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(GlobalDefaultInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LogContext.start(request);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			if(handler instanceof HandlerMethod){
				HandlerMethod method = (HandlerMethod)handler;
				LogObject logObject = LogContext.get();
				if(logObject != null){
					LogOption  logOption = method.getMethod().getAnnotation(LogOption.class);
					if(logOption == null || !logOption.skip()){
						if(logOption != null && StringUtils.isNotBlank(logOption.actionName())){
							logObject.setActionName(logOption.actionName());
						}
						if(StringUtils.isBlank(logObject.getActionName())){
							ApiOperation annotation = method.getMethod().getAnnotation(ApiOperation.class);
							if(annotation!= null)logObject.setActionName(annotation.value());
						}
						LogContext.end(String.valueOf(200), null);
						AsyncTaskProduceClient.send(LogContext.SYS_LOG_TOPIC, logObject);
					}
				}
			}
		} catch (Exception e) {
			log.warn("process_log_error",e);
		}finally {
			LogContext.clear();
		}
	}
	
}
