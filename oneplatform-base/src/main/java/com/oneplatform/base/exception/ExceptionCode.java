package com.oneplatform.base.exception;

public enum ExceptionCode {

	ILLEGAL_STATE(4001,"非法访问"),
	REQUEST_PARAM_REQUIRED(4002,"参数不能为空"),
	REQUEST_FORMAT_ILLEGAL(4003,"参数格式错误"),
	REQUEST_DUPLICATION(4004,"重复请求"),
	REQUEST_PARAM_ERROR(4005,"请求数据错误"),
	
	RECORD_NOT_EXIST(5001,"记录不存在"),
	RECORD_EXISTED(5002,"记录已存在"),
	RECORD_ILLEGAL_STATE(5003,"数据异常"),
	
	OPTER_NOT_ALLOW(5100,"不允许操作"),

	DB_ERROR(9001,"数据库异常"),
	SYSTEM_ERROR(9999,"系统异常");
	
	
	public final int code;
	public final String defaultMessage;

	private ExceptionCode(int code,String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}
	
	
}
