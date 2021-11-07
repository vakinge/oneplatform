package com.oneplatform.modules.system.dto;

public class GrantRelation {

    private Integer id;

    /**
     * 授权资源类型
     */
    private String sourceType;

    /**
     * 资源ID
     */
    private Integer sourceId;

    /**
     * 授权目标类型
     */
    private String targetType;

    /**
     * 目标ID
     */
    private Integer targetId;

    /**
     * 关联系统
     */
    private Integer systemId;

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
     * 获取授权资源类型
     *
     * @return source_type - 授权资源类型
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置授权资源类型
     *
     * @param sourceType 授权资源类型
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取资源ID
     *
     * @return source_id - 资源ID
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置资源ID
     *
     * @param sourceId 资源ID
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取授权目标类型
     *
     * @return target_type - 授权目标类型
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * 设置授权目标类型
     *
     * @param targetType 授权目标类型
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * 获取目标ID
     *
     * @return target_id - 目标ID
     */
    public Integer getTargetId() {
        return targetId;
    }

    /**
     * 设置目标ID
     *
     * @param targetId 目标ID
     */
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    /**
     * 获取关联系统
     *
     * @return system_id - 关联系统
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * 设置关联系统
     *
     * @param systemId 关联系统
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}