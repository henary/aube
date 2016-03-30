package com.aube.util;

import java.util.Map;
import java.util.TreeMap;

public class CCSginUtils {
	public static String getCCUploadSginStr(TreeMap<String, String> params, String appSecret) {
		StringBuilder queryStr = new StringBuilder(256);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			queryStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		queryStr.append("time").append("=").append(System.currentTimeMillis() / 1000);
		StringBuilder temp = new StringBuilder(queryStr);
		queryStr.append("&").append("salt").append("=").append(appSecret);
		temp.append("&").append("hash").append("=").append(StringUtil.md5(queryStr.toString()));
		return temp.toString();
	}
}
