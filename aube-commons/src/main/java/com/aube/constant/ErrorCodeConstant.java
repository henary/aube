package com.aube.constant;

public interface ErrorCodeConstant {
	// 成功
	String CODE_SUCCESS = "0000";
	// 系统异常
	String CODE_SYSTEM_ERROR = "9999";
	// 系统级别错误java exception的message
	String CODE_UNKNOWN_ERROR = "10000";
	
	/**
	 * 100~200 API参数业务错误
	 */
	// 数据为空
	String CODE_DATA_ERROR = "100";
	// 参数类型不一致
	String CODE_DATA_TYPE_ERROR = "101";

	/**
	 * 1000～1100用户相关
	 */
	// 用户不存在
	String CODE_ACCOUNT_NOT_EXIST = "1000";
	// 密码必须输入
	String CODE_ACCOUNT_PASS_MUST = "1001";
	// 用户账号或者密码错误
	String CODE_ACCOUNT_PASS_ERROR = "1002";
	// 两次密码不一致
	String CODE_ACCOUNT_PASS_UNMATCHED = "1003";
	// 密码格式不正确,只能是字母，数字，英文标点，长度6—14位！
	String CODE_ACCOUNT_PASS_PATTERN_ERROR = "1004";
	// 密码过于简单，请重新输入！
	String CODE_ACCOUNT_PASS_2SIMPLE = "1005";
	// 第三方用户没有登陆
	String CODE_OPENMEMBER_NOT_EXITS = "1010";

}
