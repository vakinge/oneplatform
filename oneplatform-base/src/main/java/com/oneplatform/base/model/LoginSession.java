package com.oneplatform.base.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.common.util.TokenGenerator;
import com.oneplatform.base.GlobalContants.UserScope;
import com.oneplatform.base.util.SecurityCryptUtils;

@JsonInclude(Include.NON_NULL)
public class LoginSession {

	public static final int SESSION_EXPIRE_SECONDS = ResourceUtils.getInt("auth.session.expire.seconds", 7200);
	private static final String CONTACT_CHAR = "#";
	
	private Integer userId;
	private String userName;
	private List<String> scopes = new ArrayList<>();

	private String sessionId;
	private Integer expiresIn;
	private Long expiresAt;
	
	private UserInfo userInfo;
	
	public LoginSession() {}
	
	public static LoginSession create(){
		LoginSession session = new LoginSession();
		session.sessionId = TokenGenerator.generate("auth");
		session.expiresIn = 1800;
		session.expiresAt = System.currentTimeMillis()/1000 + session.expiresIn;
		return session;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
		this.expiresAt = System.currentTimeMillis()/1000 + this.expiresIn;
	}

	public boolean isAnonymous(){
		return userId == null || userId == 0;
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
	public Long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public boolean isSuperAdmin(){
		return scopes.contains(UserScope.sa.name());
	}

	public String toEncodeString(){
		
		StringBuilder builder = new StringBuilder();
		builder.append(sessionId);
		if(isAnonymous() == false){
			builder.append(CONTACT_CHAR);
			builder.append(userId).append(CONTACT_CHAR);
			if(StringUtils.isNotBlank(userName)){
				builder.append(userName);
			}
		}
		
		return SecurityCryptUtils.encrypt(builder.toString());
	}
	
	public String toJsonString(){
		return JsonUtils.toJson(this);
	}

	public static LoginSession decode(String encodeString){
		if(StringUtils.isBlank(encodeString))return null;
		encodeString = SecurityCryptUtils.decrypt(encodeString);
		String[] splits = encodeString.split(CONTACT_CHAR); //StringUtils.split(encodeString, CONTACT_CHAR);
		
		LoginSession session = new LoginSession();
		session.setSessionId(splits[0]);
		
		if(splits.length > 1){
			session.setUserId(Integer.parseInt(splits[1]));
			session.setUserName(splits[2]);
		}
		
		return session;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	 public static void main(String[] args) {
		LoginSession session = LoginSession.create();
		session.setUserId(1000);
		session.setUserName("周大福");
		String encodeString = session.toEncodeString();
		System.out.println(encodeString);
		LoginSession session2 = decode(encodeString);
		System.out.println(session2);
		System.out.println("---------------------");
		session = LoginSession.create();
		encodeString = session.toEncodeString();
		session2 = decode(encodeString);
		System.out.println(session2);
		
	}
	
	
}
