package com.oneplatform.organisation.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String mobile;

    private String address;

    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @Column(name = "contact_tel")
    private String contactTel;

    @Column(name = "account_id")
    private Integer accountId;
    
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "joined_at")
    private Date joinedAt;

    @Column(name = "left_at")
    private Date leftAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;
    @Transient
    private String departmentInfo;
    @Transient
    private String companyName;

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
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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

    /**
     * 获取联系人电话
     *
     * @return contact_tel - 联系人电话
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * 设置联系人电话
     *
     * @param contactTel 联系人电话
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     * @return account_id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * @return in_active
     */
    public Boolean getInActive() {
        return inActive;
    }

    /**
     * @param inActive
     */
    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    /**
     * @return joined_at
     */
    public Date getJoinedAt() {
        return joinedAt;
    }

    /**
     * @param joinedAt
     */
    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    /**
     * @return left_at
     */
    public Date getLeftAt() {
        return leftAt;
    }

    /**
     * @param leftAt
     */
    public void setLeftAt(Date leftAt) {
        this.leftAt = leftAt;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(String departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}