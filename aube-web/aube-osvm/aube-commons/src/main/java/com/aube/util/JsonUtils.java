package com.aube.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static <T> String writeObjectToJson(T gsonObj) {
		return writeGson(gsonObj, false);
	}
	
	public static <T> String writeMapToJson(T gsonObj) {
		return writeGson(gsonObj, false);
	}
	
	public static <T> String writeGson(T gsonObj, boolean pretty) {
		GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping()
				.setDateFormat("yyyy-MM-dd HH:mm:ss");
		if (pretty) {
			builder.setPrettyPrinting().create();
		}
		Gson gson = builder.create();
		return gson.toJson(gsonObj);
	}

	
	public static <T> T parseObj(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

}
