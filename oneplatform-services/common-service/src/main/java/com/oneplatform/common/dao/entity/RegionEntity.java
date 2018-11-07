package com.oneplatform.common.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jeesuite.mybatis.core.BaseEntity;

@Table(name = "static_region")
public class RegionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "name_index")
    private String nameIndex;

    @Column(name = "name_pinyin")
    private String namePinyin;

    @Column(name = "name_pinyin_short")
    private String namePinyinShort;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return name_index
     */
    public String getNameIndex() {
        return nameIndex;
    }

    /**
     * @param nameIndex
     */
    public void setNameIndex(String nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * @return name_pinyin
     */
    public String getNamePinyin() {
        return namePinyin;
    }

    /**
     * @param namePinyin
     */
    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    /**
     * @return name_pinyin_short
     */
    public String getNamePinyinShort() {
        return namePinyinShort;
    }

    /**
     * @param namePinyinShort
     */
    public void setNamePinyinShort(String namePinyinShort) {
        this.namePinyinShort = namePinyinShort;
    }
}