package com.aube.constant;


/**
 * 3000~3100 节目相关
 */
public interface AubeErrorCodeConstants extends ErrorCodeConstant {
	// 节目不存在
	String CODE_SHOW_NOT_EXITS = "3000";
	// 没有删除的权限
	String CODE_SHOW_NO_OPERA_AUTH = "3001";
	// 视频不存在
	String CODE_VIDEO_NOT_EXITS = "3010";
	// 不支持的时间线类型
	String CODE_UNSUPPORT_EXTRA = "3020";
	// VS最多只能是2
	String CODE_VS_MOST2 = "3030";
	// VS 多镜头信息为空
	String CODE_MC_LIST_NULL = "3031";
}
