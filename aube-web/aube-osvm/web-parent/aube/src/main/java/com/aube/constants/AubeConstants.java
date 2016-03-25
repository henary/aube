package com.aube.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class AubeConstants {
	// 发布
	public static final String VIDEO_STATUS_PUBLISH = "publish";
	// 草稿
	public static final String VIDEO_STATUS_DRAFT = "draft";

	public static Map<String, String> VIDEO_STATUS_MAP = new LinkedHashMap<String, String>();
	static {
		VIDEO_STATUS_MAP.put(VIDEO_STATUS_DRAFT, "草稿箱");
		VIDEO_STATUS_MAP.put(VIDEO_STATUS_PUBLISH, "已发布");
	}
}
