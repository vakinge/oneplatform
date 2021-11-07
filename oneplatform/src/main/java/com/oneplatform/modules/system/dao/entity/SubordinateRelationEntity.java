package com.oneplatform.modules.system.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jeesuite.mybatis.core.BaseEntity;


@Table(name = "subordinate_relations")
public class SubordinateRelationEntity extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 子级ID
     */
    @Column(name = "child_id")
    private String childId;

    /**
     * 子级显示名
     */
    @Column(name = "child_name")
    private String childName;

    /**
     * 关系类型(userToGroup:用户-用户组,pageToMenu:菜单-页面,buttonToPage:页面-按钮)
     */
    @Column(name = "relation_type")
    private String relationType;

	public SubordinateRelationEntity() {}

	/**
	 * @param relationType
	 * @param parentId
	 * @param childId
	 */
	public SubordinateRelationEntity(String relationType, String parentId, String childId) {
		this.relationType = relationType;
		this.parentId = parentId;
		this.childId = childId;
	}

    /**
     *
     * @param relationType
     * @param parentId
     * @param childId
     * @param childName
     */
    public SubordinateRelationEntity(String relationType, String parentId, String childId, String childName) {
        this.relationType = relationType;
        this.parentId = parentId;
        this.childId = childId;
        this.childName = childName;
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
     * 获取父级ID
     *
     * @return parent_id - 父级ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID
     *
     * @param parentId 父级ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取子级ID
     *
     * @return child_id - 子级ID
     */
    public String getChildId() {
        return childId;
    }

    /**
     * 设置子级ID
     *
     * @param childId 子级ID
     */
    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    /**
     * 获取关系类型(userToGroup:用户-用户组,pageToMenu:菜单-页面,buttonToPage:页面-按钮)
     *
     * @return relation_type - 关系类型(userToGroup:用户-用户组,pageToMenu:菜单-页面,buttonToPage:页面-按钮)
     */
    public String getRelationType() {
        return relationType;
    }

    /**
     * 设置关系类型(userToGroup:用户-用户组,pageToMenu:菜单-页面,buttonToPage:页面-按钮)
     *
     * @param relationType 关系类型(userToGroup:用户-用户组,pageToMenu:菜单-页面,buttonToPage:页面-按钮)
     */
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }


}