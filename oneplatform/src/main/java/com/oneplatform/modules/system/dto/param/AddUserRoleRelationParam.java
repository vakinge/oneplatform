package com.oneplatform.modules.system.dto.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <br>
 * Class Name   : AddUserRoleRelationParam
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年12月31日
 */
public class AddUserRoleRelationParam  {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
	private String userId;
    @ApiModelProperty(value = "用户名称",required = false)
	private String userName;
    @ApiModelProperty(value = "角色列表",required = true)
    @NotNull(message = "角色列表不能为空")
	private List<Integer> roleIds;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the roleIds
	 */
	public List<Integer> getRoleIds() {
		return roleIds;
	}
	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
