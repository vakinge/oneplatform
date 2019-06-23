package com.oneplatform.cms.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;
import javax.persistence.*;

@Table(name = "cms_tag")
public class TagEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "item_nums")
    private Integer itemNums;

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
     * @return item_nums
     */
    public Integer getItemNums() {
        return itemNums;
    }

    /**
     * @param itemNums
     */
    public void setItemNums(Integer itemNums) {
        this.itemNums = itemNums;
    }
}