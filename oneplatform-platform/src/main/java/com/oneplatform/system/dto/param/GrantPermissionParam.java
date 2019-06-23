package com.oneplatform.system.dto.param;

import java.util.List;

public class GrantPermissionParam {

	private int userId;
	private String platformType;
	private List<Integer> userGroupIds;
	private List<Integer> permGroupIds;
	private List<Integer> apiIds;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public List<Integer> getUserGroupIds() {
		return userGroupIds;
	}
	public void setUserGroupIds(List<Integer> userGroupIds) {
		this.userGroupIds = userGroupIds;
	}
	public List<Integer> getPermGroupIds() {
		return permGroupIds;
	}
	public void setPermGroupIds(List<Integer> permGroupIds) {
		this.permGroupIds = permGroupIds;
	}
	public List<Integer> getApiIds() {
		return apiIds;
	}
	public void setApiIds(List<Integer> apiIds) {
		this.apiIds = apiIds;
	}
	
	
}
