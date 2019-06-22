package com.oneplatform.weixin.dto;

import java.io.Serializable;

public class OauthUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String openId;
	private String nickname;
	private String avatar;
	private String gender; // male/female/unknow
	private String unionId;
	private String openType;
	private String appId;

	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String userInfoToUrlQueryString(){
		return String.format("nickname=%s&gender=%s&avatar=%s", nickname,gender,avatar);
	}

}
