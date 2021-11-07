package com.oneplatform.modules.system.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


public class BizSystem {

	@ApiModelProperty(value = "业务系统id")
	private Integer id;

	/**
	 * 系统标识
	 */
	private String code;

	/**
	 * 系统名称
	 */
	private String name;

	/**
	 * 系统说明
	 */
	private String description;

	/**
	 * 系统等级
	 */
	private String level;

	/**
	 * 系统类型
	 */
	private String systemType;

	/**
	 * 管理部门编码
	 */
	private String chargeDepartmentId;

	/**
	 * 管理部门名称
	 */
	private String chargeDepartmentName;

	/**
	 * 系统负责人编码
	 */
	private String chargeUserId;

	/**
	 * 系统负责人名称
	 */
	private String chargeUserName;

	/**
	 * 业务需求部门编码
	 */
	private String bizDepartmentId;

	/**
	 * 业务需求部门
	 */
	private String bizDepartmentName;

	/**
	 * 系统状态
	 */
	private Integer status;

	/**
	 * 上线时间
	 */
	private Date serviceStartTime;
	
	private List<BizSystemDomain> domains;
	
	private List<BizSystemModule> modules;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getChargeDepartmentId() {
		return chargeDepartmentId;
	}

	public void setChargeDepartmentId(String chargeDepartmentId) {
		this.chargeDepartmentId = chargeDepartmentId;
	}

	public String getChargeDepartmentName() {
		return chargeDepartmentName;
	}

	public void setChargeDepartmentName(String chargeDepartmentName) {
		this.chargeDepartmentName = chargeDepartmentName;
	}

	public String getChargeUserId() {
		return chargeUserId;
	}

	public void setChargeUserId(String chargeUserId) {
		this.chargeUserId = chargeUserId;
	}

	public String getChargeUserName() {
		return chargeUserName;
	}

	public void setChargeUserName(String chargeUserName) {
		this.chargeUserName = chargeUserName;
	}

	public String getBizDepartmentId() {
		return bizDepartmentId;
	}

	public void setBizDepartmentId(String bizDepartmentId) {
		this.bizDepartmentId = bizDepartmentId;
	}

	public String getBizDepartmentName() {
		return bizDepartmentName;
	}

	public void setBizDepartmentName(String bizDepartmentName) {
		this.bizDepartmentName = bizDepartmentName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(Date serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public List<BizSystemDomain> getDomains() {
		return domains;
	}

	public void setDomains(List<BizSystemDomain> domains) {
		this.domains = domains;
	}

	public List<BizSystemModule> getModules() {
		return modules;
	}

	public void setModules(List<BizSystemModule> modules) {
		this.modules = modules;
	}
	
	
}
