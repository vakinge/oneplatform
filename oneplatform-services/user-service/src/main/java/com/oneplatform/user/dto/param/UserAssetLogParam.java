package com.oneplatform.user.dto.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * generated by www.jeesuite.com
 */
public class UserAssetLogParam{

    @ApiModelProperty("属性名")
    private Integer id;
    
    @ApiModelProperty("属性名")
    private Integer userId;
    
    @ApiModelProperty("属性名")
    private String tradeName;
    
    @ApiModelProperty("余额，平台币，积分")
    private String assetType;
    
    @ApiModelProperty("可用的，冻结的")
    private String subType;
    
    @ApiModelProperty("支出，收入，冻结，解冻")
    private String tradeType;
    
    @ApiModelProperty("属性名")
    private java.math.BigDecimal amount;
    
    @ApiModelProperty("当前可用的")
    private java.math.BigDecimal currentAvailable;
    
    @ApiModelProperty("当前冻结的")
    private java.math.BigDecimal currentFrozen;
    
    @ApiModelProperty("属性名")
    private String sign;
    
    @ApiModelProperty("属性名")
    private String memo;
    
    @ApiModelProperty("是否隐藏(不对外显示)")
    private Boolean hidden;
    
    @ApiModelProperty("属性名")
    private java.util.Date tradeAt;
    
    @ApiModelProperty("经办人")
    private String operator;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    public java.math.BigDecimal getCurrentAvailable() {
        return currentAvailable;
    }

    public void setCurrentAvailable(java.math.BigDecimal currentAvailable) {
        this.currentAvailable = currentAvailable;
    }
    public java.math.BigDecimal getCurrentFrozen() {
        return currentFrozen;
    }

    public void setCurrentFrozen(java.math.BigDecimal currentFrozen) {
        this.currentFrozen = currentFrozen;
    }
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
    public java.util.Date getTradeAt() {
        return tradeAt;
    }

    public void setTradeAt(java.util.Date tradeAt) {
        this.tradeAt = tradeAt;
    }
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}