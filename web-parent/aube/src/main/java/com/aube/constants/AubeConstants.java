package com.aube.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AubeConstants {
	// 发布
	public static final String VIDEO_STATUS_PUBLISH = "publish";
	// 草稿
	public static final String VIDEO_STATUS_DRAFT = "draft";
	
	// 视频默认时长
	public static final Integer VIDEO_DEFAULT_DURATION = 180;

	public static Map<String, String> VIDEO_STATUS_MAP = new LinkedHashMap<String, String>();
	static {
		VIDEO_STATUS_MAP.put(VIDEO_STATUS_DRAFT, "草稿箱");
		VIDEO_STATUS_MAP.put(VIDEO_STATUS_PUBLISH, "已发布");
	}
	// 标签页－－全部
	public static final String TABS_TL_ALL = "all";
	// 标签页－－互动
	public static final String TABS_TL_INTERACTION = "interaction";
	// 标签页－－多镜头
	public static final String TABS_TL_MC = "mc";
	// 标签页－－物品
	public static final String TABS_TL_GOODS = "goods";

	// 时间线－－QA
	public static final String TYPE_TL_QA = "QA";
	// 时间线－－投票
	public static final String TYPE_TL_VOTE = "VOTE";
	// 时间线－－VS
	public static final String TYPE_TL_VS = "VS";
	// 时间线－－信息
	public static final String TYPE_TL_INFO = "INFO";
	// 时间线－－多镜头
	public static final String TYPE_TL_MULTICAMERA = "MULTICAMERA";
	// 时间线－－物品
	public static final String TYPE_TL_GOODS = "GOODS";

	public static Map<String, List<String>> TABS_2TYPE_LIST_MAP = new LinkedHashMap<String, List<String>>();
	static {
		List<String> interactionList = Arrays
				.asList(new String[] { TYPE_TL_QA, TYPE_TL_VOTE, TYPE_TL_VS, TYPE_TL_INFO });
		List<String> mcList = Arrays.asList(new String[] { TYPE_TL_MULTICAMERA });
		List<String> goodsList = Arrays.asList(new String[] { TYPE_TL_GOODS });
		List<String> allList = new ArrayList<String>();
		allList.addAll(interactionList);
		allList.addAll(mcList);
		allList.addAll(goodsList);
		TABS_2TYPE_LIST_MAP.put(TABS_TL_ALL, allList);
		TABS_2TYPE_LIST_MAP.put(TABS_TL_INTERACTION, interactionList);
		TABS_2TYPE_LIST_MAP.put(TABS_TL_MC, mcList);
		TABS_2TYPE_LIST_MAP.put(TABS_TL_GOODS, goodsList);
	}
}
