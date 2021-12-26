package com.oneplatform.permission.dto.param;

import com.jeesuite.springweb.model.BaseQueryParam;


public class BizSystemQueryParam extends BaseQueryParam {

    /**
     * 系统id
     */
    private Integer id;

    /**
     * 系统编码
     */
    private String code;

    /**
     * 系统名称（可模糊）
     */
    private String name;

    /**
     * 系统管理部门编码
     */
    private String chargeDepartmentCode;

    /**
     * 负责人编码
     */
    private String chargeUserCode;

    /**
     * 负责人名称
     */
    private String chargeUserName;

    /**
     * 激活状态
     */
    private Boolean enabled;

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

    public String getChargeName() {
        return chargeUserName;
    }

    public void setChargeName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
