package com.oneplatform.permission.dto.param;

import javax.validation.constraints.NotNull;

import com.oneplatform.permission.constants.SubRelationType;

import io.swagger.annotations.ApiModelProperty;


public class SubordinateRelationQueryParam {

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private String parentId;

    /**
     * 子级id
     */
    @ApiModelProperty("子级id")
    private String childId;

    /**
     * 关系类型
     */
    @ApiModelProperty("关系类型")
    @NotNull(message = "从属关系类型不能为空")
    private SubRelationType relationType;

    public SubordinateRelationQueryParam(){}

    public SubordinateRelationQueryParam(SubRelationType subRelationType,String parentId,String childId){
        this.relationType = subRelationType;
        this.parentId = parentId;
        this.childId = childId;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public SubRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(SubRelationType relationType) {
        this.relationType = relationType;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
}
