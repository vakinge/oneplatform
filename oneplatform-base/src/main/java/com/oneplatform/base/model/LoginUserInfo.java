package com.oneplatform.base.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.oneplatform.base.util.SecurityCryptUtils;

@JsonInclude(Include.NON_NULL)
public class LoginUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String CONTACT_CHAR = "#";

	private Integer id;

	private String username;

	private String nickname;

	private String email;

	private String mobile;

	private String avatar;
	
	private String userType;

	private Integer expiresIn;

	private Long expiresAt;

	public LoginUserInfo() {
	}

	public LoginUserInfo(Integer id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return StringUtils.isBlank(username) ? mobile : username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String toEncodeString() {

		StringBuilder builder = new StringBuilder();
		builder.append(id).append(CONTACT_CHAR);
		if (userType != null)
			builder.append(userType);
		builder.append(CONTACT_CHAR);
		if (username != null)
			builder.append(username);
		builder.append(CONTACT_CHAR);
		if (nickname != null)
			builder.append(nickname);
		builder.append(CONTACT_CHAR);

		return SecurityCryptUtils.encrypt(builder.toString());
	}

	public static LoginUserInfo decode(String encodeString) {
		if (StringUtils.isBlank(encodeString))
			return null;
		encodeString = SecurityCryptUtils.decrypt(encodeString);
		String[] splits = encodeString.split(CONTACT_CHAR);

		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setId(Integer.parseInt(splits[0]));
		userInfo.setUserType(splits[1]);
		userInfo.setUsername(StringUtils.trimToNull(splits[2]));
		if(splits.length >=4)userInfo.setNickname(StringUtils.trimToNull(splits[3]));

		return userInfo;
	}
	
	public static void main(String[] args) {
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setId(100);
		userInfo.setUsername("admin");
		
		String encodeString = userInfo.toEncodeString();
		System.out.println(encodeString);
		
		userInfo = decode(encodeString);
		System.out.println(userInfo);
	}

}