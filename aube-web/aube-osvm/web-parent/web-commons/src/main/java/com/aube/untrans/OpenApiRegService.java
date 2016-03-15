package com.aube.untrans;

public interface OpenApiRegService {
	void registerOpenApi(String path, String data);

	void unregisterOpenApi();
}
