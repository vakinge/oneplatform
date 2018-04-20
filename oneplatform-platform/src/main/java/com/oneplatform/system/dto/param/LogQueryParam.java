package com.oneplatform.system.dto.param;

import com.jeesuite.mybatis.plugin.pagination.PageParams;

public class LogQueryParam extends PageParams {
	
	    private Integer moduleId;
	    private String name;

	    private String requestIp;
	    private Boolean successed;
	    private String actionStart;
	    private String actionEnd;
	    
		
		public Integer getModuleId() {
			return moduleId;
		}
		public void setModuleId(Integer moduleId) {
			this.moduleId = moduleId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRequestIp() {
			return requestIp;
		}
		public void setRequestIp(String requestIp) {
			this.requestIp = requestIp;
		}
		public Boolean getSuccessed() {
			return successed;
		}
		public void setSuccessed(Boolean successed) {
			this.successed = successed;
		}
		public String getActionStart() {
			return actionStart;
		}
		public void setActionStart(String actionStart) {
			this.actionStart = actionStart;
		}
		public String getActionEnd() {
			return actionEnd;
		}
		public void setActionEnd(String actionEnd) {
			this.actionEnd = actionEnd;
		}
	    
	    

}
