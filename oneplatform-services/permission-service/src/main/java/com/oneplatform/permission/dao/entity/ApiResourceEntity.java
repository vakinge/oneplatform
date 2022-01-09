package com.oneplatform.permission.dao.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.oneplatform.permission.dao.StandardBaseEntity;


@Table(name = "api_resource")
public class ApiResourceEntity extends StandardBaseEntity {

    
    @Column(name = "module_id",updatable = false)
    private Integer moduleId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * uri
     */
    private String uri;

    @Column(name = "http_method")
    private String httpMethod;

    /**
     * 授权类型
     */
    @Column(name = "grant_type")
    private String grantType;



	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	/**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取uri
     *
     * @return uri - uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置uri
     *
     * @param uri uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return http_method
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * 获取授权类型
     *
     * @return grant_type - 授权类型
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * 设置授权类型
     *
     * @param grantType 授权类型
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

}