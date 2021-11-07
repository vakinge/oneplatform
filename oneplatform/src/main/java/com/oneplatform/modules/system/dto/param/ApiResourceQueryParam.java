package com.oneplatform.modules.system.dto.param;

import com.jeesuite.springweb.model.BaseQueryParam;


public class ApiResourceQueryParam extends BaseQueryParam {


    private Integer moduleId;

    /**
     * 接口名称（可模糊）
     */
    private String name;

    /**
     * 请求方式（GET,POST)
     */
    private String httpMethod;

    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 激活状态
     */
    private Boolean enabled;
   

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

}
