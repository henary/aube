package com.aube.constant;

/**
 * 3000~3100 节目相关
 */
public interface AubeErrorCodeConstants extends ErrorCodeConstant {
	// 没有删除的权限
	String CODE_SHOW_NO_OPERA_AUTH = "3000";
	// 节目不存在
	String CODE_SHOW_NOT_EXITS = "3010";
	// 视频不存在
	String CODE_VIDEO_NOT_EXITS = "3011";
	// 不支持的时间线类型
	String CODE_TL_UNSUPPORT_EXTRA = "3020";
	// VS最多只能是2
	String CODE_TL_VS_MOST2 = "3021";
	// 多镜头信息为空
	String CODE_TL_MC_LIST_NULL = "3022";
	// 时间线重复
	String CODE_TL_TIME_REPEAT = "3023";
	// 时间线为空
	String CODE_TL_EMPTY = "3030";
	// 时间线有时间未设置
	String CODE_TL_TIME_NOT_SET = "3031";

}
