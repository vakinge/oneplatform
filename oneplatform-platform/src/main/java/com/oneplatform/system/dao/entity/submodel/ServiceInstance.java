package com.oneplatform.system.dao.entity.submodel;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jeesuite.common.json.serializer.DateTimeConvertSerializer;

//@JsonInclude(Include.NON_NULL)
public class ServiceInstance {

	private String nodeId;
	private String instanceId;
	private String hostName; 
	private String app; 
	private String ipAddr; 
	private String vipAddress; 
	private String secureVipAddress; 
	private String status; 
	private int port; 
	private int securePort; 
	private String homePageUrl; 
	private String statusPageUrl; 
	private String healthCheckUrl; 
	private Date registrationTime;
	private Date lastRenewalTime;
	private Date evictionTime;
	private Date serviceUpTime;
	
	
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
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
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
	public String getSecureVipAddress() {
		return secureVipAddress;
	}
	public void setSecureVipAddress(String secureVipAddress) {
		this.secureVipAddress = secureVipAddress;
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
	public int getSecurePort() {
		return securePort;
	}
	public void setSecurePort(int securePort) {
		this.securePort = securePort;
	}
	public String getHomePageUrl() {
		return homePageUrl;
	}
	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}
	public String getStatusPageUrl() {
		return statusPageUrl;
	}
	public void setStatusPageUrl(String statusPageUrl) {
		this.statusPageUrl = statusPageUrl;
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
	
	@JsonSerialize(using=DateTimeConvertSerializer.class)
	public Date getEvictionTime() {
		return evictionTime;
	}
	public void setEvictionTime(Date evictionTime) {
		this.evictionTime = evictionTime;
	}
	@JsonSerialize(using=DateTimeConvertSerializer.class)
	public Date getServiceUpTime() {
		return serviceUpTime;
	}
	public void setServiceUpTime(Date serviceUpTime) {
		this.serviceUpTime = serviceUpTime;
	}
	
	
	
	
}
