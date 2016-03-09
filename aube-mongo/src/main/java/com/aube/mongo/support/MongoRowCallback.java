package com.aube.mongo.support;

import java.util.Map;


@SuppressWarnings({"rawtypes"})
public interface MongoRowCallback {
	void processRow(Map row);
}
