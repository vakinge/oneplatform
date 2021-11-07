package com.oneplatform.core.filter;

import org.springframework.stereotype.Component;

import com.jeesuite.zuul.filter.AbstractZuulFilter;
import com.oneplatform.core.filter.prehandler.MobileVerifyHandler;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年6月12日
 */
@Component
public class ApiRoutePreFilter extends AbstractZuulFilter {

	public ApiRoutePreFilter() {
		super("pre",new MobileVerifyHandler());
	}
	


}
