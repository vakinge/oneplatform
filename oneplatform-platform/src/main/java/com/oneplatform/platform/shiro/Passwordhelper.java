package com.oneplatform.platform.shiro;

import java.util.Date;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.jeesuite.common.util.DateUtils;


public class Passwordhelper {
	
	private static final String SALT_PREFIX = "@q&k6^g";
	
	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	public static String encryptPassword(String password,Date createAt) {
		password = new SimpleHash(algorithmName, password, getPasswordSalt(createAt), hashIterations).toHex();
		return password;

	}
	
	public static String encryptPassword(String password,ByteSource salt) {
		password = new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
		return password;

	}
	
	public static ByteSource getPasswordSalt(Date createAt){
		return ByteSource.Util.bytes(SALT_PREFIX + (createAt.getTime() / 1000 / 60 / 10));
	}
	
	
	public static void main(String[] args) {
		System.out.println(encryptPassword("123456", getPasswordSalt(DateUtils.parseDate("2018-03-03 12:55:30"))));
	}
}
