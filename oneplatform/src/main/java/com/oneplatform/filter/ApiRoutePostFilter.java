package com.oneplatform.filter;

import org.springframework.stereotype.Component;

import com.jeesuite.zuul.filter.AbstractZuulFilter;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年6月15日
 */
@Component
public class ApiRoutePostFilter extends AbstractZuulFilter {

	public ApiRoutePostFilter() {
		super("post");
	}
	
}
