package com.oneplatform.base.log;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.common.util.TokenGenerator;
import com.jeesuite.springweb.utils.IpUtils;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.model.LoginSession;
import com.oneplatform.base.model.UserInfo;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月24日
 */
public class LogContext {

	private static ThreadLocal<LogObject> holder = new ThreadLocal<>();

	public static final String SYS_LOG_TOPIC = "syslog-topic";
	
	public static LogObject get() {
		LogObject log = holder.get();
		return log;
	}
	
	public static void clear() {
		holder.remove();
	}
	
	public static void start(HttpServletRequest request) {
		if(!request.getMethod().equals(HttpMethod.POST.name()))return;
		LogObject log = holder.get();
		if (log == null) {
			log = new LogObject();
			log.setRequestTime(new Date());
			holder.set(log);
		}

		String requestId = request.getHeader("x-request-id");
		if (requestId == null) {
			requestId = TokenGenerator.generate();
			log.setEntrylog(true);
		}
		log.setRequestId(requestId);
		log.setRequestIp(IpUtils.getIpAddr(request));
		log.setUri(request.getRequestURI());
		log.setOrigin(request.getHeader(HttpHeaders.REFERER));
		LoginSession loginUser = LoginContext.getLoginSession();
		if(loginUser != null){
			log.setRequestUid(loginUser.getUserId());
			log.setRequestUname(loginUser.getUserName());
		}
	}
	
	
	public static LogObject exception(Exception e) {
		LogObject log = holder.get();
		if(log != null){
			log.setException(ExceptionUtils.getStackTrace(e));
		}
		return log;
	}

	public static LogObject end(String responseCode, String responseMsg) {
		LogObject log = holder.get();
		if(log != null && log.getResponseTime() == null){
			log.setResponseCode(responseCode);
			log.setResponseMsg(responseMsg);
			log.setResponseTime(new Date());
		}
		return log;
	}


	public static class LogObject implements Serializable {

		private static final long serialVersionUID = 1L;
		public static final String SUCEESS_RESP_CODE = "200";
		private String requestId;
		private String requestIp;
		private Integer requestUid;
		private String requestUname;
		private String requestData;
		private Date requestTime;
		private String uri;
		private String actionName;
		private String origin;
		private String exception;

		private Date responseTime;
		private String responseCode = SUCEESS_RESP_CODE;
		private String responseMsg;

		private boolean entrylog;

		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public String getRequestIp() {
			return requestIp;
		}

		public void setRequestIp(String requestIp) {
			this.requestIp = requestIp;
		}

		public String getRequestData() {
			return requestData;
		}

		public void setRequestData(String requestData) {
			this.requestData = requestData;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getActionName() {
			return actionName;
		}

		public void setActionName(String actionName) {
			this.actionName = actionName;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}


		public Integer getRequestUid() {
			return requestUid;
		}

		public void setRequestUid(Integer requestUid) {
			this.requestUid = requestUid;
		}

		public String getRequestUname() {
			return requestUname;
		}

		public void setRequestUname(String requestUname) {
			this.requestUname = requestUname;
		}

		public String getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}

		public String getResponseMsg() {
			return responseMsg;
		}

		public void setResponseMsg(String responseMsg) {
			this.responseMsg = responseMsg;
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public boolean isEntrylog() {
			return entrylog;
		}

		public void setEntrylog(boolean entrylog) {
			this.entrylog = entrylog;
		}

		public Date getRequestTime() {
			return requestTime;
		}

		public void setRequestTime(Date requestTime) {
			this.requestTime = requestTime;
		}

		public Date getResponseTime() {
			return responseTime;
		}

		public void setResponseTime(Date responseTime) {
			this.responseTime = responseTime;
		}
		
		
	}

}
