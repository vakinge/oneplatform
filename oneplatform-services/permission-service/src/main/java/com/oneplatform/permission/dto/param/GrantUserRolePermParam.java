package com.oneplatform.permission.dto.param;

import java.util.List;

/**
 * 
 * <br>
 * Class Name   : GrantUserGroupPermParam
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年9月29日
 */
public class GrantUserRolePermParam {

	private Integer userGroupId;
	private List<GrantPermItem> permItems;
	
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	public List<GrantPermItem> getPermItems() {
		return permItems;
	}
	public void setPermItems(List<GrantPermItem> permItems) {
		this.permItems = permItems;
	}
	
	
 }
