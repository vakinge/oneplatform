package com.oneplatform.modules.system.dto.param;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.oneplatform.modules.system.dto.SubordinateRelation;

import io.swagger.annotations.ApiModelProperty;


public class BizSystemParam {

    @ApiModelProperty(value = "业务系统id")
    private Integer id;

    @ApiModelProperty(value = "业务系统编码",required = true)
    @NotBlank(message = "系统编码不能为空")
    private String code;

    @ApiModelProperty(value = "系统名称",required = true)
    @NotBlank(message = "系统名称不能为空")
    private String name;

    @ApiModelProperty(value = "系统别名")
    private String alias;

    @ApiModelProperty(value = "系统图标")
    private String icon;

    @ApiModelProperty(value = "系统说明")
    private String description;

    @ApiModelProperty(value = "系统等级")
    private String level;

    @ApiModelProperty(value = "研发类型")
    private String type;

    @ApiModelProperty(value = "系统状态")
    private String state;

    @ApiModelProperty(value = "上线时间")
    private Date launchTime;

    @ApiModelProperty(value = "用户群体")
    private String consumer;

    @ApiModelProperty(value = "业务需求部门编码")
    private String bizDepartmentCode;

    @ApiModelProperty(value = "业务需求部门")
    private String bizDepartmentName;

    @ApiModelProperty(value = "外购供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "外购供应商联系人")
    private String supplierLinkman;

    @ApiModelProperty(value = "外购供应商联系电话")
    private String supplierPhone;

    @ApiModelProperty(value = "系统管理部门编码")
    private String chargeDepartmentCode;

    @ApiModelProperty(value = "系统管理部门名称")
    private String chargeDepartmentName;
    
    @ApiModelProperty(value = "中台标准权限管理模式")
    private Boolean standardAuthMode = false;

    @ApiModelProperty(value = "是否运维平台系统")
    private Boolean operational;

    @ApiModelProperty(value = "是否内部系统")
    private Boolean internal;

    @ApiModelProperty(value = "负责人id")
    private String chargeUserCode;

    @ApiModelProperty(value = "负责人名称")
    private String chargeUserName;

    @ApiModelProperty(value = "绑定系统的角色名称")
    private List<UserGroupParam> roleList;

    @ApiModelProperty(value = "系统域名")
    private List<String> domainList;

    @ApiModelProperty(value = "管理员列表，员工编码")
    private List<SubordinateRelation> adminList;

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
    
	public Boolean getStandardAuthMode() {
		return standardAuthMode;
	}

	public void setStandardAuthMode(Boolean standardAuthMode) {
		this.standardAuthMode = standardAuthMode;
	}

    public String getChargeUserName() {
        return chargeUserName;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getChargeDepartmentName() {
        return chargeDepartmentName;
    }

    public void setChargeDepartmentName(String chargeDepartmentName) {
        this.chargeDepartmentName = chargeDepartmentName;
    }

    public String getChargeDepartmentCode() {
        return chargeDepartmentCode;
    }

    public void setChargeDepartmentCode(String chargeDepartmentCode) {
        this.chargeDepartmentCode = chargeDepartmentCode;
    }

    public String getChargeUserCode() {
        return chargeUserCode;
    }

    public void setChargeUserCode(String chargeUserCode) {
        this.chargeUserCode = chargeUserCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getBizDepartmentCode() {
        return bizDepartmentCode;
    }

    public void setBizDepartmentCode(String bizDepartmentCode) {
        this.bizDepartmentCode = bizDepartmentCode;
    }

    public String getBizDepartmentName() {
        return bizDepartmentName;
    }

    public void setBizDepartmentName(String bizDepartmentName) {
        this.bizDepartmentName = bizDepartmentName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierLinkman() {
        return supplierLinkman;
    }

    public void setSupplierLinkman(String supplierLinkman) {
        this.supplierLinkman = supplierLinkman;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public List<String> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<String> domainList) {
        this.domainList = domainList;
    }

    public List<SubordinateRelation> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<SubordinateRelation> adminList) {
        this.adminList = adminList;
    }

    public List<UserGroupParam> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserGroupParam> roleList) {
        this.roleList = roleList;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getOperational() {
        return operational;
    }

    public void setOperational(Boolean operational) {
        this.operational = operational;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }
}
