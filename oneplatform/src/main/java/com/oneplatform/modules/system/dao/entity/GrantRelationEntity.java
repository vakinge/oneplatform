package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jeesuite.mybatis.core.BaseEntity;
import com.oneplatform.modules.system.dto.GrantRelation;

@Table(name = "grant_relations")
public class GrantRelationEntity extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 授权资源类型
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 资源ID
     */
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 授权目标类型
     */
    @Column(name = "target_type")
    private String targetType;

    /**
     * 目标ID
     */
    @Column(name = "target_id")
    private String targetId;

    public GrantRelationEntity(){}

    /**
     *
     * @param targetId
     * @param targetType
     * @param sourceType
     */
    public GrantRelationEntity(String targetId, String targetType, String sourceId, String sourceType){
        this.targetId = targetId;
        this.targetType = targetType;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
    }

    public static GrantRelationEntity fromObject(GrantRelation object){
        return new GrantRelationEntity(object.getTargetId().toString(), object.getTargetType(), object.getSourceId().toString(), object.getSourceType());
    }

    /**
     * @return id
     */
    @Override
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
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置资源ID
     *
     * @param sourceId 资源ID
     */
    public void setSourceId(String sourceId) {
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

}