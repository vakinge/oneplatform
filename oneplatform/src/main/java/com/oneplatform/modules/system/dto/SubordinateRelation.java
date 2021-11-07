package com.oneplatform.modules.system.dto;

import io.swagger.annotations.ApiModelProperty;


public class SubordinateRelation {

    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("父级name")
    private String parentName;

    /**
     * 子级id
     */
    @ApiModelProperty("子级id")
    private String childId;

    @ApiModelProperty("子级name")
    private String childDisplayName;

    /**
     * 子级信息集合,用逗号隔开（同一系统和同一约束范围下存在多个管理员的场景使用）
     */
    @ApiModelProperty("子级信息集合")
    private String childInfoList;

    /**
     * 关系类型
     */
    @ApiModelProperty("关系类型")
    private String relationType;

    /**
     * 关联系统id
     */
    @ApiModelProperty("关联系统id")
    private Integer systemId;

    @ApiModelProperty("关联系统名称")
    private String systemName;


    public SubordinateRelation(){}

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getChildInfoList() {
        return childInfoList;
    }

    public void setChildInfoList(String childInfoList) {
        this.childInfoList = childInfoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildDisplayName() {
        return childDisplayName;
    }

    public void setChildDisplayName(String childDisplayName) {
        this.childDisplayName = childDisplayName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
