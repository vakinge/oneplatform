package com.oneplatform.base.exception;

import com.jeesuite.common.JeesuiteBaseException;

public class AssertUtil {

	public static void isTrue(boolean expression, int code,String message) {
		if (!expression) {
			throw new JeesuiteBaseException(code, message);
		}
	}
	

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new JeesuiteBaseException(ExceptionCode.RECORD_EXISTED.code, message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, ExceptionCode.RECORD_EXISTED.defaultMessage);
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new JeesuiteBaseException(ExceptionCode.RECORD_NOT_EXIST.code, message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, ExceptionCode.RECORD_NOT_EXIST.defaultMessage);
	}
	

	public static void notBlank(String expression, String message) {
		if (expression == null ||"".equals(expression.trim())) {
			throw new JeesuiteBaseException(ExceptionCode.REQUEST_PARAM_REQUIRED.code, message);
		}
	}
	
	public static void notInitData(int id) {
		if (id < 10000) {
			throw new JeesuiteBaseException(ExceptionCode.OPTER_NOT_ALLOW.code, "默认数据禁止当前操作");
		}
	}

}
