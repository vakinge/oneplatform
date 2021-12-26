package com.oneplatform.permission.dto.param;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ApiResourceParam {

    private Integer id;

    /**
     * 对应应用id
     */
    @ApiModelProperty(value = "对应应用id",required = true)
    @NotBlank(message = "关联应用不能为空")
    private String appId;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "接口名称",required = true)
    @NotBlank(message = "接口名称不能为空")
    private String name;

    /**
     * uri
     */
    @ApiModelProperty(value = "uri",required = true)
    @NotBlank(message = "uri不能为空")
    private String uri;

    /**
     * 请求方式：GET,POST
     */
    @ApiModelProperty("请求方式(GET,POST),默认为GET")
    private String httpMethod;

    /**
     * 授权类型
     */
    @ApiModelProperty("授权类型(Anonymous,Logined,Authorized)")
    private String grantType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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