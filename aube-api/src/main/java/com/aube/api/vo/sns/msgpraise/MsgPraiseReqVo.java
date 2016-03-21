package com.aube.api.vo.sns.msgpraise;

import com.aube.api.vo.PageReqVo;

/**
 * 消息点赞
 * 
 * @author admin
 *
 */
public class MsgPraiseReqVo extends PageReqVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5070230548510310980L;
	// 视频ID
	private String videoid;
	// 消息ID
	private String msgid;

	public MsgPraiseReqVo() {

	}

	public MsgPraiseReqVo(String videoid, String msgid) {
		this.videoid = videoid;
		this.msgid = msgid;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
}
