package com.oneplatform.organisation.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "department")
public class DepartmentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String telephone;

    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 联系人员工ID
     */
    @Column(name = "contact_employee_id")
    private Integer contactEmployeeId;

    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String contactName;
    
    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

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
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
     * 获取联系人员工ID
     *
     * @return contact_employee_id - 联系人员工ID
     */
    public Integer getContactEmployeeId() {
        return contactEmployeeId;
    }

    /**
     * 设置联系人员工ID
     *
     * @param contactEmployeeId 联系人员工ID
     */
    public void setContactEmployeeId(Integer contactEmployeeId) {
        this.contactEmployeeId = contactEmployeeId;
    }

    /**
     * 获取联系人
     *
     * @return contact_name - 联系人
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 设置联系人
     *
     * @param contactName 联系人
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
  
    public Boolean getInActive() {
		return inActive;
	}

	public void setInActive(Boolean inActive) {
		this.inActive = inActive;
	}

	/**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return created_by
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return updated_by
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}