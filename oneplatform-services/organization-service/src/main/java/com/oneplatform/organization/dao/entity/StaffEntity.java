package com.oneplatform.organization.dao.entity;

import com.oneplatform.organization.dao.StandardBaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "staff")
public class StaffEntity extends StandardBaseEntity {
 
    /**
     * 员工编码
     */
    private String code;

    /**
     * 员工名称
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 证件类型
     */
    @Column(name = "id_type")
    private Integer idType;

    /**
     * 证件号码
     */
    @Column(name = "id_no")
    private String idNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;
    
    @Column(name = "employ_type")
    private Integer employType;
    
    @Column(name = "entry_date")
    private Date entryDate;

    /**
     * 是否部门负责人
     */
    @Column(name = "is_leader")
    private Boolean isLeader;

    /**
     * 管理员账号id
     */
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "tenant_id")
    private String tenantId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public Integer getEmployType() {
		return employType;
	}

	public void setEmployType(Integer employType) {
		this.employType = employType;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    
}