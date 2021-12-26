package com.oneplatform.permission.dto.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.oneplatform.permission.constants.GrantSourceType;
import com.oneplatform.permission.constants.GrantTargetType;

import io.swagger.annotations.ApiModelProperty;

public class GrantRelationParam {

    /**
     * 授权资源类型
     */
    @ApiModelProperty("授权资源类型(menu,api)")
    @NotNull(message = "授权资源类型不能为空")
    private GrantSourceType sourceType;

    /**
     * 资源ID
     */
    @ApiModelProperty("资源ID列表")
    private List<String> sourceIdList;

    /**
     * 授权目标类型
     */
    @ApiModelProperty("授权目标类型(user,userGroup)")
    @NotNull(message = "授权目标类型不能为空")
    private GrantTargetType targetType;

    /**
     * 目标ID
     */
    @ApiModelProperty("目标ID")
    @NotNull(message = "目标id不能为空")
    private String targetId;

    /**
     * 获取授权资源类型
     *
     * @return source_type - 授权资源类型
     */
    public GrantSourceType getSourceType() {
        return sourceType;
    }

    /**
     * 设置授权资源类型
     *
     * @param sourceType 授权资源类型
     */
    public void setSourceType(GrantSourceType sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取授权目标类型
     *
     * @return target_type - 授权目标类型
     */
    public GrantTargetType getTargetType() {
        return targetType;
    }

    /**
     * 设置授权目标类型
     *
     * @param targetType 授权目标类型
     */
    public void setTargetType(GrantTargetType targetType) {
        this.targetType = targetType;
    }

    /**
     * 获取目标ID
     *
     * @return target_id - 目标ID
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 设置目标ID
     *
     * @param targetId 目标ID
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public List<String> getSourceIdList() {
        return sourceIdList;
    }

    public void setSourceIdList(List<String> sourceIdList) {
        this.sourceIdList = sourceIdList;
    }
}