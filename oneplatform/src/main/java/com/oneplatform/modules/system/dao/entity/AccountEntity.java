package com.oneplatform.modules.system.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jeesuite.common.util.DigestUtils;
import com.oneplatform.core.base.StandardBaseEntity;

@Table(name = "account")
public class AccountEntity extends StandardBaseEntity {

	private static final String salts = DigestUtils.md5(AccountEntity.class.getName());
	
    private String name;

    private String realname;

    private String email;

    private String mobile;

    private String password;

    @Column(name = "staff_id")
    private Integer staffId;

    private Boolean enabled;

    private Boolean deleted;

    /**
     * 最后登录ip
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "last_login_at")
    private Date lastLoginAt;
    
    @Transient
    private List<AccountScopeEntity> accountScopes;


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
     * @return realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	/**
     * @return enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取最后登录ip
     *
     * @return last_login_ip - 最后登录ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置最后登录ip
     *
     * @param lastLoginIp 最后登录ip
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * @return last_login_at
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * @param lastLoginAt
     */
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    
	public List<AccountScopeEntity> getAccountScopes() {
		return accountScopes;
	}

	public void setAccountScopes(List<AccountScopeEntity> accountScopes) {
		this.accountScopes = accountScopes;
	}

	public static String encryptPassword(String password) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < password.length(); i++) {
			sb.append(password.charAt(i)).append(salts.substring(i*2, (i+1)*2));
		}
		return DigestUtils.md5(sb.toString());

	}
}