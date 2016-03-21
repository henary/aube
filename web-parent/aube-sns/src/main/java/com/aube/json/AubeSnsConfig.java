package com.aube.json;

import com.aube.mongo.support.MGObject;

public class AubeSnsConfig extends MGObject<AubeSnsConfig> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7373239273332006466L;
	private String content;
	
	public AubeSnsConfig() {
		
	}
	
	public AubeSnsConfig(Integer id, String content) {
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
