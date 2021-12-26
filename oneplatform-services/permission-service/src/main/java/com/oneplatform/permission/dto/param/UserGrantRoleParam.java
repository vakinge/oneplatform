package com.oneplatform.permission.dto.param;


public class UserGrantRoleParam {

    /**
     * 关联系统id
     */
    private Integer systemId;

    /**
     * 用户编码
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 系统角色id
     */
    private String roleId;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
