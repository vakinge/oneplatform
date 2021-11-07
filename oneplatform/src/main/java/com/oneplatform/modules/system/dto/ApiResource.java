package com.oneplatform.modules.system.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class ApiResource {

    private Integer id;

    /**
     * 对应应用id
     */
    @ApiModelProperty("对应应用id")
    private String appId;

    /**
     * 资源名称
     */
    @ApiModelProperty("资源名称")
    private String name;

    /**
     * uri
     */
    @ApiModelProperty("uri")
    private String uri;

    /**
     * 请求方式：GET,POST
     */
    @ApiModelProperty("请求方式(GET,POST)")
    private String httpMethod;

    /**
     * 授权类型
     */
    @ApiModelProperty("授权类型")
    private String grantType;

    /**
     * 网关转发路径
     */
    @ApiModelProperty("网关转发路径")
    private String routePath;

    /**
     * 可用状态(0:不可用;1:可用)
     */
    @ApiModelProperty("可用状态(0:不可用;1:可用)")
    private Boolean enabled;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;

    /**
     * 创建人姓名
     */
    @ApiModelProperty("创建人姓名")
    private String createdBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedAt;

    /**
     * 更新人姓名
     */
    @ApiModelProperty("更新人姓名")
    private String updatedBy;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取对应应用id
     *
     * @return app_id - 对应应用id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置对应应用id
     *
     * @param appId 对应应用id
     */
    public void setAppId(String appId) {
        this.appId = appId;
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

    /**
     * 获取网关转发路径
     *
     * @return route_path - 网关转发路径
     */
    public String getRoutePath() {
        return routePath;
    }

    /**
     * 设置网关转发路径
     *
     * @param routePath 网关转发路径
     */
    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    /**
     * 获取可用状态(0:不可用;1:可用)
     *
     * @return enabled - 可用状态(0:不可用;1:可用)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置可用状态(0:不可用;1:可用)
     *
     * @param enabled 可用状态(0:不可用;1:可用)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取创建人姓名
     *
     * @return created_by - 创建人姓名
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人姓名
     *
     * @param createdBy 创建人姓名
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取更新人姓名
     *
     * @return updated_by - 更新人姓名
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置更新人姓名
     *
     * @param updatedBy 更新人姓名
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}