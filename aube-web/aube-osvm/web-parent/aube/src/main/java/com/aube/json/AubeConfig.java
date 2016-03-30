package com.aube.json;

import com.aube.mongo.support.MGObject;

public class AubeConfig extends MGObject<AubeConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562369085939377123L;
	private String content;

	public AubeConfig() {

	}

	public AubeConfig(String id, String content) {
		this.set_id(id);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
