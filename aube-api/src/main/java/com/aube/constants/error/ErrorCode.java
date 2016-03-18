package com.aube.constants.error;

public interface ErrorCode {
	String CODE_SUCCESS = "0000";
	
	/**
	 * 100~200系统错误
	 */
	String CODE_DATA_ERROR = "100";
	
	/**
	 * 1000～2000用户相关
	 */
	// 用户不存在
	String CODE_ACCOUNT_NOT_EXIST = "1000";
	// 用户账号或者密码错误
	String CODE_ACCOUNT_ERROR = "1001";
	// 第三方用户没有登陆
	String CODE_OPENMEMBER_NOT_EXITS = "1010";
}
