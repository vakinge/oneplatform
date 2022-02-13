package com.oneplatform.system.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "action_logs")
public class ActionLogEntity extends BaseEntity {
    @Id
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "action_key")
    private String actionKey;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "platform_type")
    private String platformType;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "request_at")
    private Date requestAt;

    @Column(name = "query_parameters")
    private String queryParameters;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "use_time")
    private Integer useTime;

    @Column(name = "request_data")
    private String requestData;

    @Column(name = "response_data")
    private String responseData;

    private String exceptions;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return app_id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return tenant_id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return action_name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionName
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * @return action_key
     */
    public String getActionKey() {
        return actionKey;
    }

    /**
     * @param actionKey
     */
    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return client_type
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * @return platform_type
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * @param platformType
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * @return module_id
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return request_ip
     */
    public String getRequestIp() {
        return requestIp;
    }

    /**
     * @param requestIp
     */
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    /**
     * @return request_id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return request_at
     */
    public Date getRequestAt() {
        return requestAt;
    }

    /**
     * @param requestAt
     */
    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    /**
     * @return query_parameters
     */
    public String getQueryParameters() {
        return queryParameters;
    }

    /**
     * @param queryParameters
     */
    public void setQueryParameters(String queryParameters) {
        this.queryParameters = queryParameters;
    }

    /**
     * @return response_code
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return use_time
     */
    public Integer getUseTime() {
        return useTime;
    }

    /**
     * @param useTime
     */
    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    /**
     * @return request_data
     */
    public String getRequestData() {
        return requestData;
    }

    /**
     * @param requestData
     */
    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    /**
     * @return response_data
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * @param responseData
     */
    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    /**
     * @return exceptions
     */
    public String getExceptions() {
        return exceptions;
    }

    /**
     * @param exceptions
     */
    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }
}