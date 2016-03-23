package com.aube.constans;

import com.aube.util.StringUtil;

public class MongoData {
	public static final String ID_NAME_SYSTEMID_NAME_SYSTEM = "_id";

	public static String buildId() {
		return buildId(4);
	}

	public static String buildId(int num) {
		return StringUtil.getRandomString(num) + "_" + System.currentTimeMillis();
	}
}
