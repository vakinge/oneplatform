package com.oneplatform.system.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jeesuite.mybatis.core.BaseEntity;
import com.oneplatform.base.GlobalContants;
import com.oneplatform.base.log.LogContext.LogObject;

@Table(name = "sys_logs")
public class SysLogEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_id")
    private String requestId;
    
    @Column(name = "module_id")
    private Integer moduleId;
    
    private String module;

    private String name;

    private String uri;

    private String origin;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "request_at")
    private Date requestAt;

    @Column(name = "response_at")
    private Date responseAt;

    @Column(name = "request_uid")
    private Integer requestUid;

    @Column(name = "request_uname")
    private String requestUname;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "response_msg")
    private String responseMsg;

    @Column(name = "use_time")
    private Integer useTime;

    private Boolean entrylog;

    @Column(name = "request_datas")
    private String requestDatas;

    private String exception;
    
    public SysLogEntity() {}
    
    public SysLogEntity(LogObject log) {
    	this.requestId = log.getRequestId();
    	this.module = GlobalContants.MODULE_NAME;
    	this.name = log.getActionName();
    	this.uri = log.getUri();
    	this.origin = log.getOrigin();
    	this.requestIp = log.getRequestIp();
    	this.requestAt = log.getRequestTime();
    	this.requestUid = log.getRequestUid();
    	this.requestUname = log.getRequestUname();
    	this.responseCode = log.getResponseCode();
    	this.responseMsg = log.getResponseMsg();
    	this.responseAt = log.getResponseTime();
    	this.requestDatas = log.getRequestData();
    	this.exception = log.getException();
    	this.useTime = (int) (this.responseAt.getTime() - this.requestAt.getTime());
    	this.entrylog = log.isEntrylog();
    }

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
    

    public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	/**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
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
     * @return response_at
     */
    public Date getResponseAt() {
        return responseAt;
    }

    /**
     * @param responseAt
     */
    public void setResponseAt(Date responseAt) {
        this.responseAt = responseAt;
    }

    /**
     * @return request_uid
     */
    public Integer getRequestUid() {
        return requestUid;
    }

    /**
     * @param requestUid
     */
    public void setRequestUid(Integer requestUid) {
        this.requestUid = requestUid;
    }

    /**
     * @return request_uname
     */
    public String getRequestUname() {
        return requestUname;
    }

    /**
     * @param requestUname
     */
    public void setRequestUname(String requestUname) {
        this.requestUname = requestUname;
    }

    /**
     * @return response_code
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return response_msg
     */
    public String getResponseMsg() {
        return responseMsg;
    }

    /**
     * @param responseMsg
     */
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
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
     * @return entrylog
     */
    public Boolean getEntrylog() {
        return entrylog;
    }

    /**
     * @param entrylog
     */
    public void setEntrylog(Boolean entrylog) {
        this.entrylog = entrylog;
    }

    /**
     * @return request_datas
     */
    public String getRequestDatas() {
        return requestDatas;
    }

    /**
     * @param requestDatas
     */
    public void setRequestDatas(String requestDatas) {
        this.requestDatas = requestDatas;
    }

    /**
     * @return exception
     */
    public String getException() {
        return exception;
    }

    /**
     * @param exception
     */
    public void setException(String exception) {
        this.exception = exception;
    }
    
    public boolean isSuccess(){
    	return LogObject.SUCEESS_RESP_CODE.equals(responseCode);
    }
}