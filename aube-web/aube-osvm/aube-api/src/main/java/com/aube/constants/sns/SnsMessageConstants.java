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
	
	public static ConcurrentMap<String, String> RONGCLOUD_CHANNEL_TYPE_2APP_MAP = null;
	
	static {
		ConcurrentMap<String, String> channelType2AppMap = new ConcurrentHashMap<String, String>();
		channelType2AppMap.put("PERSON", "1");
		channelType2AppMap.put("PERSONS", "2");
		channelType2AppMap.put("GROUP", "3");
		channelType2AppMap.put("TEMPGROUP", "4");
		channelType2AppMap.put("CUSTOMERSERVICE", "5");
		channelType2AppMap.put("NOTIFY", "6");
		channelType2AppMap.put("MC", "7");
		channelType2AppMap.put("MP", "8");
		RONGCLOUD_CHANNEL_TYPE_2APP_MAP = channelType2AppMap;
	}
}
