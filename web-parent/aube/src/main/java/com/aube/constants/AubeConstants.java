package com.aube.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.aube.util.GoogleCache;
import com.google.common.cache.Cache;

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
	// 标签页－－广告
	public static final String TABS_TL_AD = "ad";

	public static Cache<String, List<String>> TABS_2TYPE_LIST_CACHE = new GoogleCache<String, List<String>>(10);
	public static Cache<String, String> TL_TYPE_2_TABS_CACHE = new GoogleCache<String, String>(100);

	static {
		List<String> interactionList = Arrays.asList(new String[] { VideoTimelineExtraEnum.QA.getTlType(), VideoTimelineExtraEnum.VOTE.getTlType(),
				VideoTimelineExtraEnum.VS.getTlType(), VideoTimelineExtraEnum.INFO.getTlType() });
		List<String> mcList = Arrays.asList(new String[] { VideoTimelineExtraEnum.MULTICAMERA.getTlType() });
		List<String> goodsList = Arrays.asList(new String[] { VideoTimelineExtraEnum.AD.getTlType() });
		List<String> allList = new ArrayList<String>();
		allList.addAll(interactionList);
		allList.addAll(mcList);
		allList.addAll(goodsList);
		TABS_2TYPE_LIST_CACHE.put(TABS_TL_INTERACTION, interactionList);
		TABS_2TYPE_LIST_CACHE.put(TABS_TL_MC, mcList);
		TABS_2TYPE_LIST_CACHE.put(TABS_TL_AD, goodsList);

		ConcurrentMap<String, List<String>> tab2TypeMap = TABS_2TYPE_LIST_CACHE.asMap();
		for (Map.Entry<String, List<String>> entry : tab2TypeMap.entrySet()) {
			List<String> tlTypeList = entry.getValue();
			for (String tltype : tlTypeList) {
				TL_TYPE_2_TABS_CACHE.put(tltype, entry.getKey());
			}
		}
		TABS_2TYPE_LIST_CACHE.put(TABS_TL_ALL, allList);
	}
}
