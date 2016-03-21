package com.aube.constants.sns;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SnsMessageConstants {
	// 消息通知来源--融云
	public static final String MESSAGE_SOURCE_RONGCLOUD = "rongcloud";
	// 消息通知来源--环信
	public static final String MESSAGE_SOURCE_HUANXIN = "huanxin";
	
	// 普通消息类型
	public static final String MESSAGE_TYPE_NORMAL = "normal";
	// 喜欢的消息类型
	public static final String MESSAGE_TYPE_LIKE = "like";
	
	public static ConcurrentMap<String, String> RONGCLOUD_CHANNEL_TYPE_2APP_MAP = new ConcurrentHashMap<String, String>();
	
	static {
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("PERSON", "1");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("PERSONS", "2");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("GROUP", "3");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("TEMPGROUP", "4");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("CUSTOMERSERVICE", "5");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("NOTIFY", "6");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("MC", "7");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP.put("MP", "8");
	}
}
