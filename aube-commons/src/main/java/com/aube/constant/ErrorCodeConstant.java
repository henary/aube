package com.aube.constant;

/**
 * 错误代码<br>
 * 0000~0100 系统错误<br>
 * 0101~0200 API项目错误<br>
 * 3000~3100 节目相关
 */
public interface ErrorCodeConstant {
	// 成功
	String CODE_SUCCESS = "0000";
	// 系统异常
	String CODE_SYSTEM_ERROR = "9999";
	// 系统级别错误java exception的message
	String CODE_UNKNOWN_ERROR = "10000";

	/**
	 * 1000~1100 数据不支持的类型
	 */
	// 数据为空
	String CODE_DATA_ERROR = "1000";
	// 参数类型不一致
	String CODE_DATA_TYPE_ERROR = "1001";
	// 文件上传不支持的标签类型
	String CODE_UPLOAD_NOT_SUPPORTS_TAG = "1002";
	// 文件上传的列表为空
	String CODE_UPLOAD_EMPTY = "1003";
	// 上传参数错误
	String CODE_UPLOAD_PARAM_ERROR = "1004";

	/**
	 * 1100～1200用户相关
	 */
	// 用户不存在
	String CODE_ACCOUNT_NOT_EXIST = "1100";
	// 密码必须输入
	String CODE_ACCOUNT_PASS_MUST = "1101";
	// 用户账号或者密码错误
	String CODE_ACCOUNT_PASS_ERROR = "1102";
	// 两次密码不一致
	String CODE_ACCOUNT_PASS_UNMATCHED = "1103";
	// 密码格式不正确,只能是字母，数字，英文标点，长度6—14位！
	String CODE_ACCOUNT_PASS_PATTERN_ERROR = "1104";
	// 密码过于简单，请重新输入！
	String CODE_ACCOUNT_PASS_2SIMPLE = "1105";
	// 第三方用户没有登陆
	String CODE_OPENMEMBER_NOT_EXITS = "1106";

	/**
	 * 2000～2100 聊天相关
	 */
	// 聊天应用没有配置
	String CODE_CHAT_CFG_DEFAULT_NOT_EXITS = "2000";
	String CODE_CHAT_APP_NOT_EXITS = "2001";
	String CODE_CHAT_APP_NOT_SUPPORT = "2002";

}
