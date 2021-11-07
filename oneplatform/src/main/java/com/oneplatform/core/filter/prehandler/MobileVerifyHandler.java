package com.oneplatform.core.filter.prehandler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.cache.command.RedisString;
import com.jeesuite.common.WebConstants;
import com.jeesuite.common.util.SimpleCryptUtils;
import com.jeesuite.zuul.filter.FilterHandler;
import com.jeesuite.zuul.model.BizSystemModule;
import com.netflix.zuul.context.RequestContext;


/**
 * 统一验证手机验证码
 * <br>
 * Class Name   : MobileVerifyHandler
 *
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @version 1.0.0
 * @date 2021年3月20日
 */
public class MobileVerifyHandler implements FilterHandler {
	
	private static final String SMS_CODE_PARAM_NAME = "_sms_code";

	@Override
	public Object process(RequestContext ctx, HttpServletRequest request, BizSystemModule module) {
		
		String smsCode = request.getParameter(SMS_CODE_PARAM_NAME);
		if(StringUtils.isNotBlank(smsCode)) {
			String mobile = request.getParameter("mobile");
			if(StringUtils.isBlank(mobile)) {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(400);
				ctx.setResponseBody("{\"code\": 400,\"msg\":\"验证手机号不能为空\"}");
				return null;
			}
			//TODO 验证
			String checkCode = new RedisString("sms_code_" + mobile).get();
			if(StringUtils.isBlank(checkCode)){
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(400);
				ctx.setResponseBody("{\"code\": 400,\"msg\":\"验证码已过期，请重新发送\"}");
				return null;
			}
			
			if(!smsCode.equals(checkCode)){
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(400);
				ctx.setResponseBody("{\"code\": 400,\"msg\":\"验证码错误，请重新输入\"}");
				return null;
			}
			//TODO 删除缓存验证码
            
			//通过header透传到后端
			String encryptMobile = SimpleCryptUtils.encrypt(mobile);
			ctx.addZuulRequestHeader(WebConstants.HEADER_VERIFIED_MOBILE,encryptMobile);
		}
		
		return null;
	}

	@Override
	public int order() {
		return 1;
	}

}
