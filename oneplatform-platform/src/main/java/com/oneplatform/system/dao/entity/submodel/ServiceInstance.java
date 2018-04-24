package com.oneplatform.system.dao.entity.submodel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jeesuite.common.json.serializer.DateTimeConvertSerializer;
import com.netflix.appinfo.InstanceInfo;

@JsonInclude(Include.NON_NULL)
public class ServiceInstance {

	private String nodeId;
	private String instanceId;
	private String hostName; 
	private String appName; 
	private String ipAddr; 
	private String vipAddress; 
	private String status; 
	private int port;  
	private String healthCheckUrl; 
	private Date registrationTime;
	private Date lastRenewalTime;
	
	public ServiceInstance() {}
	
	public ServiceInstance(InstanceInfo instanceInfo) {
		this.appName = instanceInfo.getAppName();
		this.instanceId = instanceInfo.getInstanceId();
		this.hostName = instanceInfo.getHostName();
		this.ipAddr = instanceInfo.getIPAddr();
		this.vipAddress = instanceInfo.getVIPAddress();
		this.status = instanceInfo.getStatus().name();
		this.port = instanceInfo.getPort();
		this.healthCheckUrl = instanceInfo.getHealthCheckUrl();
		this.lastRenewalTime = new Date(instanceInfo.getLastDirtyTimestamp());
		this.nodeId = instanceInfo.getMetadata().get("nodeId");
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getVipAddress() {
		return vipAddress;
	}
	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}
	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}
	
	@JsonSerialize(using=DateTimeConvertSerializer.class)
	public Date getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}
	
	@JsonSerialize(using=DateTimeConvertSerializer.class)
	public Date getLastRenewalTime() {
		return lastRenewalTime;
	}
	public void setLastRenewalTime(Date lastRenewalTime) {
		this.lastRenewalTime = lastRenewalTime;
	}

}
