package com.oneplatform.platform.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;


public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher {
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		// 取得原始的输入数据信息
		Object tokenCredentials = Passwordhelper.encryptPassword(super.toString(token.getCredentials()), ((SimpleAuthenticationInfo)info).getCredentialsSalt());
		// 取得认证数据库中的数据
		Object accountCredentials = super.getCredentials(info);
		return super.equals(tokenCredentials, accountCredentials);
	}
}
