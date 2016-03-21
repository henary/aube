package com.aube.vo.msg.req;

import java.io.Serializable;

public class MsgContentDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7057118710726232161L;

	private MsgContentExtra extra;
	private String content;

	public MsgContentExtra getExtra() {
		return extra;
	}

	public void setExtra(MsgContentExtra extra) {
		this.extra = extra;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
